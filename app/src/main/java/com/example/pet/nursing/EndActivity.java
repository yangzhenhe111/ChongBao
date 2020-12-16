package com.example.pet.nursing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Address;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EndActivity extends AppCompatActivity {
    private ListView addlv;
    private HisaddAdapter hadapter;
    private RelativeLayout choice;
    private RadioGroup yesno;
    private List<HisAddress> addlist = new ArrayList<>();
    private TextView histv;
    private TextView add;
    private EditText men;
    private EditText peo;
    private EditText tel;
    private Button btnsure;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            histv.setText("历史地址");
            findViews();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        getViews();
        histv.setText(null);
        initData("");
        btnsure.setEnabled(false);
        yesno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.yes:
                        btnsure.setEnabled(true);
                        //上传数据库位置信息
                        break;
                    case R.id.no:
                        btnsure.setEnabled(true);

                        break;
                }
            }
        });
    }

    private void getViews() {
        choice = findViewById(R.id.choice);
        btnsure = findViewById(R.id.btnsure);
        yesno = findViewById(R.id.yesno);
        addlv = findViewById(R.id.lvhis);
        add = findViewById(R.id.newend);
        add.setText(AddressInfo.END);
        men = findViewById(R.id.endmen);
        peo = findViewById(R.id.endpeo);
        tel = findViewById(R.id.endtel);
        histv = findViewById(R.id.tvhis);
    }

    public void sureadd(View view) {
        if (add.getText().toString().equals("") || men.getText().toString().equals("") || peo.getText().toString().equals("") || tel.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");//标题
            builder.setMessage("信息填写不全");//显示的提示内容
            builder.setPositiveButton("确定", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Log.e("msg", add.getText().toString() + men.getText().toString() + peo.getText().toString() + tel.getText().toString());
            AddressInfo.END = add.getText().toString() + men.getText().toString();
            AddressInfo.ENDPE = peo.getText().toString();
            AddressInfo.ENDTEL = tel.getText().toString();
            AddAddress();
            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
            intent.putExtra("data", "refresh");
            LocalBroadcastManager.getInstance(EndActivity.this).sendBroadcast(intent);
            sendBroadcast(intent);
            finish();
        }
    }

    private void AddAddress() {
        Address address = new Address();
        address.setAddress(add.getText().toString() + men.getText().toString());
        address.setPeople(peo.getText().toString());
        address.setTel(tel.getText().toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), new Gson().toJson(address));
        Request request = new Request.Builder().post(requestBody).url(Cache.MY_URL + "AddAddress?isPost=0&userId=" + Cache.user.getUserId()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("StartActivity", "上传地址失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("StartActivity", "上传地址成功");
            }
        });

    }

    public void initData(final String s) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(Cache.MY_URL + "MyAddress?isPost=0&userId=" + Cache.user.getUserId());
                    InputStream in = url.openStream();
                    byte[] bytes = new byte[256];
                    StringBuilder builder = new StringBuilder();
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        builder.append(new String(bytes, 0, len, "utf-8"));
                    }
                    Log.e("StartActivity", builder.toString());
                    JSONArray array = new JSONArray(builder.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String add = object.getString("address");
                        String name = object.getString("people");
                        String tel = object.getString("tel");
                        int id = object.getInt("id");
                        HisAddress address = new HisAddress(add, name, tel, id);
                        addlist.add(address);
                    }
                    Message msg = new Message();
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void findViews() {
        addlv = findViewById(R.id.lvhis);
        hadapter = new HisaddAdapter(this, R.layout.adapter_historyadd, addlist);
        addlv.setAdapter(hadapter);
        addlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressInfo.END = addlist.get(position).getAdd();
                AddressInfo.ENDPE = addlist.get(position).getName();
                AddressInfo.ENDTEL = addlist.get(position).getTel();
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data", "refresh");
                LocalBroadcastManager.getInstance(EndActivity.this).sendBroadcast(intent);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
        intent.putExtra("data", "refresh");
        LocalBroadcastManager.getInstance(EndActivity.this).sendBroadcast(intent);
        sendBroadcast(intent);
        finish();
    }

    public void choice(View view) {
        Intent i = new Intent(this, Choice2Activity.class);
        startActivity(i);
    }
}