package com.practise.realz.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.practise.concurrent.thread.callable.CyclicBarrierCallable;
import com.practise.concurrent.thread.callable.RealzCallable;
import com.practise.concurrent.thread.transactionManager.RealzTransactionManager;
import com.practise.realz.domain.User;
import com.practise.realz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: realz
 * @package: com.practise.realz.service
 * @date: 2019-09-23
 * @email: zlp951116@hotmail.com
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private DataSource dataSource;

    public User createUser(User user) {
        if (user.getId() != null) {
            throw new RuntimeException("USER_ERR_001");
        }

        User exist = userMapper.selectByLogin(user.getLogin());
        if (exist != null) {
            throw new RuntimeException("USER_ERR_002");
        }

        userMapper.insert(user);

        return user;
    }

    public void testMultiThread() {
        List<RealzCallable> callables = new ArrayList<>();
        CyclicBarrierCallable<String> realzCallable = new CyclicBarrierCallable(()->{
            User realz = userMapper.selectByLogin("realz");
            realz.setNickname("1");
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("user_id", realz.getId());
            userMapper.update(realz, updateWrapper);
            return "1";
        });
        callables.add(realzCallable);

        CyclicBarrierCallable<Boolean> zeusCallable = new CyclicBarrierCallable<>(() -> {
            Thread.sleep(1000);
            User realz = userMapper.selectByLogin("realz");
            return "1".equals(realz.getNickname());
        });
        callables.add(zeusCallable);

        RealzTransactionManager threadTransactionManager = new RealzTransactionManager(transactionTemplate, callables, 600000, TimeUnit.MILLISECONDS);
        threadTransactionManager.setOnlyOneConnection(true);
        threadTransactionManager.setDataSource(dataSource);
        threadTransactionManager.execute();


        List<Object> result = threadTransactionManager.get();
        System.out.println("result :" + result.toString());
    }
}
