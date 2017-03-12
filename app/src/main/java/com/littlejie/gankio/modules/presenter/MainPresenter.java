package com.littlejie.gankio.modules.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.littlejie.core.base.Core;
import com.littlejie.gankio.R;
import com.littlejie.gankio.modules.CategoryFragment;
import com.littlejie.gankio.modules.DayPushFragment;
import com.littlejie.gankio.modules.contract.IMainContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlejie on 2017/3/12.
 */

public class MainPresenter implements IMainContact.Presenter {

    private IMainContact.View mView;

    private String[] mTitles;
    private List<Fragment> mFragmentList;

    public MainPresenter(IMainContact.View view) {
        mView = view;
    }

    @Override
    public void initData(Intent intent) {
        mTitles = Core.getStringArray(R.array.category);
        initFragmentList();
    }

    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(DayPushFragment.newInstance());

        for (int i = 1; i < mTitles.length; i++) {
            mFragmentList.add(CategoryFragment.newInstance(mTitles[i]));
        }
    }

    @Override
    public void process() {
        mView.setTab(mFragmentList, mTitles);
        mView.setSelectPage(1);
    }

}
