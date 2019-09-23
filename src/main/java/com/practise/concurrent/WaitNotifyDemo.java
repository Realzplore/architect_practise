package com.practise.concurrent;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/10
 */
public class WaitNotifyDemo {
    private static String lockObject = "";
    private static boolean isWait = true;

    public static void main1(String[] args) {
        WaitThread waitThread = new WaitThread(lockObject);
        NotifyThread notifyThread = new NotifyThread(lockObject);
        notifyThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitThread.start();
    }

    static class WaitThread extends Thread {
        private String lock;

        public WaitThread(String lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    while (isWait) {
                        System.out.println(Thread.currentThread().getName() + "进入代码块");
                        System.out.println(Thread.currentThread().getName() + "开始 wait");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + "结束 wait");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class NotifyThread extends Thread {
        private String lock;

        public NotifyThread(String lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "进入代码块");
                System.out.println(Thread.currentThread().getName() + "开始 notify");
                lock.notify();
                isWait = false;
                System.out.println(Thread.currentThread().getName() + "结束 notify");
            }
        }
    }

}
