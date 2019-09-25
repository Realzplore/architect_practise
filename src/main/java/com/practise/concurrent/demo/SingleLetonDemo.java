package com.practise.concurrent.demo;

/**
 * @author: realz
 * @package: com.practise.concurrent
 * @date: 2019-09-21
 * @email: zlp951116@hotmail.com
 */
public class SingleLetonDemo {


    /**
     * 单线程预加载
     */
    static class PreSingleThreadSingleLeton {
        private static PreSingleThreadSingleLeton preSingleThreadSingleLeton = new PreSingleThreadSingleLeton();

        private PreSingleThreadSingleLeton() {
        }
    }


    /**
     * 单线程懒加载
     */
    static class LazySingleThreadSingleLeton {
        private static LazySingleThreadSingleLeton instance = null;

        private LazySingleThreadSingleLeton() {
        }

        public static LazySingleThreadSingleLeton getInstance() {
            if (instance == null) {
                instance = new LazySingleThreadSingleLeton();
            }
            return instance;
        }
    }


    /**
     * 简单多线程
     */
    static class MultiThreadSingleLeton {

        private static MultiThreadSingleLeton instance = null;

        private MultiThreadSingleLeton() {
        }

        public static MultiThreadSingleLeton getInstance() {
            synchronized (MultiThreadSingleLeton.class) {
                if (instance == null) {
                    return new MultiThreadSingleLeton();
                }
                return instance;
            }
        }
    }

    /**
     * 双重检查锁定多线程
     */
    static class MultiThreadSingleLetonV1 {

        /**
         * volatile关键字保证有序性，防止临界区重排序产生未初始化完毕的实例对象
         */
        private static volatile MultiThreadSingleLetonV1 instance = null;

        private MultiThreadSingleLetonV1() {
        }

        public static MultiThreadSingleLetonV1 getInstance() {
            if (instance == null) {
                synchronized (MultiThreadSingleLetonV1.class) {
                    if (instance == null) {
                        return new MultiThreadSingleLetonV1();
                    }
                    return instance;
                }
            }
            return instance;

        }
    }

    /**
     * 基于静态内部类的单例模式
     */
    static class StaticSingleLeton {

        private StaticSingleLeton() {
        }

        private static class StaticSingleLetonHolder {
            static final StaticSingleLeton INSTANCE = new StaticSingleLeton();
        }

        public static StaticSingleLeton getInstance() {
            return StaticSingleLetonHolder.INSTANCE;
        }
    }
}
