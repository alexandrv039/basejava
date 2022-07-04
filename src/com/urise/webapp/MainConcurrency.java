package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();
    private String threadName = Thread.currentThread().getName();

    public String getThreadName() {
        return threadName;
    }

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

        MainConcurrency concurrency1 = new MainConcurrency();
        MainConcurrency concurrency2 = new MainConcurrency();
        TestDeadlock testDeadlock1 = new TestDeadlock(concurrency1);
        TestDeadlock testDeadlock2 = new TestDeadlock(concurrency2);

        Thread thread1 = new Thread(() -> {
            testDeadlock1.printThreadsNames(concurrency2, testDeadlock2);
        });
        Thread thread2 = new Thread(() -> {
            testDeadlock2.printThreadsNames(concurrency1, testDeadlock1);
        });
        thread1.start();
        thread2.start();

    }

    private synchronized static void inc() {
        counter++;
    }

}

class TestDeadlock {

        private MainConcurrency mainConcurrency;

        public TestDeadlock(MainConcurrency mainConcurrency) {
            this.mainConcurrency = mainConcurrency;
        }

        public void setDeadlock(MainConcurrency mainConcurrency) {
            this.mainConcurrency = mainConcurrency;
        }

        public synchronized void printThreadsNames(MainConcurrency concurrency, TestDeadlock testDeadlock) {
            System.out.println("thread1: = " + mainConcurrency.getThreadName() + ", thread2:" + concurrency.getThreadName());
            testDeadlock.printName();
        }

        public synchronized void printName() {
            System.out.println(mainConcurrency.getThreadName());
        }
}