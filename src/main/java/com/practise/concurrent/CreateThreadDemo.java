package com.practise.concurrent;

import java.util.concurrent.*;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/29
 */
public class CreateThreadDemo {
    /**
     * 创建线程一共有三种方式：
     * 1、继承Thread类，重写run()方法
     * 2、实现runnable接口
     * 3、实现callable接口
     */

    public static void main(String[] args) {
        /**
         * 1、继承Thread类
          */
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println("继承Thread类");
                super.run();
            }
        };
        thread.start();

        /**
         * 2、实现Runnable接口
         */
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("实现runnable接口");
            }
        });
        thread1.start();

        /**
         * 3、实现callable接口
         */
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "通过实现callable接口";
            }
        });

        try {
            String result = future.get();
            System.out.println(result);
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
