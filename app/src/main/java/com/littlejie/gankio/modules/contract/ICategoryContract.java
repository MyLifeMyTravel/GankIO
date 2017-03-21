package com.littlejie.gankio.modules.contract;

import android.os.Bundle;

import com.littlejie.gankio.entity.DataInfo;

import java.util.List;

/**
 * Created by littlejie on 2017/3/12.
 */

public interface ICategoryContract {

    interface View {

        /**
         * 获取当前Item的类型
         *
         * @return
         */
        String getCategory();

        /**
         * 更新列表数据
         *
         * @param dataList
         */
        void updateList(List<DataInfo> dataList);

        /**
         * 垂直偏移距离
         *
         * @param offset
         */
        void setVerticalOffset(int offset);

        /**
         * 判断是下拉刷新还是上拉加载更多
         *
         * @return
         */
        boolean isLoadMore();

        /**
         * 停止下拉加载更多
         */
        void stopPullRefresh();

        /**
         * 停止上拉加载更多
         */
        void stopLoadMoreRefresh();

        /**
         * 跳转到 WebViewActivity
         *
         * @param url
         */
        void toWebActivity(String url);

        void showMessage(int msg);

        void showMessage(CharSequence msg);

    }

    interface Presenter {

        /**
         * 保存状态
         *
         * @param outState
         */
        void onSaveInstanceState(Bundle outState);

        /**
         * 初始化数据
         *
         * @param bundle             参数
         * @param savedInstanceState
         */
        void initData(Bundle bundle, Bundle savedInstanceState);

        void process(Bundle savedInstanceState);

        /**
         * 请求分类数据
         *
         * @param isLoadMore 是否是加载更多
         */
        void pullToRefresh(boolean isLoadMore);

        /**
         * 加载数据，通过 Model 中对应方法获取
         *
         * @param dataList
         */
        void loadData(List<DataInfo> dataList);

        /**
         * 网络请求失败
         *
         * @param msg
         */
        void fail(String msg);
    }

    /**
     * 普通类目的Model
     */
    interface Model {

        /**
         * 请求数据数据
         *
         * @param category
         * @param page
         */
        void loadData(String category, int page);
    }

    /**
     * 每日精选类目的Model
     */
    interface DayPublishModel extends Model {

        /**
         * 获取发布干货的日期
         */
        void loadPublishDays();

//        /**
//         * 加载每日精选数据
//         *
//         * @param index 日期索引
//         */
//        void loadDayPush(int index);
    }
}
