package com.littlejie.gankio.entity;

import java.util.List;

/**
 * Created by littlejie on 2017/3/6.
 */

public class GankInfo<T> {

    private List<String> category;
    private boolean error;
    private T results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankInfo{" +
                "category=" + category +
                ", error=" + error +
                ", results=" + results +
                '}';
    }
}
