package com.practise.concurrent.thread.transactionManager;

import com.practise.concurrent.thread.callable.RealzCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    private boolean onlyOneConnection = false;
    private DataSource dataSource;

    public RealzTransactionManager(TransactionTemplate transactionTemplate, List<RealzCallable> callableList, long timeout, TimeUnit timeUnit) {
        super(transactionTemplate);
        if (callableList == null || callableList.isEmpty()) {
            this.callableList = new ArrayList<>();
            this.cyclicBarrier = new CyclicBarrier(0);
            this.executorService = Executors.newSingleThreadExecutor();
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
            this.executorService = Executors.newFixedThreadPool(callableList.size());
            this.completionService = new ExecutorCompletionService(executorService);
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
            if (onlyOneConnection && dataSource != null) {
                Connection connection = null;
                try {
                    connection = dataSource.getConnection();
                    connection.setAutoCommit(false);
                    if (callableList != null && !callableList.isEmpty()) {
                        for (RealzCallable callable : callableList) {
                            this.completionService.submit(callable);
                        }
                        executed = true;
                    }
                    connection.commit();
                } catch (SQLException e) {
                    log.info("数据库错误,code:{}", e.getErrorCode());
                    if (connection != null) {
                        try {
                            connection.rollback();
                        } catch (SQLException ex) {
                        }
                    }
                    executed = false;
                }
            } else {
                if (callableList != null && !callableList.isEmpty()) {
                    for (RealzCallable callable : callableList) {
                        this.completionService.submit(callable);
                    }
                    executed = true;
                }
            }

        }
    }

    @Override
    public synchronized List<Object> get() {
        List<Object> result = new ArrayList<>();

        if (executed) {
            try {
                for (int i = 0; i < callableList.size(); i++) {
                    result.add(completionService.take().get());
                }
            } catch (Exception e) {
                log.error("子线程错误:{}", e.getMessage());
            } finally {
                executorService.shutdown();
            }
        }
        return result;
    }

    public boolean isOnlyOneConnection() {
        return onlyOneConnection;
    }

    public void setOnlyOneConnection(boolean onlyOneConnection) {
        this.onlyOneConnection = onlyOneConnection;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
