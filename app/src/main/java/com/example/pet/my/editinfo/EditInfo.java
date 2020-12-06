package com.example.pet.my.editinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.pet.R;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditInfo extends AppCompatActivity {
    private TextView upSex;
    private PopupWindow popupWindow;
    private Toolbar toolbar;
    private CircleImageView upPhoto;
    private TextView upBrithday;
    private TextView upName;
    private TimePickerView pvTime;
    private OptionsPickerView pvCustomOptions;
    private ArrayList<String> cardItem = new ArrayList<>();


    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMER"};
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
            }
        })
                .setLayoutRes(R.layout.picker_sex_bg, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        //自定义布局中的控件初始化及事件处理
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvCancel = v.findViewById(R.id.tv_cancel);
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
                .setOutSideCancelable(true)
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
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
        cardItem.add("未知");
        cardItem.add("男");
        cardItem.add("女");
        //加载数据
        upSex();
        upBri();



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
                .setOutputCameraPath(Const.getImgPath())// 自定义拍照保存路径,可不填
                .isEnableCrop(true)// 是否裁剪 true or false
                .isCompress(true)// 是否压缩 true or false
                .compressSavePath(Const.getImgPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
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
                    Log.e("地址：",selectList.get(0).getCompressPath());
                    upPhoto.setImageURI(Uri.parse(selectList.get(0).getCompressPath()));
                    //上传图片
//                    setPicture(selectList.get(0).getCompressPath());
                    break;
            }
        }
    }
    /**
     * 上传图片
     * 未测试
     */
    public static void setPicture(String path){
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),new File(path));
        MultipartBody body=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//上传图片格式一般都是这个格式MediaType.parse("multipart/form-data")
                .addFormDataPart("name","nate")//除了图片在上传一些东西
                .addFormDataPart("filename","grile.jpg",requestBody).build();//图片服务器定义名字，
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url("").post(body).build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                Log.e("response",response.body().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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