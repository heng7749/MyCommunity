package com.example.mycommunity.utils;

import java.util.regex.Pattern;

public class ParamFormatUtil {

    public static boolean checkAccount(String account){
        boolean isValid = false;
        if (account != null) {
            Pattern pattern = Pattern.compile("[A-Za-z]\\w{5,14}");
            isValid = pattern.matcher(account).matches();
        }
        return isValid;
    }

    public static boolean checkPassword(String password){
        boolean isValid = false;
        if (password != null) {
            Pattern pattern = Pattern.compile("(\\w|.){6,15}");
            isValid = pattern.matcher(password).matches();
        }
        return isValid;
    }

}
