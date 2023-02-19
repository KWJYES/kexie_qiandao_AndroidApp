package com.example.kexieqiandao.network;

import androidx.lifecycle.MutableLiveData;

import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.entity.UserId;
import com.example.kexieqiandao.network.retrofit_api.IService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public void qiandao(MutableLiveData<String> uid,MutableLiveData<String> uname,MutableLiveData<String> time,MutableLiveData<String> totalTime,MutableLiveData<Boolean> online,MutableLiveData<String> qiandaoStatus){
        UserId userId=new UserId();
        userId.setUserId(uid.getValue());
        service.signIn(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                qiandaoStatus.setValue(MyApplication.qiandaoFail);
            }
        });
    }
}
