package com.practise.concurrent.thread.callable;

import com.practise.concurrent.thread.CyclicTransactionCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.concurrent.Callable;

/**
 * 无序并发线程类
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-12
 * @email: liping.zheng@huilianyi.com
 */
public class CyclicBarrierCallable<T> extends RealzCallable<T> {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierCallable.class);
    private Callable<T> callable;

    public CyclicBarrierCallable(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public T call() {
        return transactionTemplate.execute(new CyclicTransactionCallback<T>() {
            @Override
            public T doInTransactionWithResult(TransactionStatus status) {
                try {
                    T object = callable.call();
                    cyclicBarrier.await(timeout, timeUnit);
                    return object;
                } catch (Exception e) {
                    log.error("系统错误, message:{}", e.getMessage());
                    throw new RuntimeException("线程执行错误 : " + e.getMessage());
                }
            }
        });
    }
}
