package com.littlejie.gankio.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by littlejie on 2017/3/14.
 */

public class ScrollWebView extends WebView {

    private OnScrollChangedListener mOnScrollChangedListener;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l, t);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        mOnScrollChangedListener = onScrollChangedListener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t);
    }
}
