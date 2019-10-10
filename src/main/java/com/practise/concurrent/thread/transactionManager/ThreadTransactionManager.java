package com.practise.concurrent.thread.transactionManager;

import com.practise.concurrent.thread.callable.RealzCallable;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.*;

/**
 * 多线程事务管理抽象类
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-12
 * @email: liping.zheng@huilianyi.com
 */
public abstract class ThreadTransactionManager implements TransactionManager {

    protected TransactionTemplate transactionTemplate;
    protected CyclicBarrier cyclicBarrier;
    protected ExecutorService executorService;
    protected CompletionService completionService;
    protected long timeout = 1000L;
    protected TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    protected boolean executed = false;

    public ThreadTransactionManager(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public ThreadTransactionManager(TransactionTemplate transactionTemplate, CyclicBarrier cyclicBarrier) {
        this.transactionTemplate = transactionTemplate;
        this.cyclicBarrier = cyclicBarrier;
    }

    public abstract Callable getCallable(RealzCallable callable);
}
