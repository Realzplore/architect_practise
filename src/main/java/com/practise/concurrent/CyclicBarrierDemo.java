package com.practise.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/11
 */
public class CyclicBarrierDemo {
    private static CyclicBarrier barrier = new CyclicBarrier(6,()->{
        System.out.println("所有运动员入场，裁判员一声令下！！！！！");
    });

    public static void main(String[] args) {
        System.out.println("运动员准备进场，全场欢呼............");

        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (int i = 0; i < 6; i++) {
            executorService.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "运动员，进场");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + "运动员出发");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
