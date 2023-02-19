package com.example.kexieqiandao;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static Context getContext() {
        return context;
    }

    public static final String baseUrl="https://at.kexie.space";
    public static String uid="";
    public static String qiandaoSucceed ="qiandaoSucceed";
    public static String qiantuiSucceed ="qiantuiSucceed";
    public static String qiandaoFail ="qiandaoFail";
    public static String qiantuiFail ="qiantuiFail";
}
