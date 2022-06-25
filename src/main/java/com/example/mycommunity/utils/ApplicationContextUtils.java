package com.example.mycommunity.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationContextUtils {
    public static ConfigurableApplicationContext applicationContext;
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}