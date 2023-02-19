package com.example.kexieqiandao.local;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    /**
     * 使用单例模式
     */
    private SharedPreferencesManager(){}
    private static SharedPreferencesManager sharedPreferencesManager;
    public static SharedPreferencesManager getInstance(){
        if(sharedPreferencesManager==null){
            synchronized (SharedPreferencesManager.class){
                if(null==sharedPreferencesManager){
                    sharedPreferencesManager=new SharedPreferencesManager();
                }
            }
        }
        return sharedPreferencesManager;
    }

    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor getEditor(Context context){
        if(editor==null){
            synchronized (SharedPreferences.Editor.class){
                if(null==editor){
                    editor=context.getSharedPreferences("data",MODE_PRIVATE).edit();
                }
            }
        }
        return editor;
    }

    private SharedPreferences pref;
    private SharedPreferences getPref(Context context){
        if(pref==null){
            synchronized (SharedPreferences.class){
                if(null==pref){
                    pref=context.getSharedPreferences("data",MODE_PRIVATE);
                }
            }
        }
        return pref;
    }


    public String getUId(Context context){
        return getPref(context).getString("uid","null");
    }

    public void applyUId(Context context,String uid){
        getEditor(context).putString("uid",uid);
        getEditor(context).apply();
    }

    public String getUName(Context context){
        return getPref(context).getString("uname","null");
    }

    public void applyUName(Context context,String uname){
        getEditor(context).putString("uname",uname);
        getEditor(context).apply();
    }

    public String getWeek(Context context){
        return getPref(context).getString("week","null");
    }

    public void applyWeek(Context context,String week){
        getEditor(context).putString("week",week);
        getEditor(context).apply();
    }

    public String getTotalTime(Context context){
        return getPref(context).getString("totalTime","null");
    }

    public void applyTotalTime(Context context,String totalTime){
        getEditor(context).putString("totalTime",totalTime);
        getEditor(context).apply();
    }

}
