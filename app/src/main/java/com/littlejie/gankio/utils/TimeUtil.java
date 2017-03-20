package com.littlejie.gankio.utils;

import com.littlejie.gankio.entity.DataInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by littlejie on 2017/3/12.
 */

public class TimeUtil {

    public static List<DataInfo> convertTime(List<DataInfo> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            DataInfo data = dataList.get(i);
            data.setPublishedTime(TimeUtil.convertTime(data.getPublishedTime()));
            dataList.set(i, data);
        }
        return dataList;
    }

    /**
     * 干货 api 接口获取到的时间格式为： 2017-03-11T11:43:50.30Z
     * 该方法用于处理此时间格式
     *
     * @param time
     * @return
     */
    public static String convertTime(String time) {
        return time.substring(0, time.indexOf("T"));
//        String result = time.replace("T", " ");
//        int index = result.indexOf(".");
//        if (index == -1) {
//            return result;
//        }
//        return result.substring(0, index);
    }

    public static String getDate(long mills) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date(mills));
    }
}
