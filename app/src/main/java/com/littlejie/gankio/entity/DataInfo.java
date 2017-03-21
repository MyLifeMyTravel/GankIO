package com.littlejie.gankio.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Gank IO Api 提供的最基础数据结构
 * Created by littlejie on 2017/3/11.
 */

public class DataInfo implements Parcelable{

    /**
     * "_id":"58c20bed421aa90f13178638",
     * "createdAt":"2017-03-10T10:14:05.519Z",
     * "desc":"用注解把那些恶心的，或者有 Hack 功能的代码标注出来，每次调用 IDE 都会提醒你，这个函数调用有潜在风险。",
     * "images":[
     * "http://img.gank.io/04b7b2ed-dc05-401d-bd50-91ed632b096c"
     * ],
     * "publishedAt":"2017-03-10T11:43:50.30Z",
     * "source":"chrome",
     * "type":"Android",
     * "url":"https://github.com/anupcowkur/here-be-dragons",
     * "used":true,
     * "who":"带马甲"
     */
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

    public DataInfo() {
    }

    protected DataInfo(Parcel in) {
        id = in.readString();
        createTime = in.readString();
        desc = in.readString();
        images = in.createStringArrayList();
        source = in.readString();
        publishedTime = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
    }

    public static final Creator<DataInfo> CREATOR = new Creator<DataInfo>() {
        @Override
        public DataInfo createFromParcel(Parcel in) {
            return new DataInfo(in);
        }

        @Override
        public DataInfo[] newArray(int size) {
            return new DataInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createTime);
        dest.writeString(desc);
        dest.writeStringList(images);
        dest.writeString(source);
        dest.writeString(publishedTime);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
    }
}
