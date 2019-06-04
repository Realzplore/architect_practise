package com.practise.dataStructure.set;

import lombok.Data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/14
 * Set继承Collection，内部元素使用迭代器进行存储。
 *
 * 看到array，就要想到角标。
 *
 * 看到link，就要想到first，last。
 *
 * 看到hash，就要想到hashCode,equals.
 *
 * 看到tree，就要想到两个接口。Comparable，Comparator。
 */
@Data
public class SetPractise {
    /**
     * 哈希表存放的是哈希值。HashSet使用hashCode和equals保证不重复的规则。
     * 若hashCode和equals都相同，则认为两个对象相等
     * 若hashCode相等但是equals不相等，则认为两个对象不相等，存储在同一个hashCode下（放在同一个哈希桶里）
     * 不重复，无序
     */
    private static Set<String> set = new HashSet<>();

    /**
     * 1、让存入的元素自定义比较规则。
     * 需要元素实现Comparable接口，重写compareTo方法。
     *
     * 2、给TreeSet指定排序规则。
     * 若元素自身不具备比较性，那么此时可以让容器自身具备，需要一个类实现接口Comparator，
     * 重写compare方法，并将该接口的子类作为参数传递给TreeSet集合的构造方法。
     *
     * 注意：当Comparable方式和Comparator方式同时存在时，以Comparator为主。
     */
    private static Set<Person> set1 = new TreeSet<>();

    static {
        set.add("test 1");
        set.add("test 2");
        set.add("test 3");
        set.add("test 4");

        System.out.println(set);

        set1.add(new Person("张三", 18, "男"));
        set1.add(new Person("李四", 22, "男"));
        set1.add(new Person("王五", 28, "女"));
        set1.add(new Person("赵六", 32, "男"));
    }

    public static void main(String args[]) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

@Data
class Person implements Comparable {

    private String name;

    private int age;

    private String gender;

    public Person(String name, int age,String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    @Override
    public int compareTo(Object o) {
        Person p = (Person) o;
        if (this.age > p.getAge()) {
            return 1;
        } else if (this.age < p.getAge()) {
            return -1;
        }
        return this.name.compareTo(p.getName());
    }

}


