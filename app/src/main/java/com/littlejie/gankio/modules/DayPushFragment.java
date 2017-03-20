package com.littlejie.gankio.modules;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.OnLoadMoreListener;
import com.cjj.OnRefreshListener;
import com.littlejie.core.base.BaseFragment;
import com.littlejie.core.utils.DisplayUtil;
import com.littlejie.core.utils.ToastUtil;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.modules.contract.IDayPushContract;
import com.littlejie.gankio.modules.presenter.DayPushPresenter;
import com.littlejie.gankio.ui.adapter.CategoryAdapter;
import com.littlejie.gankio.ui.decoration.SpaceDecoration;
import com.littlejie.gankio.utils.AppCommand;

import java.util.List;

import butterknife.BindView;

/**
 * 每日精选
 * Created by littlejie on 2017/3/12.
 */

public class DayPushFragment extends BaseFragment implements IDayPushContract.View {

    @BindView(R.id.swipe_refresh)
    MaterialRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;

    private DayPushPresenter mPresenter;
    private boolean isLoadMore;

    public static DayPushFragment newInstance() {

        Bundle args = new Bundle();

        DayPushFragment fragment = new DayPushFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_day_push;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new DayPushPresenter(this);
        mPresenter.initData(getArguments());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpaceDecoration(DisplayUtil.dp2px(2)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CategoryAdapter();
        mAdapter.setDayPublish(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewListener() {
        super.initViewListener();
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                mPresenter.loadDayPush(false);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                mPresenter.loadDayPush(true);
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
        mPresenter.process();
    }

    @Override
    public void update(List<DataInfo> dataList) {
        if (isLoadMore) {
            stopLoadMoreRefresh();
            mAdapter.addDataList(dataList);
        } else {
            stopPullRefresh();
            mAdapter.setDataList(dataList);
        }
    }

    @Override
    public void stopPullRefresh() {
        mSwipeRefreshLayout.finishRefresh();
    }

    @Override
    public void stopLoadMoreRefresh() {
        mSwipeRefreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void toWebActivity(String url) {
        if (TextUtils.isEmpty(url)) {
            showMessage("错误的URL");
            return;
        }
        AppCommand.openWebView(getContext(), url);
    }

    @Override
    public void showMessage(int msg) {
        ToastUtil.showDefaultToast(msg);
    }

    @Override
    public void showMessage(CharSequence msg) {
        ToastUtil.showDefaultToast(msg);
    }
}
