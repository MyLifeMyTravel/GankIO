package com.littlejie.gankio.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Gank IO Api 提供的最基础数据结构
 * Created by littlejie on 2017/3/11.
 */

public class DataInfo {

    @SerializedName("_id")
    private String id;
    @SerializedName("createAt")
    private String createTime;
    private String desc;
    private List<String> images;
    private String source;
    @SerializedName("publishedAt")
    private String publishedTime;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", desc='" + desc + '\'' +
                ", images=" + images +
                ", source='" + source + '\'' +
                ", publishedTime='" + publishedTime + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }
}
