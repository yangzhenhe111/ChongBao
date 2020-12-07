package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.other.Cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    private String phone ;
    private View progress;
    private View mInputLayout;
    private TextView login;
    private float mWidth, mHeight;
    private LinearLayout mName, mPsw;
    private EditText etName;
    private EditText etPassword;
    private Handler handler = new Handler( ){
        @Override
        public void handleMessage(@NonNull Message msg) {
           switch (msg.what){
               case 1:
                   mName.setVisibility(View.INVISIBLE);
                   mPsw.setVisibility(View.INVISIBLE);
                   inputAnimator(mInputLayout, mWidth, mHeight);
                    Cache.userPhone = phone;
                    Log.e("Login","登录成功"+Cache.userPhone);
                    Intent intent2 = new Intent(Login.this,MyUserService.class);
                    startService(intent2);

                   break;
               case 0:
                   Toast.makeText(Login.this,"密码有误，请重试",Toast.LENGTH_LONG).show();
                   break;
           }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        initView();
    }

    private void initView() {
        login = (TextView) findViewById(R.id.login_btn);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        etName = findViewById(R.id.login_et_username);
        etPassword = findViewById(R.id.login_et_password);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                mWidth = login.getMeasuredWidth();
                mHeight = login.getMeasuredHeight();
                loginService();
                break;
            case R.id.login_register:
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
                break;
            case R.id.login_update:
                Intent intent1 = new Intent(this,Update.class);
                startActivity(intent1);
                break;

        }
    }

    private void loginService() {
        phone = etName.getText().toString();
Cache.userPhone = etName.getText().toString();
        final String pwd = etPassword.getText().toString();
        if(phone.length() ==11 && pwd.length()!=0){
            new Thread(){
                @Override
                public void run() {
                    try {
                        URL url = new URL(Cache.MY_URL+"LoginServlet?num="+phone+"&pwd="+pwd);
                        InputStream in = url.openStream();
                        int num = in.read();
                        Message message = new Message();
                        message.what = num;
                        handler.sendMessage(message);
                        in.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }else{
            Toast.makeText(this,"输入格式有误",Toast.LENGTH_LONG).show();
        }
    }

    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(1500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
                Intent intent1 = new Intent(Login.this,MyDataService.class);
                startService(intent1);
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
            }
        });
    }
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1500);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    public void back(View view) {
        Intent intent = new Intent(Login.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}