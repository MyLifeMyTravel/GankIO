package com.littlejie.gankio.modules;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
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
import com.littlejie.gankio.modules.contract.ICategoryContract;
import com.littlejie.gankio.modules.presenter.CategoryPresenter;
import com.littlejie.gankio.ui.adapter.CategoryAdapter;
import com.littlejie.gankio.ui.decoration.SpaceDecoration;
import com.littlejie.gankio.utils.AppCommand;

import java.util.List;

import butterknife.BindView;

/**
 * 按Gank IO中的类目显示信息(包括每日精选)
 * Created by littlejie on 2017/3/11.
 */

public class CategoryFragment extends BaseFragment implements ICategoryContract.View {

    @BindView(R.id.swipe_refresh)
    MaterialRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;

    private ICategoryContract.Presenter mPresenter;
    private boolean isLoadMore;
    private String mCategory;
    private int mOffset;

    public static CategoryFragment newInstance(String category) {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        args.putString(Constant.EXTRA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPresenter.onSaveInstanceState(outState);
        outState.putInt("offset", mRecyclerView.computeVerticalScrollOffset());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new CategoryPresenter(this);
        if (savedInstanceState != null) {
            //恢复之前的RecyclerView的偏移量
            mOffset = savedInstanceState.getInt("offset");
        }
        if (getArguments() != null) {
            //因为category与UI显示相关，所以放在View中处理
            mCategory = getArguments().getString(Constant.EXTRA_CATEGORY);
        }
        mPresenter.initData(getArguments(), savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpaceDecoration(DisplayUtil.dp2px(5)));
        //如果是图片类目，则按瀑布流显示否则按照线性布局显示
        mRecyclerView.setLayoutManager(Constant.Category.IMAGE.equals(mCategory)
                ? new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                : new LinearLayoutManager(getContext()));
        mAdapter = new CategoryAdapter();
        //设置是否为每日精选
        mAdapter.setDayPublish(Constant.Category.DAY.equals(mCategory));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                mPresenter.pullToRefresh(false);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                mPresenter.pullToRefresh(true);
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
        mPresenter.process(savedInstanceState);
        setVerticalOffset(mOffset);
    }

    @Override
    public String getCategory() {
        return mCategory;
    }

    @Override
    public void updateList(List<DataInfo> dataList) {
        if (isLoadMore) {
            stopLoadMoreRefresh();
        } else {
            stopPullRefresh();
        }
        mAdapter.setDataList(dataList);
    }

    @Override
    public void setVerticalOffset(int offset) {
        mRecyclerView.offsetChildrenVertical(offset);
    }

    @Override
    public boolean isLoadMore() {
        return isLoadMore;
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
