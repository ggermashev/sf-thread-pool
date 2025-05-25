package org.example;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class AppThreadFactory {
    private static final Logger logger = Logger.getLogger(AppThreadFactory.class.getName());

    public static Thread newThread(AppWorker worker, int number) {
        Thread t = new Thread(worker, "Thread-" + number);
        t.setDaemon(false);
        logger.info("Created new thread: " + t.getName());
        return t;
    }
}