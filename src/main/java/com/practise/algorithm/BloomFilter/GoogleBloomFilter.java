package com.practise.algorithm.BloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.HashSet;
import java.util.Random;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/17
 */
public class GoogleBloomFilter {
    static int sizeOfNumberSet =
//            Integer.MAX_VALUE >> 4;
    10000000;
    static Random generator = new Random();

    public static void main(String[] args) {
        int error = 0;
        HashSet<Integer> hashSet = new HashSet<>();
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), sizeOfNumberSet);

        long start = System.currentTimeMillis();
        for (int i = 0; i < sizeOfNumberSet; i++) {
            int number = generator.nextInt();
            if (filter.mightContain(number) != hashSet.contains(number)) {
                error++;
            }
            filter.put(number);
            hashSet.add(number);
        }
        long end = System.currentTimeMillis();
        System.out.println("during time: " + (end - start) + " ms,Error Count: " + error + ",error rate : " + String.format("%f", (float) error / (float) sizeOfNumberSet));
    }

}
