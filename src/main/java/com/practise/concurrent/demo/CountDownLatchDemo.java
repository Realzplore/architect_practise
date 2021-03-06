package com.practise.concurrent.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/11
 */
public class CountDownLatchDemo {
    private static CountDownLatch startSignal = new CountDownLatch(1);
    private static CountDownLatch endSignal = new CountDownLatch(6);

    public static void main1(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            executorService.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "运动员等待裁判员响哨！！！");
                    startSignal.await();
                    System.out.println(Thread.currentThread().getName() + "正在全力冲刺");
                    Thread.sleep(1000);
                    endSignal.countDown();
                    System.out.println(Thread.currentThread().getName() + "到达终点");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(3000);
        System.out.println("裁判员发号施令啦！！！");
        startSignal.countDown();
        endSignal.await();
        System.out.println("所有运动员到达终点，比赛结束！");
        executorService.shutdown();
    }
}
