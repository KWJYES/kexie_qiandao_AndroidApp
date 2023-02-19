package com.example.kexieqiandao.network.retrofit_api;

import com.example.kexieqiandao.entity.UserId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IService {
    @POST("/api/user/signIn")
    Call<ResponseBody> signIn(@Body UserId userId);

    @POST("/api/user/signOut")
    Call<ResponseBody> signOut(@Body UserId userId);

    @GET("/api/record/online/{userId}")
    Call<ResponseBody> online(@Part("userId") String userId);

}
