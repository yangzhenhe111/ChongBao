package com.example.pet.my;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import androidx.annotation.Nullable;

import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;


public class MyUserService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyUserService(String name) {
        super(name);
    }

    public MyUserService() {
        super("MyUserService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("MyUserService", Cache.userPhone);

        try {
            URL url = new URL(Cache.MY_URL + "MyUser?phone=" + Cache.userPhone);
            InputStream in = url.openStream();
            StringBuilder str = new StringBuilder();
            byte[] bytes = new byte[256];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                str.append(new String(bytes, 0, len, "utf-8"));
            }
            in.close();
            Cache.user = new Gson().fromJson(str.toString(), User.class);
            Log.e("user",Cache.user.toString());

            if (Cache.user.getPicturePath() != null && Cache.user.getPicturePath().length() != 0) {
                URL url1 = new URL(Cache.MY_URL +"img/"+ Cache.user.getPicturePath());
                InputStream in1 = url1.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in1);
                Cache.user.setPhoto(bitmap);
            }



            Cache.userHashSet.add(Cache.user);
            Log.e("MyUserService", Cache.userHashSet.size()+"");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
