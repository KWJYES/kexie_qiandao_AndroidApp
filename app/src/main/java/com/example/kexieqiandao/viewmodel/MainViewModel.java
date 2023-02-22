package com.example.kexieqiandao.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.entity.ComplaintRequestBody;
import com.example.kexieqiandao.entity.ResponseBean;
import com.example.kexieqiandao.local.SharedPreferencesManager;
import com.example.kexieqiandao.network.HttpManager;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> uid = new MutableLiveData<>();//学号
    public MutableLiveData<String> uname = new MutableLiveData<>();//姓名
    public MutableLiveData<String> time = new MutableLiveData<>();//本次签到开始时间
    public MutableLiveData<String> totalTime = new MutableLiveData<>();//本周签到总时长
    public MutableLiveData<String> week = new MutableLiveData<>();//周序
    public MutableLiveData<Boolean> online = new MutableLiveData<>();//是否在线
    public MutableLiveData<Integer> onlineNun = new MutableLiveData<>();//当前在教室的人数
    public MutableLiveData<String> qiandaoStatus = new MutableLiveData<>();
    public MutableLiveData<String> complaintStatus = new MutableLiveData<>();
    public MutableLiveData<String> getOnlineUserStatus = new MutableLiveData<>();
    public MutableLiveData<String> searchText = new MutableLiveData<>();

    public MutableLiveData<List<ResponseBean.DataDTO>> onlineUserList = new MutableLiveData<>();

    public void qiandaoORqiantui() {
        if (online.getValue() == null || online.getValue()) {
            HttpManager.getInstance().qiantui(uid, uname, time, totalTime, week, online, qiandaoStatus);
        } else {
            HttpManager.getInstance().qiandao(uid, uname, time, totalTime, week, online, qiandaoStatus);
        }
    }

    /**
     * 获取在线状态
     */
    public void online() {
        String uidStr = SharedPreferencesManager.getInstance().getUId(MyApplication.getContext());
        if (uidStr == null || uidStr.equals("null")) {
            time.setValue("本次签到开始时间: null");
            totalTime.setValue("null");
            uid.setValue("null");
            uname.setValue("null");
            week.setValue("null");
            qiandaoStatus.setValue(MyApplication.initOnlineOk);
        } else {
            uid.setValue(uidStr);
            HttpManager.getInstance().online(uid, uname, time, totalTime, week, online, qiandaoStatus);
        }
    }

    /**
     * 获取当前在线用户
     */
    public void getOnlineUser() {
        HttpManager.getInstance().getOnlineUser(onlineUserList, getOnlineUserStatus);
    }

    public void complaint(ResponseBean.DataDTO item) {
        ComplaintRequestBody body = new ComplaintRequestBody();
        body.setTargetUserId(item.getUserId());//被举报者id
        body.setOperatorUserId(SharedPreferencesManager.getInstance().getUId(MyApplication.getContext()));//操作者id
        HttpManager.getInstance().complaint(body, complaintStatus);
    }

    public List<ResponseBean.DataDTO> search(String str) {
        List<ResponseBean.DataDTO> list = new ArrayList<>();
        if (onlineUserList.getValue() == null) return list;
        for (ResponseBean.DataDTO item : onlineUserList.getValue()) {
            if (item.getUserName().contains(str) ||
                    item.getUserDept().contains(str) ||
                    item.getUserId().contains(str) ||
                    item.getUserLocation().contains(str)) {
                list.add(item);
            }
        }
        return list;
    }
}
