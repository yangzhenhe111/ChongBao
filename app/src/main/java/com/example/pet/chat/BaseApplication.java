package com.example.pet.chat;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.Utils;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_SILENCE;

public class BaseApplication extends Application {
    public static com.example.pet.chat.BaseApplication baseApplication;
    public MySQLiteOpenHelper helper;
    private DaoMaster master;
    private SharedPrefHelper sharedPrefHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.init(getApplicationContext(), true);
        MultiDex.install(this);
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        baseApplication = this;
        MultiDex.install(this);
        sharedPrefHelper = SharedPrefHelper.getInstance();
        sharedPrefHelper.setRoaming(true);
        //开启极光调试
        JPushInterface.setDebugMode(true);
        //实例化极光推送
        JPushInterface.init(this);
        //实例化极光IM,并自动同步聊天记录

        JMessageClient.setDebugMode(true);
        //初始化极光sms
//        SMSSDK.getInstance().initSdk(mContext);
        //初始化数据库
        setupDatabase();
        //通知管理,通知栏开启，其他关闭
        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE);
        //初始化utils
        Utils.init(this);
        //推送状态
        initJPush2();
        //初始化统计
        JAnalyticsInterface.init(this);
        JAnalyticsInterface.setDebugMode(true);

    }

    private void initJPush2() {
        sharedPrefHelper.setMusic(false);
        sharedPrefHelper.setVib(false);
        sharedPrefHelper.setAppKey("b47a37f342eba5f9fbcd1961");
    }

    private void setupDatabase() {
        //是否开启调试
        MigrationHelper.DEBUG = true;
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        //数据库升级
        helper = new MySQLiteOpenHelper(this, "text");
        master = new DaoMaster(helper.getWritableDb());

    }
}
