package com.littlejie.gankio.modules;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.littlejie.core.base.BaseActivity;
import com.littlejie.core.utils.ToastUtil;
import com.littlejie.gankio.R;
import com.littlejie.gankio.modules.contract.IMainContract;
import com.littlejie.gankio.modules.presenter.MainPresenter;
import com.littlejie.gankio.ui.adapter.TabAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements IMainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private TabAdapter mTabAdapter;

    private IMainContract.Presenter mPresenter;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mPresenter = new MainPresenter(this);
        mPresenter.initData(getIntent());
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initViewListener() {
        super.initViewListener();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.fab)
    void onFabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //点击事件无效，除非去掉main.xml中的actionViewClass属性
                showMessage("搜索");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        showMessage(item.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setSelectPage(int item) {
        mViewPager.setCurrentItem(item);
    }

    @Override
    public void setTab(List<Fragment> fragmentList, String[] titles) {
        mTabAdapter.setData(fragmentList, titles);
        //默认选中福利
        mViewPager.setCurrentItem(1);
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
