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
import com.littlejie.gankio.modules.contract.ICategoryContact;
import com.littlejie.gankio.modules.presenter.CategoryPresenter;
import com.littlejie.gankio.ui.adapter.CategoryAdapter;
import com.littlejie.gankio.ui.decoration.SpaceDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * 按Gank IO中的类目显示信息
 * Created by littlejie on 2017/3/11.
 */

public class CategoryFragment extends BaseFragment implements ICategoryContact.View {

    @BindView(R.id.swipe_refresh)
    MaterialRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;

    private ICategoryContact.Presenter mPresenter;
    private boolean isLoadMore;

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
        mPresenter = new CategoryPresenter(this);
        if (getArguments() != null) {
            mPresenter.initData(getArguments());
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpaceDecoration(DisplayUtil.dp2px(5)));
        mRecyclerView.setLayoutManager(mPresenter.isImageCategory()
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
                isLoadMore = false;
                mPresenter.loadCategory(false);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                mPresenter.loadCategory(true);
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
        mPresenter.loadCategory(false);
    }

    @Override
    public void updateData(List<DataInfo> dataList) {
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
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(Constant.EXTRA_STRING, url);
        startActivity(intent);
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
