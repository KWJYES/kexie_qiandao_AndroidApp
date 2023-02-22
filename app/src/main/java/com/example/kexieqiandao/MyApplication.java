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
    public static String qiandaoOutTime ="qiandaoOutTime";//该时段不允许签到
    public static String qiantuiSucceed ="qiantuiSucceed";
    public static String qiandaoFail ="qiandaoFail";
    public static String qiantuiFail ="qiantuiFail";
    public static String qiandaoRepeat ="qiantuiRepeat";//重复签到
    public static String qiantuiNo ="qiantuiNo";//没有签到op
    public static String opTooFaster ="opTooFaster";//操作太频繁了
    public static String netWorkError ="netWorkError";
    public static String initOnlineOk ="initOnlineOk";
    public static String succeed ="succeed";
    public static String fail ="fail";
}
