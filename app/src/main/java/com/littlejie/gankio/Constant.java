package com.littlejie.gankio;

/**
 * Gank IO 中使用到的常量
 * Created by littlejie on 2017/3/6.
 */

public class Constant {

    public static final String URL = "http://gank.io/api/";

    public static final String EXTRA_STRING = "string";

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_CACHE = "cache";

    public static final int COUNT = 20;

    /**
     * Gank IO 类目类型，图片和视频需要特殊处理
     */
    public static final class Category {
        public static final String DAY = "每日精选";
        public static final String IMAGE = "福利";
    }
}
