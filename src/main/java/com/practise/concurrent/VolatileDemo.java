package com.practise.concurrent;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/5
 */
public class VolatileDemo {
    private static boolean isOver = false;
    public static void main1(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isOver){
                    System.out.println("isOver : " + isOver);
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOver = true;
    }


}
