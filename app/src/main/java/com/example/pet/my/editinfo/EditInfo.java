package com.example.pet.my.editinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditInfo extends AppCompatActivity {
    private User user = new User();
    private TextView upSex;
    private PopupWindow popupWindow;
    private Toolbar toolbar;
    private CircleImageView upPhoto;
    private TextView upBrithday;
    private EditText upName;
    private TimePickerView pvTime;
    private OptionsPickerView pvCustomOptions;
    private ArrayList<String> cardItem = new ArrayList<>();
    //输入框初始值
    private int num = 0;
    //输入框最大值
    public int mMaxNum=200;
    public EditText etInput;
    private TextView tvInput;
    private String image_path;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMER"};

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Toast.makeText(EditInfo.this,"上传成功",Toast.LENGTH_SHORT);
                upData();
            }else if(msg.what == 2){
                Log.e("up","6b");
                Toast.makeText(EditInfo.this,"上传失败了",Toast.LENGTH_SHORT);
            }else if(msg.what == 3){
                Cache.user.setPicturePath(Cache.userPhone + ".jpg");
                Cache.user.setUserBrithday(upBrithday.getText().toString());
                Cache.user.setUserSex(upSex.getText().toString());
                Cache.user.setUserName(upName.getText().toString());
                Cache.user.setUserAutograph(etInput.getText().toString());
                Bitmap bitmap = BitmapFactory.decodeFile(image_path);
                Cache.user.setPhoto(bitmap);
                Toast.makeText(EditInfo.this,"上传成功",Toast.LENGTH_SHORT);
                EditInfo.this.finish();
            }else {
                Log.e("up","6b");
                Toast.makeText(EditInfo.this,"上传失败了",Toast.LENGTH_SHORT);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
//        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
//                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
//        }
        //设置控件内容
        setView();
        initData();
        //加载数据
        upSex();
        upBri();
        countWords();
    }

    private void initData() {
        cardItem.add("未知");
        cardItem.add("男");
        cardItem.add("女");
        Log.e("initData","start");
        if(Cache.user.getPicturePath() != null){
            upPhoto.setImageBitmap(Cache.user.getPhoto());
        }
        if(Cache.user.getUserName() != null){
            upName.setText(Cache.user.getUserName());
        }
        if(Cache.user.getUserSex() != null){
            upSex.setText(Cache.user.getUserSex());
        }
        if(Cache.user.getUserBrithday() != null){
            upBrithday.setText(Cache.user.getUserBrithday());
        }
        if(Cache.user.getUserAutograph() != null){
            etInput.setText(Cache.user.getUserAutograph());
        }
        Log.e("initData","over");
    }

    private void setView() {
        upSex = findViewById(R.id.edit_info_sex);
        toolbar = findViewById(R.id.edit_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfo.this.finish();
            }
        });
        upBrithday = findViewById(R.id.upBrithday);
        upName = findViewById(R.id.upName);
        upPhoto = findViewById(R.id.upPhoto);
        etInput = findViewById(R.id.et_input);
        tvInput = findViewById(R.id.tv_input);



    }

    public void onClicked(View view) {
        switch(view.getId()){
            case R.id.upPhoto:
                //上传头像
                upPic();
                break;
            case R.id.upName:
                break;
            case R.id.ll_upSex:
                pvCustomOptions.show();
                break;
            case R.id.ll_upBrithday:
                pvTime.show();
                break;
            case R.id.tv_save:
                //保存信息
                if(image_path == null){
                    upData();
                }else {
                    upPhoto(image_path);
                }
                break;
        }
    }

    private void upSex() {
        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1);
                upSex.setText(tx);
//                Cache.user.setUserSex(tx);
                user.setUserSex(tx);
            }
        })
                .setLayoutRes(R.layout.picker_sex_bg, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        //自定义布局中的控件初始化及事件处理
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvCancel = v.findViewById(R.id.tv_cancel);
                        TextView tyTitle = v.findViewById(R.id.tv_title);
                        tyTitle.setText("性别");
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                    }
                })
                .setLineSpacingMultiplier(1.5f)//滚轮字体间距
                .setContentTextSize(22)//字体大小
                .setOutSideCancelable(true)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    private void upBri() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                upBrithday.setText(getTimes(date));
