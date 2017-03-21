package com.littlejie.gankio.modules.model;

import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.DayInfo;
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
 * 请求每日精选数据的Model
 * Created by littlejie on 2017/3/21.
 */

public class DayPublishModel implements ICategoryContract.DayPublishModel {

    private List<String> mPublishDayList;

    private ICategoryContract.Presenter mPresenter;

    public DayPublishModel(ICategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadPublishDays() {
        ApiService.getGankApi().getPublishDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankInfo<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankInfo<List<String>> listGankInfo) {
                        if (listGankInfo.isError()) {
                            throw new GankException("服务器错误");
                        }
                        mPublishDayList = listGankInfo.getResults();
                        loadData(null, 0);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadData(String category, int index) {
        if (mPublishDayList == null) {
            loadPublishDays();
            return;
        }
        String date = convert(mPublishDayList.get(index));
        final DataInfo dInfo = new DataInfo();
        dInfo.setPublishedTime(date);
        ApiService.getGankApi().getDayGank(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankInfo<DayInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankInfo<DayInfo> dayInfoGankInfo) {
                        if (dayInfoGankInfo.isError()) {
                            throw new GankException("服务器错误");
                        }
                        List<DataInfo> dataList = TimeUtil.convertTime(dayInfoGankInfo.getResults().getData());
                        //将日期添加到数据的最顶部
                        dataList.add(0, dInfo);
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

    private String convert(String time) {
        return time.replace("-", "/");
    }
}
