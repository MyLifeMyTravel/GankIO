package com.littlejie.gankio.http.api;

import com.littlejie.gankio.entity.DataInfo;
import com.littlejie.gankio.entity.DayInfo;
import com.littlejie.gankio.entity.GankInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Gank IO Api 接口
 * 实际使用 getDayGank 和 getData 两个接口
 * Created by littlejie on 2017/3/6.
 */

public interface GankApi {

    @GET("day/{date}")
    Observable<GankInfo<DayInfo>> getDayGank(@Path("date") String date);

    /**
     * 获取发布干货的日期
     *
     * @return
     */
    @GET("day/history")
    Observable<GankInfo<List<String>>> getPublishDays();

    @GET("search/query/listview/category/{category}/count/50/page/{page}")
    Call<ResponseBody> search(@Path("category") String category, @Path("page") int page);

    /**
     * 搜索指定类目的数据
     *
     * @param category all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param count    1-50
     * @param page     页数，大于0
     * @return
     */
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Call<ResponseBody> search(@Path("category") String category,
                              @Path("count") int count, @Path("page") int page);

    /**
     * 获取指定日期的网站数据
     *
     * @param date 日期：2016/05/01
     * @return
     */
    @GET("history/content/day/{date}")
    Call<ResponseBody> getSpecialDayGank(@Path("date") String date);

    /**
     * 获取某几日干货网站数据
     * 2 代表 2 个数据，1 代表：取第一页数据
     *
     * @param count
     * @param page
     * @return
     */
    @GET("history/content/{count}/{page}")
    Call<ResponseBody> getDaysGank(@Path("count") int count, @Path("page") int page);

    /**
     * 获取分类数据
     *
     * @param type  数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param count 请求个数： 数字，大于0
     * @param page  第几页：数字，大于0
     * @return
     */
    @GET("data/{type}/{count}/{page}")
    Observable<GankInfo<List<DataInfo>>> getData(@Path("type") String type,
                                                 @Path("count") int count, @Path("page") int page);

    /**
     * 获取随机数据
     *
     * @param type  数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * @param count 个数： 数字，大于0
     * @return
     */
    @GET("random/data/{type}/{count}")
    Call<ResponseBody> getRandomData(@Path("type") String type, @Path("count") int count);

}
