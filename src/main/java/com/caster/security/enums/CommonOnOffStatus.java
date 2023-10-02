package com.caster.security.enums;

import org.apache.commons.lang3.StringUtils;

public enum CommonOnOffStatus {
    ON("1"),
    OFF("0");

    private String code;

    CommonOnOffStatus(String code) {
        this.code = code;
    }

    public static boolean isOn(String status) {
        return StringUtils.equals(ON.code, status);
    }

    public static boolean isOn(Integer status) {
        return ON.code.equals(String.valueOf(status));
    }

    public static boolean isOff(String status) {
        return StringUtils.equals(OFF.code, status);
    }

    public static boolean isOff(Integer status) {
        return OFF.code.equals(String.valueOf(status));
    }

    public String getCode() {
        return code;
    }

    public static String getName(String status) {
        if (isOn(status))
            return "啟用";
        else if (isOff(status))
            return "停用";
        else
            return "錯誤";
    }

    public static String getName(Integer status) {
        if (isOn(status))
            return "啟用";
        else if (isOff(status))
            return "停用";
        else
            return "錯誤";

    }
}
