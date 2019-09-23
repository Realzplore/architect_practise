package com.practise.algorithm.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/21
 * 背包问题
 * 10个物品，不同的重量和价值，在有限的重量下选取最有价值的物品集合
 */
public class Greedy {
    private static Double[] weight = {35d, 30d, 45d, 15d, 60d, 50d, 25d, 10d, 55d, 40d};
    private static Double[] value = {10d, 20d, 30d, 30d, 40d, 50d, 20d, 30d, 10d, 50d};

    private static Double M = 200d;
    public static void main1(String[] args) {
        List<Map<String, Double>> results = getMaxValue(M, weight, value);
        System.out.println(results);
    }

    private static List<Map<String, Double>> getMaxValue(Double m, Double[] weight, Double[] value) {
        List<Map<String, Double>> results = new ArrayList<>();
        if (weight.length == 0 || weight.length != value.length) {
            return results;
        }
        Double[][] perValue = new Double[2][weight.length];
        for (int i = 0; i < weight.length; i++) {
            perValue[0][i] = weight[i];
            perValue[1][i] = value[i] / weight[i];
        }
        //对perValue快速排序递归实现
        quickSort(perValue, 0, weight.length - 1);


        double sumM = 0d;
        int i = weight.length - 1;

        while (sumM < m && i >= 0) {
            Map<String, Double> result = new HashMap<>();
            if (sumM + perValue[0][i] <= m) {
                sumM += perValue[0][i];
                result.put("weight", perValue[0][i]);
                result.put("unit value", perValue[1][i]);
                i--;
            } else {
                result.put("weight", m - sumM);
                result.put("unit value", perValue[1][i]);
                sumM = m;
                i--;
            }
            results.add(result);
        }

        return results;
    }

    private static void quickSort(Double[][] perValue, int left, int right) {
        if (left < right) {
            int middle = getMiddle(perValue, left, right);
            System.out.println("left:" + left + ",middle:" + middle + ",right:" + right);
            quickSort(perValue, left, middle - 1);
            quickSort(perValue, middle + 1, right);
        }
    }

    private static int getMiddle(Double[][] perValue, int left, int right) {

        double[] index = {perValue[0][left], perValue[1][left]};

        while (left < right) {
            while (left < right && perValue[1][right] >= index[1]) {
                right--;
            }
            perValue[0][left] = perValue[0][right];
            perValue[1][left] = perValue[1][right];
            while (left < right && perValue[1][left] <= index[1]) {
                left++;
            }
            perValue[0][right] = perValue[0][left];
            perValue[1][right] = perValue[1][left];
        }
        perValue[0][left] = index[0];
        perValue[1][left] = index[1];
        return left;
    }
}
