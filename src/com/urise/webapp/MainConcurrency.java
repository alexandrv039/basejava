package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();
    private static int counter2 = 10000;

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        };
        thread0.start();
        System.out.println(thread0.getState());

        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(counter);

        Thread thread1 = new Thread(()->{
            while (counter < 10000) {
                counter++;
            }
        });
        Thread thread2 = new Thread(()->{
            while (counter < 10000) {
                counter++;
            }
        });
        thread1.start();
        thread2.start();


    }

    private static void inc() {

        synchronized (LOCK) {
            counter++;

            if (counter > 5000) {
                decr();
            }
        }
    }

    private synchronized static void decr() {
        counter2--;

        if (counter2 < 5000) {
            inc();
        }
    }
}
