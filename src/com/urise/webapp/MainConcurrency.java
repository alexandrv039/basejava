package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

        CountDownLatch countDownLatch = new CountDownLatch(THREADS_NUMBER);

//        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
                countDownLatch.countDown();
            });
            thread.start();
            threads.add(thread);
        }

//        threads.forEach(thread -> {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
        countDownLatch.await();
        System.out.println(counter);

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