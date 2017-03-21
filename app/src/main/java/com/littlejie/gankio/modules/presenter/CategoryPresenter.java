package com.littlejie.gankio.modules.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.littlejie.gankio.Constant;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.modules.contract.ICategoryContract;
import com.littlejie.gankio.modules.model.CategoryModel;
import com.littlejie.gankio.modules.model.DayPublishModel;

import java.util.ArrayList;
import java.util.List;

/**
 * CategoryPresenter
 * Created by littlejie on 2017/3/12.
 */

public class CategoryPresenter implements ICategoryContract.Presenter {

    private static final String TAG = CategoryPresenter.class.getSimpleName();
    private ICategoryContract.View mView;
    private ICategoryContract.Model mModel;

    private String mCategory;
    private int mCurrentPage;
    private List<DataInfo> mDataList = new ArrayList<>();

    public CategoryPresenter(ICategoryContract.View view) {
        mView = view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("data", (ArrayList<DataInfo>) mDataList);
        outState.putInt("page", mCurrentPage);
    }

    @Override
    public void initData(Bundle bundle, Bundle savedInstanceState) {
        mCategory = mView.getCategory();
        //初始化 Model
        initModel();
        mCurrentPage = getDefaultPage();
        //恢复保存的数据
        if (savedInstanceState != null) {
            mDataList = savedInstanceState.getParcelableArrayList("data");
            //恢复之前保存的请求页数
            mCurrentPage = savedInstanceState.getInt("page", mCurrentPage);
        }
        Log.d(TAG, "initData: category = " + mCategory + ";page = " + mCurrentPage + ";data = " + mDataList);
    }

    private void initModel() {
        if (Constant.Category.DAY.equals(mCategory)) {
            mModel = new DayPublishModel(this);
        } else {
            mModel = new CategoryModel(this);
        }
    }

    private int getDefaultPage() {
        if (TextUtils.isEmpty(mCategory)
                || !Constant.Category.DAY.equals(mCategory)) {
            return 1;
        }
        return 0;
    }

    @Override
    public void process(Bundle savedInstanceState) {
        if (mDataList != null && mDataList.size() != 0) {
            mView.updateList(mDataList);
        } else {
            mModel.loadData(mCategory, mCurrentPage);
        }
    }

    @Override
    public void pullToRefresh(final boolean isLoadMore) {
        //如果为下拉刷新，则获取第一页数据
        //否则，页数递增
        mCurrentPage = isLoadMore ? ++mCurrentPage : getDefaultPage();
        Log.d(TAG, "pullToRefresh:" + mCurrentPage);
        mModel.loadData(mCategory, mCurrentPage);
    }

    @Override
    public void loadData(List<DataInfo> dataList) {
        if (mView.isLoadMore()) {
            mDataList.addAll(dataList);
        } else {
            mDataList = dataList;
        }
        mView.updateList(mDataList);
    }

    @Override
    public void fail(String msg) {
        mView.showMessage(msg);
    }

}
