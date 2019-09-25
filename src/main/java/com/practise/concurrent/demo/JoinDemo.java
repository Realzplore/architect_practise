package com.practise.concurrent.demo;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/4
 */
public class JoinDemo {

    public static void main1 (String[] args) {
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread currentThread = new JoinThread(previousThread);
            currentThread.start();
            previousThread = currentThread;
        }
    }

    static class JoinThread extends Thread {
        private Thread thread;

        public JoinThread(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
                System.out.println(thread.getName() + " terminated.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
