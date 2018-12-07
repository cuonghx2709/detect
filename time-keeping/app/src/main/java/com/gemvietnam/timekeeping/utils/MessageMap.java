package com.gemvietnam.timekeeping.utils;

import java.util.HashMap;
import java.util.Map;


public class MessageMap {
    private static Map<String, String> sMessageMap = new HashMap<>();

    static {
        sMessageMap.put("SUCCESS", "Request thành công");
        sMessageMap.put("FAILED", "Request lỗi");
        sMessageMap.put("EXCEPTION", "Unexcepted Exception");

    }

    public static String getMessageValue(String key) {
        return sMessageMap.get(key);
    }

}
