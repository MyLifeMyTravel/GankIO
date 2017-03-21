package com.littlejie.gankio.modules.model;

import com.littlejie.gankio.Constant;
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
 * 请求类目的Model层
 * Created by littlejie on 2017/3/21.
 */

public class CategoryModel implements ICategoryContract.Model {

    private ICategoryContract.Presenter mPresenter;

    public CategoryModel(ICategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadData(String category, int page) {
        ApiService.getGankApi().getData(category, Constant.COUNT, page)
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
                        mPresenter.loadData(dataList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
