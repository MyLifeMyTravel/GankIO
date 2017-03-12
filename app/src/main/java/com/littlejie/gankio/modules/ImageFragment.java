package com.littlejie.gankio.modules;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.littlejie.core.base.BaseFragment;
import com.littlejie.core.utils.DisplayUtil;
import com.littlejie.core.utils.ToastUtil;
import com.littlejie.gankio.Constant;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.GankInfo;
import com.littlejie.gankio.http.ApiService;
import com.littlejie.gankio.ui.adapter.ImageAdapter;
import com.littlejie.gankio.ui.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by littlejie on 2017/3/11.
 */

public class ImageFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private String mCategory;

    public static ImageFragment newInstance(String category) {

        Bundle args = new Bundle();

        ImageFragment fragment = new ImageFragment();
        args.putString(Constant.EXTRA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mCategory = getArguments().getString(Constant.EXTRA_CATEGORY);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpaceDecoration(DisplayUtil.dp2px(5)));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new ImageAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {
        ApiService.getGankApi().getData(mCategory, 50, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankInfo<List<DataInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankInfo<List<DataInfo>> listGankInfo) {
                        if (!listGankInfo.isError()) {
                            List<DataInfo> dataList = listGankInfo.getResults();
                            List<String> images = new ArrayList<String>();
                            for (int i = 0; i < dataList.size(); i++) {
                                images.add(dataList.get(i).getUrl());
                            }
                            mAdapter.setImageUrlList(images);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtil.showDefaultToast("完成");
                    }
                });

    }
}
