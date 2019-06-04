package com.practise.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/14
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String result = futureTaskTest();
        System.out.println(result);

    }

    private static String futureTaskTest() throws ExecutionException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<String> futureTask = new FutureTask(()->{
            Thread.sleep(3000);
            throw new RuntimeException("执行FutureTask失败");
//            System.out.println("执行FutureTask完毕,time: " + String.valueOf(System.currentTimeMillis() - startTime));
//            return null;
        });
        FutureTask<String> futureTask1 = new FutureTask<>(() ->{
            Thread.sleep(3000);
            System.out.println("执行FutureTask1完毕,time:" + String.valueOf(System.currentTimeMillis() - startTime));
            return null;
        });
        executorService.execute(futureTask);
        executorService.execute(futureTask1);

        String futureTaskResult = futureTask.get();

        return "主线程执行完毕,time:" + String.valueOf(System.currentTimeMillis() - startTime);
    }
}
