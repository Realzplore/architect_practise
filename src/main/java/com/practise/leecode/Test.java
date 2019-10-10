package com.practise.leecode;


import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: realz
 * @package: com.practise
 * @date: 2019-08-23
 * @email: zlp951116@hotmail.com
 */
@Data
public class Test {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public String name;

    boolean isTrue(){
        return true;
    }

    public static void main1(String[] args) {

    }

}