//                Cache.user.setUserBrithday(getTimes(date));
                user.setUserBrithday(getTimes(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .setLayoutRes(R.layout.picker_time_bg, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvCannel = v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        tvCannel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(22)//字体大小
                .setOutSideCancelable(true)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.5f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }

    //格式化时间
    private String getTimes(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



    /**
     * 记录字数和限制最大输入字数
     */
    private void countWords() {
        etInput.addTextChangedListener(new TextWatcher() {
            //记录输入的字数
            private CharSequence wordNum;
            private int selectionStart;
            private int selectionEnd;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                wordNum = s;
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    int tempSelection = selectionEnd;
                    etInput.setText(s);
                    etInput.setSelection(tempSelection);//设置光标在最后
                    //吐司最多输入300字
                    Toast.makeText(EditInfo.this, "最多输入"+mMaxNum+"字", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                wordNum= s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                //TextView显示剩余字数
                tvInput.setText("" + number+"/" + mMaxNum);
                selectionStart=etInput.getSelectionStart();
                selectionEnd = etInput.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etInput.setText(s);
                    etInput.setSelection(tempSelection);//设置光标在最后
                    //吐司最多输入300字
                    Toast.makeText(EditInfo.this, "最多输入"+mMaxNum+"字", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void upPic() {
        //动态申请权限
        verifyStoragePermissions(this);
        PictureSelector.create(this)
                .openGallery(PictureConfig.TYPE_IMAGE)
                .imageEngine(GlideEngine.createGlideEngine())
                .imageSpanCount(4)// 每行显示个数 int
                .maxSelectNum(1)
                .isWeChatStyle(true)
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isSingleDirectReturn(true)//PictureConfig.SINGLE模式下是否直接返回
                .isPreviewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .setOutputCameraPath(Const.getImgPath())// 自定义拍照保存路径,可不填
                .isEnableCrop(true)// 是否裁剪 true or false
                .isCompress(true)// 是否压缩 true or false
//                .compressSavePath(Const.getImgPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .synOrAsy(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //接受回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia.isCompressed()){
                        image_path = localMedia.getCompressPath();
                    }else {
                        image_path = localMedia.getPath();
                    }
                    upPhoto.setImageURI(Uri.parse(image_path));
                    //上传图片
//                    Cache.user.setPicturePath(image_path);
                    user.setPicturePath(image_path);
                    break;
            }
        }
    }

    /**
     * 上传图片
     * 未测试
     */
    public void upPhoto(String path){
//        Cache.user.setUserName(upName.getText().toString());
        Log.e("name",upName.getText().toString());
        Log.e("up","1");
        File file = new File(path);
        Log.e("up","2");
        String url = Cache.MY_URL + "UpdataUserInfoServlet";
        OkHttpClient okHttpClient = new OkHttpClient();
        if(file != null && file.exists()){
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",
                            Cache.userPhone + ".jpg",
                            RequestBody.create(MediaType.parse("image/png"),file))
                    .addFormDataPart("phone",Cache.userPhone)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);

            Log.e("up","3");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //失败
                    Log.e("up","4b");
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = 1;
                    Log.e("up","5b");
                    hd.sendMessage(msg);
                    Log.e("up","6b");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //上传成功
                    Log.e("up","4a");
                    Message msg = new Message();
                    String res = response.body().string();
                    if(res.equals("true")){
                        msg.what = 1;
                        msg.obj = 1;
                        Log.e("updata",res);
                    }else {
                        msg.what = 2;
                        msg.obj = 1;
                        Log.e("updata",res);
                    }
                    hd.sendMessage(msg);

                }
            });

        }else {
            Toast.makeText(EditInfo.this,"请添加头像",Toast.LENGTH_SHORT);
        }
    }

    /**
     * 上传数据
     */
    public void upData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建请求体对象
        user.setUserPhone(Cache.userPhone);
        String name = upName.getText().toString();
        if(name == null){
            user.setUserName("");
        }else {
            user.setUserName(name);
        }
        String autograph = etInput.getText().toString();
        if(autograph == null){
            user.setUserAutograph("");
        }else {
            user.setUserAutograph(autograph);
        }
        String path = Cache.user.getPicturePath();
        if(path == null){
            user.setPicturePath("");
        }else {
            user.setPicturePath(path);
        }
        String sex = upSex.getText().toString();
        if(sex == null){
            user.setUserSex("未知");
        }else {
            user.setUserSex(sex);
        }
        String brithday = upBrithday.getText().toString();
        if(brithday == null){
            Calendar selectedDate = Calendar.getInstance();//系统当前时间
            user.setUserBrithday(getTimes(selectedDate.getTime()));
        }else {
            user.setUserBrithday(brithday);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(
                "text/plain;charset=UTF-8"),new Gson().toJson(user));
        //3.创建请求对象
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Cache.MY_URL + "UpDataUserInfo2Servlet")
                .build();
        //3.创建Call对象，发送请求，并接受响应
        Call call = okHttpClient.newCall(request);
        //异步网络请求（无需手动使用子线程）
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Message msg = new Message();
                msg.what = 4;
                msg.obj = 3;
                Log.e("up","5b");
                hd.sendMessage(msg);
                Log.e("up","6b");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功
                Message msg = new Message();
                String res = response.body().string();
                if(res.equals("true")){
                    msg.what = 3;
                    msg.obj = 1;
                    Log.e("updata",res);
                }else {
                    msg.what = 2;
                    msg.obj = 1;
                    Log.e("updata",res);
                }
                hd.sendMessage(msg);

            }
        });
    }

    //然后通过一个函数来申请权限
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}