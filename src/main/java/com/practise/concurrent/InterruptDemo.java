package com.practise.concurrent;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/4
 */
public class InterruptDemo {
    public static void main1(String[] args) {
        Thread threadA = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };

        Thread threadB = new Thread(){
            @Override
            public void run() {
                while (true);
            }
        };

        threadA.start();
        threadB.start();

        threadA.interrupt();
        threadB.interrupt();

        while (threadA.isInterrupted());
        System.out.println("threadA isInterrupted: " + threadA.isInterrupted());
        System.out.println("threadB isInterrupted: " + threadB.isInterrupted());
    }
}
