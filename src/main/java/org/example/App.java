package org.example;

import java.util.concurrent.TimeUnit;


public class App 
{
    public static void main(String[] args) throws InterruptedException {
        AppThreadPool pool = new AppThreadPool(
                2,
                4,
                5,
                TimeUnit.SECONDS,
                5
        );

        System.out.println("\nSome tasks");
        test(pool, 10, 1000);
        Thread.sleep(15 * 1000);


        System.out.println("\nMany tasks");
        test(pool, 50, 100);
        Thread.sleep(15 * 1000);

        pool.shutdown();
    }

    private static void test(AppThreadPool pool, int count, int sleepTime) {
        for (int i = 0; i < count; i++) {
            pool.execute(() -> {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
