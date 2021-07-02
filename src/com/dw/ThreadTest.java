package com.dw;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ThreadTest tt = new ThreadTest();
        Thread a = new Thread(tt.thread1);
        Thread b = new Thread(tt.thread2);

        b.start();
        Thread.sleep(2000);
        a.start();
        Thread.sleep(2000);
//        a.interrupt();
    }




    Runnable thread1 = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("enter1--------");
                lock.lockInterruptibly();
                System.out.println("enter1--------lock");
                try {
                    Thread.sleep(10000);
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println("enter1--------unlock");
                    lock.unlock();
                    System.out.println("done1--------unlock");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    Runnable thread2 = new Runnable() {
        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("enter2--------lock");
                Thread.sleep(10000);
                System.out.println("enter2--------unlock");
                lock.unlock();
                System.out.println("done2--------unlock");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


}
