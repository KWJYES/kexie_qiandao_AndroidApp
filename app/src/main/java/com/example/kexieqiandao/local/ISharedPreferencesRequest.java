package com.example.kexieqiandao.local;

import android.content.Context;

public interface ISharedPreferencesRequest {
    String getAccount(Context context);
    void applyAccount(Context context,String account);
}
