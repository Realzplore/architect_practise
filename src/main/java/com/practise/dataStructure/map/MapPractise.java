package com.practise.dataStructure.map;

import lombok.Data;

import java.util.*;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/15
 * <p>
 * 通用Map：
 * HashMap、HashTable、Properties、LinkedHashMap、IdentityHashMap、TreeMap、WeakHashMap、ConcurrentHashMap
 * <p>
 * 专用Map：
 * java.util.jar.Attributes、
 * javax.print.attribute.standard.PrinterStateReasons、
 * java.security.Provider、
 * java.awt.RenderingHints、
 * javax.swing.UIDefaults
 * <p>
 * 自行实现Map：一个用于帮助实现自己的Map类的抽象类
 * AbstractMap
 */
@Data
public class MapPractise {

    /**
     * 最常用map，非同步，只允许一条键为null，允许多条值为null
     */
    private static Map<String, String> hashMap;

    /**
     * 能够根据保存记录的键值进行排序，默认升序，也可以指定排序的比较器，因此不允许TreeMap的key为null
     */
    private static Map<String, String> treeMap;

    /**
     * 与HashMap类似，不同的是key和value均不能为null，线程同步，一次只允许一个线程写HashTable。
     */
    private static Map<String, String> hashTable;

    /**
     * 保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的肯定是时先插入的。
     */
    private static Map<String, String> linkedHashMap;

    private static MyComparator comparator = new MyComparator();
    static {
        hashMap = new HashMap<>();
        hashMap.put("name", "Smith");
        hashMap.put("age", "18");

        treeMap = new TreeMap<>(comparator);
        treeMap.put("name", "Smith");
        treeMap.put("age", "18");

        hashTable = new Hashtable<>();
        hashTable.put("name", "Smith");
        hashTable.put("age", "18");

        linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("name", "Smith");
        linkedHashMap.put("age", "18");
    }

    public static void main(String args[]) {
        System.out.println("hashMap : " + hashMap);
        System.out.println("treeMap : " + treeMap);
        System.out.println("hashTable : " + hashTable);
        System.out.println("linkedHashMap : " + linkedHashMap);

        hashMap.clear();
        treeMap.clear();
        hashTable.clear();
        linkedHashMap.clear();

        /**
         * 比较for循环、迭代器效率
         * 比较keySet、entrySet效率
         * 初始化10W次赋值
         */
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            map.put(i, i);
        }

        /** for循环，keySet */
        long start = System.currentTimeMillis();
        for (Integer key : map.keySet()) {
            map.get(key);
        }
        long end = System.currentTimeMillis();
        System.out.println("for循环，keySet：" + (end - start) + " ms");

        /** for循环，entrySet */
        start = System.currentTimeMillis();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
        end = System.currentTimeMillis();
        System.out.println("for循环，entrySet：" + (end - start) + " ms");

        /** 迭代器，keySet */
        start = System.currentTimeMillis();
        Iterator<Integer> iterator = map.keySet().iterator();
        Integer key;
        while (iterator.hasNext()) {
            key = iterator.next();
            map.get(key);
        }
        end = System.currentTimeMillis();
        System.out.println("迭代器，keySet：" + (end - start) + " ms");

        /** 迭代器，entrySet */
        start = System.currentTimeMillis();
        Iterator<Map.Entry<Integer, Integer>> iterator1 = map.entrySet().iterator();
        Map.Entry<Integer,Integer> entry;
        while (iterator1.hasNext()) {
            entry = iterator1.next();
            entry.getKey();
            entry.getValue();
        }
        end = System.currentTimeMillis();
        System.out.println("迭代器，entrySet：" + (end - start) + " ms");

    }

}

class MyComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1.hashCode() > o2.hashCode()) {
            return 1;
        } else if (o1.hashCode() < o2.hashCode()) {
            return -1;
        }
        return 0;
    }
}
