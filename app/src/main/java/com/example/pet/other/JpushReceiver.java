package com.example.pet.other;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.pet.nursing.JiedanActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JpushReceiver extends JPushMessageReceiver {
    private Context mcontext;
    private Context mcontext2;
    private Intent i = new Intent();
    private Intent i2 = new Intent();

    private String cimg;
    private String csize;
    private String cprice;
    private String cname;
    private String cintroduction;

    private String uname;
    private String oid;
    private String oname;
    private String oprice;
    private String time;
    private String condition;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setClass(mcontext, JiedanActivity.class);
                    mcontext.startActivity(i);
                    break;
                case 2:
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i2.setClass(mcontext2, JiedanActivity.class);
                    mcontext2.startActivity(i2);
                    break;
            }
        }
    };
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        mcontext2 = context;
        String id = null;
        String username = null;
        try {
            JSONObject json = new JSONObject(customMessage.extra);
            username = json.getString("用户名"); //根据自定义消息的键值对信息获取用户名
            id = json.getString("订单编号");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //if(Username.USERNAME.equals(username)){ //指定用户
            //findOrderById(id);
        //}
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean b, int i) {
        super.onNotificationSettingsCheck(context, b, i);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        mcontext = context;
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }
    /*private void findCakeByName(final String name) { //根据促销通知的标题中提取的蛋糕名称获取相关信息
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(Config.SERVER_ADDR + "Searchcake" + "?cname=" + name);
                    url.openStream();
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String str = reader.readLine();
                    reader.close();
                    in.close();
                    if (str != null) {
                        String[] finfos = str.split("!"); //分割字符串获取数据
                        cimg = finfos[0];
                        cname = finfos[1];
                        csize = finfos[2];
                        cprice = finfos[3];
                        cintroduction = finfos[4];
                    }
                    i.putExtra("name", cname);
                    i.putExtra("price", cprice);
                    i.putExtra("img",cimg);
                    i.putExtra("intro",cintroduction);
                    i.putExtra("size",csize);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }*/
    /*private void findOrderById(final String id) { //根据自定义消息的键值对中的id寻找订单
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(Config.SERVER_ADDR + "OrderInfo" + "?id=" + id);
                    url.openStream();
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String str = reader.readLine();
                    reader.close();
                    in.close();
                    if (str != null) {
                        String[] finfos = str.split("!"); //分割字符串获取数据
                        uname = finfos[0];
                        oid = finfos[1];
                        oname = finfos[2];
                        oprice = finfos[3];
                        time = finfos[4];
                        condition = finfos[5];
                    }
                    i2.putExtra("uname", uname);
                    i2.putExtra("oid", oid);
                    i2.putExtra("oname",oname);
                    i2.putExtra("oprice",oprice);
                    i2.putExtra("time",time);
                    i2.putExtra("condition",condition);
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }*/
}
