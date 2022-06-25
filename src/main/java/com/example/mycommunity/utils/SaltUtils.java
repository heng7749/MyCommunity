package com.example.mycommunity.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class SaltUtils {
    public static String getSalt(int count){
        return RandomStringUtils.randomAscii(count);
    }
}
