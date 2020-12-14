package com.example.pet.chat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class GreenDaoHelper {

    Context context;
    DaoMaster.DevOpenHelper helper;
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;


    public GreenDaoHelper(Context context) {
        this.context = context;
    }

    public DaoSession initDao(){
        helper = new DaoMaster.DevOpenHelper(context, "test", null);
        db= helper.getWritableDatabase();
        daoMaster= new DaoMaster(db);
        daoSession= daoMaster.newSession();
        return daoSession;
    }
}
