package com.littlejie.gankio.utils;

import android.content.Context;
import android.content.Intent;

import com.littlejie.gankio.Constant;
import com.littlejie.gankio.modules.WebActivity;

/**
 * Created by littlejie on 2017/3/20.
 */

public class AppCommand {

    public static void openWebView(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constant.EXTRA_STRING, url);
        context.startActivity(intent);
    }
}
