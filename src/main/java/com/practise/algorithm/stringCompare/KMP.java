package com.practise.algorithm.stringCompare;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/17
 * Knuth-Morris-Pratt算法（简称KMP） 核心原理是利用一个“部分匹配表”，跳过已经匹配过的元素。
 */
public class KMP {
    public static void main1(String[] args) {
        String source = "KMP MATCH";
        String object = "TCH";
        System.out.println("source : " + source + ",object : " + object + ", index : " + kmp(source, object));
    }

    /**
     * 若匹配成功，返回成功匹配的第一个下标，匹配失败返回-1
     * @param source 源字符串
     * @param object 比较字符串
     * @return
     */
    private static int kmp(String source, String object) {
        if (object.length() > source.length()) {
            return -1;
        }
        //初始化比较位置
        int index = 0;
        while (index + object.length() <= source.length()) {
            for (int i = 0,j = index; i < object.length(); i++,j++) {
                if (object.charAt(i) != source.charAt(j)) {
                    if (i > 0) {
                        int partMatch = getPartMatch(object.substring(0, i - 1));
                        index =+ (i - partMatch);
                    }
                    break;
                }
                if (i == object.length() - 1) {
                    return index;
                }
            }
            index++;
        }
        return -1;
    }

    /**
     * 获取部分匹配长度
     * @param substring
     * @return
     */
    private static int getPartMatch(String substring) {
        for (int i = substring.length(); i > 0; i--) {
            if (substring.substring(0, i - 1).equals(substring.substring(substring.length() - i, substring.length() - 1))) {
                return i + 1;
            }
        }
        return 0;
    }
}
