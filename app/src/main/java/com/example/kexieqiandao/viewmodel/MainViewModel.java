package com.example.kexieqiandao.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.activity.MainActivity;
import com.example.kexieqiandao.local.SharedPreferencesManager;
import com.example.kexieqiandao.network.HttpManager;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> uid=new MutableLiveData<>();//学号
    public MutableLiveData<String> uname=new MutableLiveData<>();//姓名
    public MutableLiveData<String> time=new MutableLiveData<>();//本次签到开始时间
    public MutableLiveData<String> totalTime=new MutableLiveData<>();//本周签到总时长
    public MutableLiveData<String> week=new MutableLiveData<>();//周序
    public MutableLiveData<Boolean> online=new MutableLiveData<>();//是否在线
    public MutableLiveData<String> qiandaoStatus=new MutableLiveData<>();

    public void qiandaoORqiantui() {
        if(online.getValue()==null||online.getValue()){
            HttpManager.getInstance().qiantui(uid,uname,time,totalTime,week,online,qiandaoStatus);
        }else {
            HttpManager.getInstance().qiandao(uid,uname,time,totalTime,week,online,qiandaoStatus);
        }
    }

    /**
     * 获取在线状态
     */
    public void online(){
        String uidStr= SharedPreferencesManager.getInstance().getUId(MyApplication.getContext());
        if(uidStr==null||uidStr.equals("null")){
            time.setValue("本次签到开始时间: null");
            totalTime.setValue("null");
            uid.setValue("null");
            uname.setValue("null");
            week.setValue("null");
            qiandaoStatus.setValue(MyApplication.initViewOk);
        }else {
            uid.setValue(uidStr);
            HttpManager.getInstance().online(uid, uname, time, totalTime, week, online, qiandaoStatus);
        }
    }
}
