package com.littlejie.gankio.modules.presenter;

import android.os.Bundle;

import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.DayInfo;
import com.littlejie.gankio.entity.GankInfo;
import com.littlejie.gankio.exception.GankException;
import com.littlejie.gankio.http.ApiService;
import com.littlejie.gankio.modules.contract.IDayPushContract;
import com.littlejie.gankio.utils.TimeUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by littlejie on 2017/3/20.
 */

public class DayPushPresenter implements IDayPushContract.Presenter {

    private IDayPushContract.View mView;
    private List<String> mPublishDayList;

    private int mCurrentPage;

    public DayPushPresenter(IDayPushContract.View view) {
        mView = view;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void process() {
        loadPublishDays();
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
                        loadDayPush(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String convert(String time) {
        return time.replace("-", "/");
    }

    @Override
    public void loadDayPush(boolean isLoadMore) {
        //如果为下拉刷新，则数据重置
        mCurrentPage = isLoadMore ? ++mCurrentPage : 0;
        String date = convert(mPublishDayList.get(mCurrentPage));
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
                        dataList.add(0, dInfo);
                        mView.update(dataList);
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
