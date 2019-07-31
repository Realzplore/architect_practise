package com.practise.concurrent.thread.transactionManager;

import java.util.List;

/**
 * 多线程事务管理接口
 * @author: liping.zheng
 * @package: com.handchina.yunmart.artemis.organization.util.thread
 * @date: 2019-07-12
 * @email: liping.zheng@huilianyi.com
 */
public interface TransactionManager {
    int size();

    void execute();

    List<Object> get();
}
