package com.practise.dataStructure.queue;

import lombok.Data;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/16
 */
@Data
public class QueuePractise {

    /**
     * 内置不阻塞队列
     * 实质上维护了一个有序列表。根据Comparable接口或者传递给构造函数的Comparator接口来实现排序。
     */
    private static Queue priorityQueue;

    /**
     * 内置不阻塞队列
     * 基于链接节点的、线程安全的队列。并发访问无需同步，并发控制使用CAS算法。
     */
    private static Queue concurrentLinkedQueue;

    /**
     * 一个由链接节点支持的可选有界队列。并发控制使用锁机制。
     */
    private static Queue linkedBlockingQueue;
    static {
        priorityQueue = new PriorityQueue();
        concurrentLinkedQueue = new ConcurrentLinkedDeque();
    }


}
