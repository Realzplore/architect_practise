package com.practise.concurrent;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/29
 */
public class DeadLockDemo {
    private static String resource_a = "A";
    private static String resource_b = "B";
    public static void main1(String[] args) {
        deadLock();
    }

    /**
     * 线程A先占用了资源a，然后睡眠3秒后准备占用资源b，
     * 这个时候线程B已经占用了资源a，并准备等待线程A释放资源a，
     * 着就会导致deadLock
     */
    private static void deadLock() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource_a) {
                    System.out.println("A get resource a");
                    try {
                        Thread.sleep(3000);
                        synchronized (resource_b) {
                            System.out.println("A get resource b");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource_b) {
                    System.out.println("B get resource b");
                    synchronized (resource_a) {
                        System.out.println("B get resource a");
                    }
                }
            }
        });
        threadA.start();
        threadB.start();
    }


}
