package com.practise.concurrent.thread.transactionManager;

import com.practise.concurrent.thread.callable.RealzCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 多线程事务管理类
 *
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-12
 * @email: liping.zheng@huilianyi.com
 */
public class RealzTransactionManager extends ThreadTransactionManager {
    private static final Logger log = LoggerFactory.getLogger(RealzTransactionManager.class);

    private List<RealzCallable> callableList;

    public RealzTransactionManager(TransactionTemplate transactionTemplate, List<RealzCallable> callableList, long timeout, TimeUnit timeUnit) {
        super(transactionTemplate);
        if (callableList == null || callableList.size() == 0) {
            this.callableList = new ArrayList<>();
            this.cyclicBarrier = new CyclicBarrier(0);
            this.completionService = new ExecutorCompletionService(Executors.newSingleThreadExecutor());
        } else {
            if (timeout > 0) {
                this.timeout = timeout;
            }
            if (timeUnit != null) {
                this.timeout = timeout;
            }
            this.cyclicBarrier = new CyclicBarrier(callableList.size());
            this.callableList = new ArrayList<>();
            for (RealzCallable callable : callableList) {
                this.callableList.add(this.getCallable(callable));
            }
            this.completionService = new ExecutorCompletionService(Executors.newFixedThreadPool(callableList.size()));
        }
    }

    @Override
    public RealzCallable getCallable(RealzCallable callable) {
        callable.setTransactionTemplate(this.transactionTemplate);
        callable.setCyclicBarrier(this.cyclicBarrier);
        callable.setTimeout(this.timeout);
        callable.setTimeUnit(this.timeUnit);
        return callable;
    }


    @Override
    public int size() {
        if (callableList == null) {
            return 0;
        }else {
            return callableList.size();
        }
    }

    @Override
    public synchronized void execute() {
        if (!executed) {
            if (callableList != null && callableList.size() > 0) {
                for (RealzCallable callable : callableList) {
                    this.completionService.submit(callable);
                }
                executed = true;
            }
        }
    }

    @Override
    public synchronized List<Object> get() {
        List<Exception> exceptionResult = new ArrayList<>();
        List<Object> result = new ArrayList<>();

        if (executed) {
            for (int i = 0; i < callableList.size(); i++) {
                try {
                    result.add(completionService.take().get());
                } catch (RuntimeException e) {
                    log.error("子线程错误:{}", e.getMessage());
//                    exceptionResult.add(e);
                    throw new RuntimeException("message:" + e.getMessage());
                }catch (Exception e) {
                    log.error("子线程错误:{}", e.getMessage());
//                    exceptionResult.add(e);
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        if (exceptionResult.size() > 0) {
            throw new RuntimeException(exceptionResult.stream().map(Throwable::getMessage).collect(Collectors.joining()));
        }
        return result;
    }
}
