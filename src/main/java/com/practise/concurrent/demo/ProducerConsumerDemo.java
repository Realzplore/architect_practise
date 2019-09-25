package com.practise.concurrent.demo;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/11
 */
public class ProducerConsumerDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();

    public static void main1(String[] args) {

    }

    static class Producer implements Runnable {
        private List<Integer> list;
        private int maxLength;
        private Lock lock;

        public Producer(List list, int maxLength, Lock lock) {
            this.list = list;
            this.maxLength = maxLength;
            this.lock = lock;
        }

        @Override
        public void run() {

        }
    }

}
