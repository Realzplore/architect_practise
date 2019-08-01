package com.practise.concurrent.thread.callable;

import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 有序并发线程类
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-12
 * @email: liping.zheng@huilianyi.com
 */
public class CountDownLatchCallable<T> extends RealzCallable<T> {
    private Callable<T> callable;
    private List<CountDownLatch> waitLatch;
    private CountDownLatch countDownLatch;

    public CountDownLatchCallable(Callable<T> callable, List<CountDownLatch> waitLatch, CountDownLatch countDownLatch) {
        this.callable = callable;
        this.waitLatch = waitLatch;
        this.countDownLatch = countDownLatch;
    }

    protected void await() {
        if (!CollectionUtils.isEmpty(waitLatch)) {
            for (CountDownLatch wait : waitLatch) {
                try {
                    if (!wait.await(timeout, timeUnit)) {
                        throw new RuntimeException("子线程countDownLatch.await等待超时");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("子线程中断，进行回滚");
                }
            }
        }
    }

    protected void countdown() {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    @Override
    public T doInTransaction(TransactionStatus transactionStatus) {
        try {
            await();
            T object = callable.call();
            countdown();
            cyclicBarrier.await(timeout, timeUnit);
            return object;
        } catch (Exception e) {
            throw new RuntimeException("线程执行错误 : " + e.getMessage());
        }
    }
}
