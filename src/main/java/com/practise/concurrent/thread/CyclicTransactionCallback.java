package com.practise.concurrent.thread;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;


/**
 *
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-16
 * @email: liping.zheng@huilianyi.com
 */
public abstract class CyclicTransactionCallback<T> implements TransactionCallback<T> {

    @Override
    public T doInTransaction(TransactionStatus transactionStatus) {
        return this.doInTransactionWithResult(transactionStatus);
    }

    public abstract T doInTransactionWithResult(TransactionStatus status);
}
