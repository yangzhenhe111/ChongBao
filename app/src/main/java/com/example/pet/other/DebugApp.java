package com.example.pet.other;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;

public class DebugApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
