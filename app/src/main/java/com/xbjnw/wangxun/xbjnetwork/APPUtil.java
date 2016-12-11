package com.xbjnw.wangxun.xbjnetwork;

/**
 * Created by wangxun on 16/12/10.
 */

public class APPUtil {
    public static String getString(int stringId) {
        try {
            return MyApplication.getInstance().getString(stringId);
        } catch (Exception e) {
            BBLog.w("wenba", e);
            return null;
        }
    }
}
