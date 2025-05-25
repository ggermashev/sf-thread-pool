package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class AppThreadPool {
    private static final Logger logger = Logger.getLogger(AppThreadPool.class.getName());

    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAliveTime;
    private final TimeUnit timeUnit;
    private final int queueSize;

    private final List<AppWorker> workers;
    private final List<BlockingQueue<Runnable>> queues;

    private volatile boolean isShutdown = false;
    private final ReentrantLock mainLock = new ReentrantLock();
    private final AtomicInteger currentPoolSize = new AtomicInteger(0);
    private final AtomicInteger activeThreads = new AtomicInteger(0);

    public AppThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit timeUnit, int queueSize) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.queueSize = queueSize;

        this.workers = new ArrayList<>(maxPoolSize);
        this.queues = new ArrayList<>(maxPoolSize);

        for (int i = 0; i < corePoolSize; i++) {
            createWorker();
        }
        logger.info("Created pool with " + corePoolSize + " threads");
    }

    private void createWorker() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueSize);
        queues.add(queue);

        AppWorker worker = new AppWorker(workers, queue, keepAliveTime, timeUnit, activeThreads, currentPoolSize, corePoolSize);
        workers.add(worker);

        int poolSize = currentPoolSize.incrementAndGet();
        Thread thread = AppThreadFactory.newThread(worker, poolSize);
        thread.start();
    }

    public void execute(Runnable task) {
        if (isShutdown) {
            logRejectedTask(task);
            return;
        }

        mainLock.lock();
        try {
            int activeCount = activeThreads.get();
            int currentSize = currentPoolSize.get();

            if (activeCount == currentSize && currentSize < maxPoolSize) {
                createWorker();
                logger.info("New worker created");
            }

            int newSize = currentPoolSize.get();
            BlockingQueue<Runnable> queue = getRandomQueue(newSize);

            if (!queue.offer(task)) {
                logRejectedTask((task));
            } else {
                logger.info("Task added to queue");
            }
        } finally {
            mainLock.unlock();
        }
    }

    public void shutdown() {
        mainLock.lock();
        try {
            isShutdown = true;
            for (AppWorker worker : workers) {
                worker.interrupt();
            }
            logger.info("Shutdown");
        } finally {
            mainLock.unlock();
        }
    }

    private BlockingQueue<Runnable> getRandomQueue(int size) {
        int index = (int) Math.floor(Math.random() * size);
        return queues.get(index);
    }

    private void logRejectedTask(Runnable task) {
        logger.warning("Task rejected: " + task.toString());
    }
}
