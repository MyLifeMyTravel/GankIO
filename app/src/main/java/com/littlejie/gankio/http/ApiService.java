package com.littlejie.gankio.http;

import com.littlejie.gankio.Constant;
import com.littlejie.gankio.http.api.GankApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 接口请求实现类
 * Created by littlejie on 2017/3/11.
 */

public class ApiService {

    private static GankApi sGankApi;

    public static void init() {
        Retrofit sRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        sGankApi = sRetrofit.create(GankApi.class);

    }

    public static GankApi getGankApi() {
        return sGankApi;
    }

}
