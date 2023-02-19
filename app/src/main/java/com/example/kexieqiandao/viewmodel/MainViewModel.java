package com.example.kexieqiandao.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> uid=new MutableLiveData<>();//学号
    public MutableLiveData<String> uname=new MutableLiveData<>();//姓名
    public MutableLiveData<String> time=new MutableLiveData<>();//本次签到开始时间
    public MutableLiveData<String> totalTime=new MutableLiveData<>();//本周签到总时长
    public MutableLiveData<Boolean> online=new MutableLiveData<>();//是否在线
    public MutableLiveData<String> qiandaoStatus=new MutableLiveData<>();

    public void qiandao(){

    }
}
