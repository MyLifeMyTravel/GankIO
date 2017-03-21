package com.littlejie.gankio.modules.contract;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by littlejie on 2017/3/12.
 */

public interface IMainContract {

    interface View {

        void setSelectPage(int item);

        void setTab(List<Fragment> fragmentList, String[] titles);

        void showMessage(int msg);

        void showMessage(CharSequence msg);
    }

    interface Presenter {

        void initData(Intent intent);

        void process();
    }
}
