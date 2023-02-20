package com.example.kexieqiandao.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.entity.UserId;
import com.example.kexieqiandao.local.SharedPreferencesManager;
import com.example.kexieqiandao.network.retrofit_api.IService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void qiandao(MutableLiveData<String> uid, MutableLiveData<String> uname, MutableLiveData<String> time, MutableLiveData<String> totalTime, MutableLiveData<String> week, MutableLiveData<Boolean> online, MutableLiveData<String> qiandaoStatus) {
        UserId userId = new UserId();
        userId.setUserId(uid.getValue());
        service.signIn(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("HttpManager", jsonObject.toString());
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        uid.setValue(data.getString("userId"));
                        uname.setValue(data.getString("userName"));
                        totalTime.setValue(data.getString("totalTime"));
                        week.setValue(data.getString("week"));
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        time.setValue("本次签到开始时间: " + formatter.format(curDate));
                        SharedPreferencesManager.getInstance().applyUId(MyApplication.getContext(),uid.getValue());
                        SharedPreferencesManager.getInstance().applyUName(MyApplication.getContext(),uname.getValue());
                        SharedPreferencesManager.getInstance().applyTotalTime(MyApplication.getContext(),totalTime.getValue());
                        SharedPreferencesManager.getInstance().applyWeek(MyApplication.getContext(),week.getValue());
                        online.setValue(true);
                        qiandaoStatus.setValue(MyApplication.qiandaoSucceed);
                    } else if (code == -201) {
                        online.setValue(true);
                        qiandaoStatus.setValue(MyApplication.qiandaoRepeat);
                    }else if (code == -205) {
                        qiandaoStatus.setValue(MyApplication.opTooFaster);
                    }else if (code == -206) {
                        qiandaoStatus.setValue(MyApplication.qiandaoOutTime);
                    } else {
                        qiandaoStatus.setValue(MyApplication.qiandaoFail);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                qiandaoStatus.setValue(MyApplication.netWorkError);
            }
        });
    }

    public void qiantui(MutableLiveData<String> uid, MutableLiveData<String> uname, MutableLiveData<String> time, MutableLiveData<String> totalTime, MutableLiveData<String> week, MutableLiveData<Boolean> online, MutableLiveData<String> qiandaoStatus) {
        UserId userId = new UserId();
        userId.setUserId(uid.getValue());
        service.signOut(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("HttpManager", jsonObject.toString());
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        uid.setValue(data.getString("userId"));
                        uname.setValue(data.getString("userName"));
                        totalTime.setValue(data.getString("totalTime"));
                        week.setValue(data.getString("week"));
                        time.setValue("本次签到时长: " + data.getString("accumulatedTime"));
                        SharedPreferencesManager.getInstance().applyUId(MyApplication.getContext(),uid.getValue());
                        SharedPreferencesManager.getInstance().applyUName(MyApplication.getContext(),uname.getValue());
                        SharedPreferencesManager.getInstance().applyTotalTime(MyApplication.getContext(),totalTime.getValue());
                        SharedPreferencesManager.getInstance().applyWeek(MyApplication.getContext(),week.getValue());
                        online.setValue(false);
                        qiandaoStatus.setValue(MyApplication.qiantuiSucceed);
                    } else if (code == -202) {
                        online.setValue(false);
                        qiandaoStatus.setValue(MyApplication.qiantuiNo);
                    } else {
                        qiandaoStatus.setValue(MyApplication.qiantuiFail);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                qiandaoStatus.setValue(MyApplication.netWorkError);
            }
        });
    }

    public void online(MutableLiveData<String> uid, MutableLiveData<String> uname, MutableLiveData<String> time, MutableLiveData<String> totalTime, MutableLiveData<String> week, MutableLiveData<Boolean> online, MutableLiveData<String> qiandaoStatus) {
        service.online(uid.getValue()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("HttpManager", jsonObject.toString());
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        uid.setValue(data.getString("userId"));
                        uname.setValue(data.getString("userName"));
                        try{
                            String status=data.getString("status");
                            if (data.getString("status").equals("已签退")){//已签退
                                time.setValue("本次签到开始时间: null");
                                online.setValue(false);
                            }
                        }catch (Exception e){//已签到
                            time.setValue("本次签到开始时间: " + data.getString("start"));
                            online.setValue(true);
                        }
                        totalTime.setValue(SharedPreferencesManager.getInstance().getTotalTime(MyApplication.getContext()));
                        week.setValue(SharedPreferencesManager.getInstance().getWeek(MyApplication.getContext()));
                    } else {
                        qiandaoStatus.setValue(MyApplication.netWorkError);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                qiandaoStatus.setValue(MyApplication.initViewOk);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                qiandaoStatus.setValue(MyApplication.netWorkError);
            }
        });
    }
}
