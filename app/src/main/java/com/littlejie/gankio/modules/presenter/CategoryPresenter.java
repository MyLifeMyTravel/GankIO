package com.littlejie.gankio.modules.presenter;

import android.os.Bundle;
import android.util.Log;

import com.littlejie.gankio.Constant;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.GankInfo;
import com.littlejie.gankio.exception.GankException;
import com.littlejie.gankio.http.ApiService;
import com.littlejie.gankio.modules.contract.ICategoryContract;
import com.littlejie.gankio.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * CategoryPresenter，省略了M层
 * Created by littlejie on 2017/3/12.
 */

public class CategoryPresenter implements ICategoryContract.Presenter {

    private static final String TAG = CategoryPresenter.class.getSimpleName();
    private ICategoryContract.View mView;
    private int mCurrentPage = 0;

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
        if (savedInstanceState != null) {
            mDataList = savedInstanceState.getParcelableArrayList("data");
            mCurrentPage = savedInstanceState.getInt("page", 0);
            Log.d(TAG, "page = " + mCurrentPage + ";data = " + mDataList);
        }
    }

    @Override
    public void process(Bundle savedInstanceState) {
        if (mDataList != null && mDataList.size() != 0) {
            mView.updateList(mDataList);
        } else {
            loadCategory(false);
        }
    }

    @Override
    public void loadCategory(final boolean isLoadMore) {
        //如果为下拉刷新，则获取第一页数据
        //否则，页数递增
        mCurrentPage = isLoadMore ? ++mCurrentPage : 0;
        ApiService.getGankApi().getData(mView.getCategory(), Constant.COUNT, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankInfo<List<DataInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankInfo<List<DataInfo>> listGankInfo) {
                        if (listGankInfo.isError()) {
                            throw new GankException("服务器错误");
                        }
                        List<DataInfo> dataList = TimeUtil.convertTime(listGankInfo.getResults());
                        if (isLoadMore) {
                            mDataList.addAll(dataList);
                        } else {
                            mDataList = dataList;
                        }
                        mView.updateList(mDataList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(R.string.toast_request_fail);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
