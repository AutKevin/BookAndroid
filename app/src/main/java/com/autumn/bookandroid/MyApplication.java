package com.autumn.bookandroid;

import android.app.Application;
import android.content.Context;


/**
 * 编写自定义Application，管理全局状态信息，比如Context
 * @author autumn
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Context
        context = getApplicationContext();
    }

    //返回
    public static Context getContextObject(){
        return context;
    }
}
