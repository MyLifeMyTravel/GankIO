package com.littlejie.gankio.modules;

import android.os.Bundle;
import android.view.View;

import com.littlejie.core.base.BaseFragment;
import com.littlejie.gankio.R;

/**
 * 每日精选
 * Created by littlejie on 2017/3/12.
 */

public class DayPushFragment extends BaseFragment {

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

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }
}
