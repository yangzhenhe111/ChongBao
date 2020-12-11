package com.example.pet.my;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Register extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView registerAgreement;
    private TextView tvLogin;
    private EditText etPhone;
    private CheckBox cbXieYi;
    private EditText etYan;
    private EditText etPass;
    private EditText etPass1;
    private Button btnGetYan;
    private String phone;
    private int time = 60;
    private static final int CODE_TRUE = 2; //注册成功
    private static final int CODE_FALSE = 3; //已存在
    private static final int CODE_FALSE1 = 4; //未知原因错误

    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == CODE_TRUE){
                Intent intent = new Intent();
                intent.setClass(Register.this,Login.class);
                startActivity(intent);
                Register.this.finish();
            }else if(msg.what == CODE_FALSE){
                Log.e("register","6");
                Log.e("6",msg.obj.toString());
                toast(msg.obj.toString());
            }else if(msg.what == CODE_FALSE1){
                toast("后台服务繁忙，请1分钟后再次申请注册！");
            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //启动短信验证SDK
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btnGetYan.setText("重新发送(" + time + ")");
            } else if (msg.what == -8) {
                btnGetYan.setText("获取验证码");
                btnGetYan.setClickable(true);
                time = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        toast("验证成功，密码是："+etPass.getText().toString());
                        String pass = etPass.getText().toString();
                        String pass1 = etPass1.getText().toString();
                        if(pass.equals(pass1)){
                            insert();
                        }else {
                            toast("请重新验证密码！");
                        }
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){        //获取验证码成功
                        toast("验证码已发送");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                        //返回支持发送验证码的国家列表
                    }
                }else{//错误等在这里（包括验证失败）
                    //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                    ((Throwable)data).printStackTrace();
                    String str = data.toString();
                    toast(str);
                }
            }
        }
    };


    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Register.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView() {
        etPhone = findViewById(R.id.et_phone);
        cbXieYi = findViewById(R.id.cb_xieyi);
        toolbar = findViewById(R.id.register_toolbar);
        etYan = findViewById(R.id.et_yan);
        etPass = findViewById(R.id.et_password);
        etPass1 = findViewById(R.id.et_password1);
        btnGetYan = findViewById(R.id.btn_getYan);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.this.finish();
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.register_agreement:
                Intent intent = new Intent(this,Agreement.class);
                startActivity(intent);
                break;
            case R.id.tv_login:
                Register.this.finish();
                break;
            case R.id.btn_getYan:
                //获取验证码
                phone = etPhone.getText().toString();
                //验证手机号
                yanPhone();
                break;
            case R.id.btn_register:
                //提交注册信息
                String code = etYan.getText().toString().replaceAll("/s","");
                if (!TextUtils.isEmpty(code)) {//判断验证码是否为空
                    //验证
                    Log.e("code",code);
                    SMSSDK.submitVerificationCode( "86",  phone,  code);
                }else{//如果用户输入的内容为空，提醒用户
                    toast("请输入验证码后再提交");
                }
                break;
        }
    }
    /**
     * 上传服务端
     */
    private void insert() {
        //发送数据
        Log.e("register","1");
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .serializeNulls()//序列化空值
                .create();
        User user = new User();
        user.setUserPassword(etPass.getText().toString());
        user.setUserPhone(phone);
        String str = gson.toJson(user);
        Log.e("register","2   " + str);
        RequestBody requestBody = RequestBody.create(MediaType.parse(
                "text/plain;charset=UTF-8"),str);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Cache.MY_URL + "RegisterServlet")
                .build();
        Log.e("register",request.toString());
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                toast("注册失败，原因不明！");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                Log.e("register","4");
                //使用HandlerMessage修改用户界面
                Message msg = new Message();
                String str = response.body().string();
                if(str.equals("true")){
                    msg.what = CODE_TRUE;
                }else if(str.equals("false")){
                    msg.what = CODE_FALSE1;
                }else{
                    msg.what = CODE_FALSE;
                }
                msg.obj = str;
                hd.sendMessage(msg);
                Log.e("register","5");
            }
        });

    }

    private void yanPhone() {
        // 1. 判断手机号是不是11位并且看格式是否合理
        if (!judgePhoneNums(phone)) {
            return;
        }
        // 2. 通过sdk发送短信验证
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("我们将要发送到" + phone + "验证"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phone);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                btnGetYan.setClickable(false);
                btnGetYan.setText("重新发送(" + time + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; time > 0; time--) {
                            handler.sendEmptyMessage(-9);
                            if (time <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Register.this, "已取消" + which, Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();

    }
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;

    }
    /**
     * 验证手机格式
     */
    private boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);

    }
    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    private boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }

    }
    @Override
    protected void onDestroy() {
        //反注册回调监听接口
        SMSSDK.unregisterAllEventHandler();
        //存储手机号
//        inputText = inputPhoneEt.getText().toString();
//        save(inputText);
//        System.out.println("活动毁灭之前是否传值"+inputText);
        super.onDestroy();
    }

}