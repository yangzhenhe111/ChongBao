package com.example.pet.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.BarUtils;
import com.example.pet.R;
import com.example.pet.SystemStatusManager;
import com.example.pet.my.Login;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;

import static cn.jpush.im.android.api.event.LoginStateChangeEvent.Reason.user_logout;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    //记录处于前台的Activity
    private static com.example.pet.chat.BaseActivity  mForegroundActivity = null;

    //记录所有活动的Activity
    private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    Context mContext;
    //加载中
    private ProgressDialog progressDialog;
    SharedPrefHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setNavBarImmersive(this);
        setContentView(rootContentView());
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        ButterKnife.bind(this);
        JMessageClient.registerEventReceiver(this);
        mContext = com.example.pet.chat.BaseActivity.this;
        helper=SharedPrefHelper.getInstance();
        initView();
        initData();
    }

    @Override
    public void onClick(View view) {
    }
    public void onEventMainThread(LoginStateChangeEvent event) {
        final LoginStateChangeEvent.Reason reason = event.getReason();
        if (reason==user_logout) {
            showLongToast(this, "该账号在其他设备登录，被强制下线");
            JMessageClient.logout();
            helper.setUserPW("");
            helper.setNakeName("");
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

        @Override
    protected void onDestroy() {
        //销毁
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();

    }

    /**
     * 返回界面布局
     */
    protected abstract int setContentView();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 处理点击事件
     */
    protected void processClick(View v) {
    }

    /**
     * 获取根View
     */
    public View rootContentView() {


        if (setContentView() != 0) {
            return View.inflate(this, setContentView(), null);
        } else {
            return View.inflate(this, R.layout.page_default, null);
        }
    }


    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
    }

    /**
     * 返回错误
     */
    protected void showPhoneErr() {
       new SVProgressHUD(this).showErrorWithStatus("格式错误",
               SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    /***
     * 关软件盘
     * @param activity
     */
    protected void dismissKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     * @param v
     */
    public static void ShowKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 关闭所有activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (com.example.pet.chat.BaseActivity activity : copy) {
            activity.finish();
        }
    }

    /**
     * 短时间Toast提示
     * @param activity
     * @param s
     */
    public void showToast(Activity activity,String s){
        Toast toast=Toast.makeText(activity,s,Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 长时间Toast提示
     * @param activity
     * @param s
     */
    public void showLongToast(Activity activity,String s){
        Toast toast=Toast.makeText(activity,s,Toast.LENGTH_LONG);
        toast.show();
    }

    /*加载中的进度条*/
    public void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在加载......");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /*自定义消息的加载进度条*/
    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 隐藏正在加载的进度条
     *
     */
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
        }
    }


    /*返回上下文*/
    public Context getContext() {
        return mContext;
    }
}
