package com.littlejie.gankio.modules.contract;

import android.os.Bundle;

import com.littlejie.gankio.entity.DataInfo;

import java.util.List;

/**
 * Created by littlejie on 2017/3/12.
 */

public interface ICategoryContact {

    interface View {

        /**
         * 更新列表数据
         *
         * @param dataList
         */
        void updateData(List<DataInfo> dataList);

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
         * 初始化数据
         *
         * @param bundle
         */
        void initData(Bundle bundle);

        boolean isImageCategory();

        /**
         * 请求分类数据
         *
         * @param isLoadMore 是否是加载更多
         */
        void loadCategory(boolean isLoadMore);
    }
}
