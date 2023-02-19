package com.example.kexieqiandao.network.retrofit_api;

import com.example.kexieqiandao.entity.UserId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IService {
    @POST("/api/user/signIn")
    Call<ResponseBody> signIn(@Body UserId userId);
}
