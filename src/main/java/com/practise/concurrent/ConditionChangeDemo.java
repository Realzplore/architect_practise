package com.practise.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/11
 */
public class ConditionChangeDemo {
    private static List<String> lockObject = new ArrayList<>();

    public static void main1(String[] args) {
        Consumer consumer1 = new Consumer(lockObject);
        Consumer consumer2 = new Consumer(lockObject);
        Producer producer = new Producer(lockObject);
        consumer1.start();
        consumer2.start();
        producer.start();
    }

    static class Consumer extends Thread {
        private List<String> lock;

        public Consumer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    while (lock.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "list为空");
                        System.out.println(Thread.currentThread().getName() + "调用wait方法");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + "wait方法结束");
                    }
                    String element = lock.remove(0);
                    System.out.println(Thread.currentThread().getName() + "取出第一个元素为：" + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Producer extends Thread {
        private List<String> lock;

        public Producer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "开始添加元素");
                lock.add(Thread.currentThread().getName());
                lock.notifyAll();
            }
        }
    }

}
