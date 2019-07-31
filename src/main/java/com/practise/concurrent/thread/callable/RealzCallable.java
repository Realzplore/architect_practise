package com.practise.concurrent.thread.callable;

import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 多线程抽象类
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-12
 * @email: liping.zheng@huilianyi.com
 */
public abstract class RealzCallable<T> implements Callable<T> {

    protected TransactionTemplate transactionTemplate;
    protected CyclicBarrier cyclicBarrier;
    protected long timeout;
    protected TimeUnit timeUnit;


    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
