package com.example.kexieqiandao.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.R;
import com.example.kexieqiandao.adapter.OnlineUserListAdapter;
import com.example.kexieqiandao.base.BaseActivity;
import com.example.kexieqiandao.databinding.ActivityMainBinding;
import com.example.kexieqiandao.entity.ResponseBean;
import com.example.kexieqiandao.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends BaseActivity {

    private MainViewModel vm;
    private ActivityMainBinding binding;
    private Toast toast0;

    private static void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    protected void initActivity() {
        setTransparentStatusBar(true);
        vm = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setEvent(new Event());
        binding.setVm(vm);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void observerDataStateUpdateAction() {
        vm.qiandaoStatus.observe(this, s -> {
            Toast toast = null;
            if (s.equals(MyApplication.qiandaoSucceed)) {
                toast = Toast.makeText(MainActivity.this, "签到成功", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.qiantuiSucceed)) {
                toast = Toast.makeText(MainActivity.this, "签退成功", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.qiandaoFail)) {
                toast = Toast.makeText(MainActivity.this, "签到失败！", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.qiantuiFail)) {
                toast = Toast.makeText(MainActivity.this, "签退失败！", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.netWorkError)) {
                toast = Toast.makeText(MainActivity.this, "网络错误！", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.qiandaoRepeat)) {
                toast = Toast.makeText(MainActivity.this, "重复签到！", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.qiantuiNo)) {
                toast = Toast.makeText(MainActivity.this, "您没还有签到！", Toast.LENGTH_SHORT);
            } else if (s.equals(MyApplication.qiandaoOutTime)) {
                toast = Toast.makeText(MainActivity.this, "该时段不允许签到", Toast.LENGTH_LONG);
            } else if (s.equals(MyApplication.opTooFaster)) {
                toast = Toast.makeText(MainActivity.this, "宁操作太频繁了\n请稍后重试", Toast.LENGTH_LONG);
            } else if (s.equals(MyApplication.initOnlineOk)) {

            }
            binding.editText.setEnabled(true);
            binding.qiandao.setClickable(true);
            if (toast == null) return;
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
        vm.online.observe(this, aBoolean -> {
            if (aBoolean) {
                vm.getOnlineUser();
            }
        });
        vm.getOnlineUserStatus.observe(this, s -> {
            if (s.equals(MyApplication.succeed)) {
                updateRecyclerViewOfOnlineUser(vm.onlineUserList.getValue());
                vm.onlineNun.setValue(vm.onlineUserList.getValue().size());
                toast0.cancel();
                Toast toast = Toast.makeText(MainActivity.this, "加载完成！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast.makeText(MainActivity.this, "获取在线用户列表失败", Toast.LENGTH_SHORT).show();
            }
        });
        vm.complaintStatus.observe(this, s -> {
            if (s.equals(MyApplication.netWorkError)){
                Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }else if(s.equals(MyApplication.succeed)){
                Toast.makeText(MainActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                binding.editText.setEnabled(false);
                binding.qiandao.setClickable(false);
                vm.online();//从新获取在线状态，更新UI
            }else {
                Toast.makeText(MainActivity.this, "举报失败", Toast.LENGTH_SHORT).show();
            }
        });
        vm.searchText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("")){
                    updateRecyclerViewOfOnlineUser(vm.onlineUserList.getValue());
                }else {
                    updateRecyclerViewOfOnlineUser(vm.search(s));
                }
            }
        });
    }

    private void updateRecyclerViewOfOnlineUser(List<ResponseBean.DataDTO> data) {
        binding.rvOnlineUser.setLayoutManager(new LinearLayoutManager(this));
        OnlineUserListAdapter adapter = new OnlineUserListAdapter(data);
        adapter.setComplaintListener(item -> {//举报点击回调
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("确定要举报Ta么");
            dialog.setMessage(item.getUserId() + "\n" + item.getUserName());
            dialog.setCancelable(false);
            dialog.setPositiveButton("确定", (dialogInterface, i) -> vm.complaint(item));
            dialog.setNegativeButton("取消,Ta正在上班", MainActivity::onClick);
            dialog.show();
        });
        binding.rvOnlineUser.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        toast0 = Toast.makeText(MainActivity.this, "正在加载...", Toast.LENGTH_SHORT);
        toast0.setGravity(Gravity.CENTER, 0, 0);
        toast0.show();
        binding.editText.setEnabled(false);
        binding.qiandao.setClickable(false);
        vm.online.setValue(false);
        vm.online();
        Glide.with(this).load(R.drawable.logo)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .into(binding.mainLogo);
    }

    public class Event {
        public void qiandaoORqiantui(View view) {
            binding.editText.setEnabled(false);
            binding.qiandao.setClickable(false);
            vm.qiandaoORqiantui();
        }
    }
}