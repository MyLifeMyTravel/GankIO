package com.littlejie.core.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 封装了 Glide 的 ImageView
 * Created by littlejie on 2017/3/11.
 */

public class BaseImageView extends AppCompatImageView {

    public BaseImageView(Context context) {
        super(context);
    }

    public BaseImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImage(int resId) {
        Glide.with(getContext())
                .load(resId)
                .crossFade()
                .into(this);
    }

    public void setImage(String url) {
        Glide.with(getContext())
                .load(url)
                .dontAnimate()
//                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .into(this);
    }

    public void setImage(Activity activity, String url) {
        Glide.with(activity)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(this);
    }

    public void setImage(Fragment fragment, String url) {
        Glide.with(fragment)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(this);
    }
}
