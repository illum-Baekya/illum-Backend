package com.gdg.illum.BusinessDistrict.service.utils;

public class CodeUtil {

    public static boolean isSamePrefix(String wholeCode, String prefixCode) {
        return wholeCode.substring(0, 2).equals(prefixCode);
    }

}
