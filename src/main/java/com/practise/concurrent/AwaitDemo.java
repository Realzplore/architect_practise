package com.practise.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/6
 */
public class AwaitDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static volatile Boolean flag = false;


    public static void main1(String[] args) {
        Thread waiter = new Thread(new Waiter());
        waiter.start();
        Thread signaler = new Thread(new Signaler());
        signaler.start();
    }

    static class Waiter implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                while (!flag) {
                    System.out.println(Thread.currentThread().getName() + "条件不足请等待");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "接收到通知条件满足");
            }finally {
                lock.unlock();
            }
        }
    }

    static class Signaler implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                flag = true;
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }
    }
}
