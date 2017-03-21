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
        void offsetVertical(int offset);

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
        void loadCategory(boolean isLoadMore);
    }
}
