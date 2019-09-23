package com.practise.leecode;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: realz
 * @package: com.practise
 * @date: 2019-08-23
 * @email: zlp951116@hotmail.com
 */
public class Test {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    boolean isTrue(){
        return true;
    }


}
