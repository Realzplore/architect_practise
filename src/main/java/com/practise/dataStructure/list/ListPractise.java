package com.practise.dataStructure.list;

import lombok.Data;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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

    public static void main(String[] args) {
//        forTest();
//        forVector();
        forEnsureCapacity();
    }

    public static void forTest() {
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            list1.add("A");
        }

        long startTime,endTime;

        //for循环
        startTime = System.currentTimeMillis();
        for (int i = 0; i < list1.size(); i++) {
            System.out.print(list1.get(i));
        }
        System.out.print("\n");
        endTime = System.currentTimeMillis();
        System.out.println("for循环耗时： " + (endTime - startTime));

        //foreach
        startTime = System.currentTimeMillis();
        for (String i : list1) {
            System.out.print(i);
        }
        System.out.print("\n");
        endTime = System.currentTimeMillis();
        System.out.println("foreach耗时： " + (endTime - startTime));

        //迭代器
        startTime = System.currentTimeMillis();
        Iterator it = list1.iterator();
        while (it.hasNext()) {
            String obj = (String) it.next();
            System.out.print(obj);
        }
        System.out.print("\n");
        endTime = System.currentTimeMillis();
        System.out.println("迭代器耗时：" + (endTime - startTime));
    }

    private static void forVector() {
        Vector vector = new Vector();
    }

    private static void forEnsureCapacity() {
        long startTime = System.currentTimeMillis();
        ArrayList<String> str1 = new ArrayList<>();
        str1.ensureCapacity(200000000);
        for (int i = 0; i < 200000000; i++) {
            str1.add("A");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("add time :".concat(String.valueOf(endTime - startTime)).concat(" ms"));



    }

}
