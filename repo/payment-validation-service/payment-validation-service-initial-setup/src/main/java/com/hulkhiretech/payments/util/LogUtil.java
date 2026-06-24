package com.hulkhiretech.payments.util;

public class LogUtil {

    public static String maskCustomer(String customerId){

        if(customerId == null)
            return null;

        return "****" +
                customerId.substring(
                Math.max(0, customerId.length()-4));
    }
}