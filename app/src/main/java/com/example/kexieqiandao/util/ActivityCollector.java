package com.example.kexieqiandao.util;

import android.app.Activity;
import android.util.Log;


import com.example.kexieqiandao.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        Log.d("ActivityCollector","activity : "+activity.getClass());
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        Log.d("ActivityCollector","removeActivity : "+activity.getClass());
        activities.remove(activity);
    }

    public static void finishActivity(Class<? extends BaseActivity> activityClass){
        Log.d("ActivityCollector","finishActivity Tag : "+activityClass);
        for (Activity activity : activities) {
            Log.d("ActivityCollector","Activity is : "+activity.getClass());
            if (activity.getClass().equals(activityClass)) {
                Log.d("ActivityCollector","found finishActivity : "+activity.getClass());
                if (!activity.isFinishing()) {
                    activity.finish();
                    activities.remove(activity);
                    Log.d("ActivityCollector","Activity finish : "+activity.getClass());
                }
                break;
            }
        }
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

//    public static void loginOutFinish(){
//        for (Activity activity : activities) {
//            if (!activity.getClass().equals(LoginActivity.class)) {
//                activity.finish();
//                activities.remove(activity);
//            }
//        }
//    }
}
