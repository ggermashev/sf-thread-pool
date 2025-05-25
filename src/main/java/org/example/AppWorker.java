package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class AppWorker implements Runnable {
    private static final Logger logger = Logger.getLogger(AppWorker.class.getName());

    private final long keepAliveTime;
    private final TimeUnit timeUnit;

    private final List<AppWorker> workers;
    private final BlockingQueue<Runnable> queue;

    private final AtomicInteger activeThreads;
    private final AtomicInteger currentPoolSize;
    private final int corePoolSize;
    private volatile boolean running = true;

    public AppWorker(List<AppWorker> workers, BlockingQueue<Runnable> queue, long keepAliveTime, TimeUnit timeUnit, AtomicInteger activeThreads, AtomicInteger currentPoolSize, int corePoolSize) {
        this.workers = workers;
        this.queue = queue;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.activeThreads = activeThreads;
        this.currentPoolSize = currentPoolSize;
        this.corePoolSize = corePoolSize;
    }

    public void interrupt() {
        running = false;
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Runnable task = queue.poll(keepAliveTime, timeUnit);
                if (task != null) {
                    activeThreads.incrementAndGet();
                    try {
                        task.run();
                        logger.info("Task completed");
                    } finally {
                        activeThreads.decrementAndGet();
                    }
                }
            } catch (InterruptedException e) {
                if (currentPoolSize.get() > corePoolSize) {
                    terminate();
                }
            }
        }
    }

    public void terminate() {
        logger.info("Terminate worker");
        currentPoolSize.decrementAndGet();
        workers.remove(this);
    }
}
