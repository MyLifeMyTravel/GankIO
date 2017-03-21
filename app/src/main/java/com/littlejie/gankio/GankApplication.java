package com.littlejie.gankio;

import com.littlejie.core.base.BaseApplication;
import com.littlejie.core.utils.DisplayUtil;
import com.littlejie.gankio.http.ApiService;

/**
 * Gank IO Application类
 * Created by littlejie on 2017/3/6.
 */

public class GankApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ApiService.init();
        DisplayUtil.init(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//
//        LeakCanary.install(this);
    }

}
