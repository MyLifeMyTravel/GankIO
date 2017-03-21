package com.littlejie.gankio.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日精选实体类
 * Created by littlejie on 2017/3/20.
 */

public class DayInfo {

    private List<DataInfo> iOS;
    private List<DataInfo> Android;
    //瞎推荐
    @SerializedName("瞎推荐")
    private List<DataInfo> blind;
    //拓展资源
    @SerializedName("拓展资源")
    private List<DataInfo> resource;
    @SerializedName("福利")
    private List<DataInfo> welfare;
    @SerializedName("休息视频")
    private List<DataInfo> videos;

    public List<DataInfo> getiOS() {
        return iOS;
    }

    public void setiOS(List<DataInfo> iOS) {
        this.iOS = iOS;
    }

    public List<DataInfo> getAndroid() {
        return Android;
    }

    public void setAndroid(List<DataInfo> android) {
        Android = android;
    }

    public List<DataInfo> getBlind() {
        return blind;
    }

    public void setBlind(List<DataInfo> blind) {
        this.blind = blind;
    }

    public List<DataInfo> getResource() {
        return resource;
    }

    public void setResource(List<DataInfo> resource) {
        this.resource = resource;
    }

    public List<DataInfo> getWelfare() {
        return welfare;
    }

    public void setWelfare(List<DataInfo> welfare) {
        this.welfare = welfare;
    }

    public List<DataInfo> getVideos() {
        return videos;
    }

    public void setVideos(List<DataInfo> videos) {
        this.videos = videos;
    }

    public List<DataInfo> getData() {
        List<DataInfo> dataList = new ArrayList<>();
        if (welfare != null) {
            dataList.addAll(welfare);
        }
        if (iOS != null) {
            dataList.addAll(iOS);
        }
        if (Android != null) {
            dataList.addAll(Android);
        }
        if (blind != null) {
            dataList.addAll(blind);
        }
        if (resource != null) {
            dataList.addAll(resource);
        }
        if (videos != null) {
            dataList.addAll(videos);
        }
        return dataList;
    }
}
