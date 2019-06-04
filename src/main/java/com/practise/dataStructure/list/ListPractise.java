package com.practise.dataStructure.list;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/14
 * List不同于Set，List允许重复，允许值为null。
 */
@Data
public class ListPractise {
    /**
     * 无参构造函数初始化Object数组为{}，默认数组长度为10，每次add都会调用Arrays.copy来扩展数组长度，容易造成性能浪费
     */
    private static ArrayList<Integer> arrayList = new ArrayList<>();

    /**
     * 有参构造函数直接根据传参来确定初始化数组长度，避免了数组扩展前的性能浪费
     */
    private static ArrayList<Integer> arrayList1 = new ArrayList<>(5);

    /**
     * Vector继承ArrayList，是ArrayList的线程安全版，方法前加了synchronized锁，其他实现逻辑相同。
     */
    private static Vector<Integer> vector = new Vector<>();

    /**
     * 本质上是链表，一个双向链表，内部维护了三个变量，头节点，尾节点以及链表长度。节点为内部静态类Node
     */
    private static LinkedList<Integer> linkedList = new LinkedList<>();


    static {
        /**
         * add函数先确认当前数组是否充足，若不足则将原长扩展50%后与新长度进行比较。
         */
        arrayList.add(5);

        /**
         * 先检查index是否越界，然后将index后的值前移一位，并将最后一位置为null。
         */
        arrayList.remove(0);

    }
}
