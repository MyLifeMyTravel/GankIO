package com.littlejie.gankio.modules.presenter;

import android.os.Bundle;

import com.littlejie.gankio.Constant;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.GankInfo;
import com.littlejie.gankio.exception.GankException;
import com.littlejie.gankio.http.ApiService;
import com.littlejie.gankio.modules.contract.ICategoryContract;
import com.littlejie.gankio.utils.TimeUtil;

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

    private ICategoryContract.View mView;
    private int mCurrentPage = 1;

    public CategoryPresenter(ICategoryContract.View view) {
        mView = view;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void process() {
        loadCategory(false);
    }

    @Override
    public void loadCategory(final boolean isLoadMore) {
        mCurrentPage = isLoadMore ? ++mCurrentPage : 1;
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
                        mView.updateData(TimeUtil.convertTime(listGankInfo.getResults()));
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
