package com.example.pet.my;

import androidx.annotation.NonNull;

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
import com.example.pet.Start1Activity;
import com.example.pet.chat.BaseActivity;
import com.example.pet.chat.SharedPrefHelper;
import com.example.pet.other.Cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jiguang.analytics.android.api.LoginEvent;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class Login extends BaseActivity {
    @BindView(R.id.login_et_username)
    EditText etName;
    private View progress;
    private View mInputLayout;
    private TextView login;
    private float mWidth, mHeight;
    private LinearLayout mName, mPsw;
    private EditText etPassword;
    SharedPrefHelper sharedPrefHelper;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        login = (TextView) findViewById(R.id.login_btn);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        etPassword = findViewById(R.id.login_et_password);
        sharedPrefHelper = SharedPrefHelper.getInstance();
        if (!sharedPrefHelper.getUserId().equals("")) {
            etName.setText(sharedPrefHelper.getUserId());
        }
    }

    @Override
    protected void initData() {

    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                mWidth = login.getMeasuredWidth();
                mHeight = login.getMeasuredHeight();
                loginService();
                break;
            case R.id.login_register:
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
            case R.id.login_update:
                Intent intent1 = new Intent(this, Update.class);
                startActivity(intent1);
                break;
        }
    }

    private void initLogin(String userName, String passWord, final int type){
        JMessageClient.login(userName, passWord, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                switch (i) {
                    case 801003:
                        showToast(Login.this, "用户名不存在");
                        break;
                    case 871301:
                        showToast(Login.this, "密码格式错误");
                        break;
                    case 801004:
                        showToast(Login.this, "密码错误");
                        handler.sendEmptyMessage(-1);
                        break;
                    case 0:
                        showToast(Login.this, "登录成功");
                        sharedPrefHelper.setUserId(userName);
                        sharedPrefHelper.setUserPW(passWord);
                        LoginEvent event = new LoginEvent("userName", true);
                        JAnalyticsInterface.onEvent(getContext(),event);
                        inputAnimator(mInputLayout, mWidth, mHeight,type);
                        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, UserInfo userInfo) {
                                if (i==0) {
                                    mName.setVisibility(View.INVISIBLE);
                                    mPsw.setVisibility(View.INVISIBLE);
                                    Cache.userPhone = userName;
                                    Intent intent2 = new Intent(Login.this, MyUserService.class);
                                    startService(intent2);
                                    progress.setVisibility(View.VISIBLE);
                                    progressAnimator(progress);
                                    mInputLayout.setVisibility(View.INVISIBLE);
                                    Intent intent1 = new Intent(Login.this, MyDataService.class);
                                    startService(intent1);
                                }
                            }
                        });
                }
            }
        });
    }
    private void loginService() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initLogin(etName.getText().toString(),etPassword.getText().toString(),0);
            }
        }, 0);
    }
    private void inputAnimator(final View view, float w, float h,int type) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(2000);
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
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("LOGINTYPE", type);
                startActivity(intent);
                Login.this.finish();
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
        animator3.setDuration(2000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    public void back(View view) {
        this.finish();
    }
}