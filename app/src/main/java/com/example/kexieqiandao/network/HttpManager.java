package com.example.kexieqiandao.network;

import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.network.retrofit_api.IService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static IService service;

    private static HttpManager HttpManager;

    public static HttpManager getInstance() {
        if (HttpManager == null) {
            synchronized (HttpManager.class) {
                if (null == HttpManager) {
                    HttpManager = new HttpManager();
                }
            }
        }
        return HttpManager;
    }

    private HttpManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MyApplication.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(IService.class);
    }

    public void qiandao()
}
