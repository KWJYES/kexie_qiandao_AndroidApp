package com.example.kexieqiandao.activity;


import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.kexieqiandao.MyApplication;
import com.example.kexieqiandao.R;
import com.example.kexieqiandao.base.BaseActivity;
import com.example.kexieqiandao.databinding.ActivityMainBinding;
import com.example.kexieqiandao.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    private MainViewModel vm;
    private ActivityMainBinding binding;
    private Toast toast0;

    @Override
    protected void initActivity() {
        setTransparentStatusBar(true);
        setTransparentStatusBar(true);
        vm = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setEvent(new Event());
        binding.setVm(vm);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void observerDataStateUpdateAction() {
        vm.qiandaoStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast toast = null;
                if (s.equals(MyApplication.qiandaoSucceed)){
                    toast = Toast.makeText(MainActivity.this, "签到成功", Toast.LENGTH_SHORT);
                }else if (s.equals(MyApplication.qiantuiSucceed)){
                    toast = Toast.makeText(MainActivity.this, "签退成功", Toast.LENGTH_SHORT);
                }else if (s.equals(MyApplication.qiandaoFail)){
                    toast = Toast.makeText(MainActivity.this, "签到失败！", Toast.LENGTH_SHORT);
                }else if (s.equals(MyApplication.qiantuiFail)){
                    toast = Toast.makeText(MainActivity.this, "签退失败！", Toast.LENGTH_SHORT);
                }else if (s.equals(MyApplication.netWorkError)){
                    toast = Toast.makeText(MainActivity.this, "网络错误！", Toast.LENGTH_SHORT);
                } else if (s.equals(MyApplication.qiandaoRepeat)) {
                    toast = Toast.makeText(MainActivity.this, "重复签到！", Toast.LENGTH_SHORT);
                } else if (s.equals(MyApplication.qiantuiNo)) {
                    toast = Toast.makeText(MainActivity.this, "您没还有签到！", Toast.LENGTH_SHORT);
                }else if (s.equals(MyApplication.qiandaoOutTime)) {
                    toast = Toast.makeText(MainActivity.this, "该时段不允许签到", Toast.LENGTH_LONG);
                }else if (s.equals(MyApplication.initViewOk)){
                    binding.editText.setEnabled(true);
                    binding.qiandao.setClickable(true);
                    toast0.cancel();
                    toast = Toast.makeText(MainActivity.this, "加载完成！", Toast.LENGTH_SHORT);
                }
                if(toast==null) return;
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
    }

    @Override
    protected void initView() {
        toast0 = Toast.makeText(MainActivity.this, "正在加载...", Toast.LENGTH_SHORT);
        toast0.setGravity(Gravity.CENTER,0,0);
        toast0.show();
        binding.editText.setEnabled(false);
        binding.qiandao.setClickable(false);
        vm.online.setValue(false);
        vm.online();
        Glide.with(this).load(R.drawable.logo)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .into(binding.mainLogo);
    }

    public class Event{
        public void qiandaoORqiantui(View view){
            vm.qiandaoORqiantui();
        }
    }
}