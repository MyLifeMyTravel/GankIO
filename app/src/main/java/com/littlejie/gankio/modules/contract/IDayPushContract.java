package com.littlejie.gankio.modules.contract;

import android.os.Bundle;

import com.littlejie.gankio.entity.DataInfo;

import java.util.List;

/**
 * 每日精选
 * Created by littlejie on 2017/3/20.
 */

public interface IDayPushContract {

    interface View {

        void update(List<DataInfo> dataList);

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

        void initData(Bundle bundle);

        void process();

        /**
         * 获取发布干货的日期
         */
        void loadPublishDays();

        /**
         * 加载每日精选数据
         *
         * @param isLoadMore 是否加载更多
         */
        void loadDayPush(boolean isLoadMore);
    }
}
