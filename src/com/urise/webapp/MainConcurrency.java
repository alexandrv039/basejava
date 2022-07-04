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

        TestDeadlock.Test testDeadlock1 = new TestDeadlock.Test("thread1");
        TestDeadlock.Test testDeadlock2 = new TestDeadlock.Test("thread2");

        Thread thread1 = new Thread(() -> {
            testDeadlock1.printThreadsNames(testDeadlock2);
        });
        Thread thread2 = new Thread(() -> {
            testDeadlock2.printThreadsNames(testDeadlock1);
        });
        thread1.start();
        thread2.start();

    }

    private synchronized static void inc() {
        counter++;
    }

}

class TestDeadlock {

    static class Test {
        private String threadName;

        public Test(String threadName) {
            this.threadName = threadName;
        }

        public synchronized String getThreadName() {
            return threadName;
        }

        public synchronized void printThreadsNames(Test thread) {
            System.out.println("thread1: = " + getThreadName() + ", thread2:" + thread.getThreadName());
            thread.printName();
        }

        public synchronized void printName() {
            System.out.println(getThreadName());
        }
    }
}