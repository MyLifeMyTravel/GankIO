package com.littlejie.gankio.utils;

/**
 * Created by littlejie on 2017/3/12.
 */

public class TimeUtil {

    /**
     * 干货 api 接口获取到的时间格式为： 2017-03-11T11:43:50.30Z
     * 该方法用于处理此时间格式
     *
     * @param time
     * @return
     */
    public static String convertTime(String time) {
        String result = time.replace("T", " ");
        int index = result.indexOf(".");
        if (index == -1) {
            return result;
        }
        return result.substring(0, index);
    }
}
