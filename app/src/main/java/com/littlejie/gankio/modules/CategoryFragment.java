package com.littlejie.gankio.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.OnLoadMoreListener;
import com.cjj.OnRefreshListener;
import com.littlejie.core.base.BaseFragment;
import com.littlejie.core.utils.DisplayUtil;
import com.littlejie.core.utils.ToastUtil;
import com.littlejie.gankio.Constant;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.GankInfo;
import com.littlejie.gankio.http.ApiService;
import com.littlejie.gankio.ui.adapter.CategoryAdapter;
import com.littlejie.gankio.ui.decoration.SpaceDecoration;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 按Gank IO中的类目显示信息
 * Created by littlejie on 2017/3/11.
 */

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.swipe_refresh)
    MaterialRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;

    private String mCategory;
    private int mCurrentPage = 1;

    public static CategoryFragment newInstance(String category) {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        args.putString(Constant.EXTRA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_category;
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
        mRecyclerView.setLayoutManager(Constant.Category.IMAGE.equals(mCategory)
                ? new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                : new LinearLayoutManager(getContext()));
        mAdapter = new CategoryAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                getData(mCurrentPage, false);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(++mCurrentPage, true);
            }
        });
        mAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toWebActivity(mAdapter.getDataList().get(position).getUrl());
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        getData(1, false);
    }

    private void getData(int page, final boolean isLoadMore) {
        ApiService.getGankApi().getData(mCategory, Constant.COUNT, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankInfo<List<DataInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankInfo<List<DataInfo>> listGankInfo) {
                        if (listGankInfo.isError()) {
                            return;
                        }
                        finishRefresh(isLoadMore);
                        if (isLoadMore) {
                            mAdapter.addDataList(listGankInfo.getResults());
                        } else {
                            mAdapter.setDataList(listGankInfo.getResults());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showDefaultToast(R.string.toast_request_fail);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void finishRefresh(boolean isLoadMore) {
        if (isLoadMore) {
            mSwipeRefreshLayout.finishRefreshLoadMore();
        } else {
            mSwipeRefreshLayout.finishRefresh();
        }
    }

    private void toWebActivity(String url) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(Constant.EXTRA_STRING, url);
        startActivity(intent);
    }

}
