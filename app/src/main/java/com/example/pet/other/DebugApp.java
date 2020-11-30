package com.example.pet.other;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class DebugApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
