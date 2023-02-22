package com.example.kexieqiandao.network.retrofit_api;

import com.example.kexieqiandao.entity.ComplaintRequestBody;
import com.example.kexieqiandao.entity.ResponseBean;
import com.example.kexieqiandao.entity.UserId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IService {

    /**
     * 签到
     * @param userId 学号
     * @return
     */
    @POST("/api/user/signIn")
    Call<ResponseBody> signIn(@Body UserId userId);

    /**
     * 签退
     * @param userId
     * @return
     */
    @POST("/api/user/signOut")
    Call<ResponseBody> signOut(@Body UserId userId);

    /**
     * 检查在线状态
     * @param userId
     * @return
     */
    @GET("/api/record/online/{userId}")
    Call<ResponseBody> online(@Path("userId") String userId);

    /**
     * 获取当前在线用户
     * @return
     */
    @GET("/api/record/online")
    Call<ResponseBean> recordOnline();

    /**
     * 举报
     * @return
     */
    @POST("/api/user/complaint")
    Call<ResponseBody> complaint(@Body ComplaintRequestBody body);

    /**
     * 获取有效排行榜
     * 目前测试为8个
     * @param isTrueOrNull "true"：获取老人排行
     *                     “”：空的，获取新人
     * @return
     */
    @GET("/api/record/topFive")
    Call<ResponseBody> topRank(@Query("old-man") String isTrueOrNull);
}
