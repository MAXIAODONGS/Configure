package com.maxd.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static Map<String, String> school = new HashMap<>();

    public static void add() {
        school.put("10704", "西安科技大学");
        school.put("11664", "西安邮电大学");
        school.put("13123", "陕西国际商贸学院");
        school.put("10716", "陕西中医药大学");
        school.put("10722", "咸阳师范学院");
        school.put("12712", "西安欧亚学院");
        school.put("10697", "西北大学");
        school.put("10000", "新路网络");

    }

    public static String getName(String key) {
        add();
        String s = school.get(key);
        if (s == null) {
            return key;
        } else {
            return s;
        }
    }

}
