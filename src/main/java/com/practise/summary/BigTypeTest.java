package com.practise.summary;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 包装类型测试
 * @author: realz
 * @package: com.practise.summary
 * @date: 2019-10-25
 * @email: zlp951116@hotmail.com
 */
public class BigTypeTest {

    public static void main1(String[] args) {
        Integer x = 1;
        Integer y = 1;
        Integer a = new Integer(1);
        Integer b = new Integer(1);
        System.out.println(x == y);
        System.out.println(a == b);


        BigDecimal f1 = new BigDecimal("1.0");
        BigDecimal f2 = new BigDecimal(1.0f);
        BigDecimal f3 = BigDecimal.valueOf(1.0f);

        System.out.println(f1 == f2);
        System.out.println(f1 == f3);

        BigInteger bigInteger = new BigInteger("111");
    }
}
