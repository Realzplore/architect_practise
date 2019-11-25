package com.practise.concurrent.threadLocal;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author: realz
 * @package: com.practise.concurrent.threadLocal
 * @date: 2019-10-10
 * @email: zlp951116@hotmail.com
 */
public enum ThreadSpecificSecureRandom {
    INSTANCE;

    final static ThreadLocal<SecureRandom> SECURE_RANDOM = ThreadLocal.withInitial(() -> {
        SecureRandom scrd;
        try {
            scrd = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            scrd = new SecureRandom();
        }
        scrd.nextBytes(new byte[20]);
        return scrd;
    });

    public int nextInt(int upperBound) {
        SecureRandom secureRandom = SECURE_RANDOM.get();
        return secureRandom.nextInt(upperBound);
    }


    public void setSeed(long seed) {
        SecureRandom secureRandom = SECURE_RANDOM.get();
        secureRandom.setSeed(seed);
    }
}
