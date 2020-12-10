package com.example.pet.my;

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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.pet.R;
import com.example.pet.my.editinfo.Const;
import com.example.pet.my.editinfo.GlideEngine;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Pet;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PetActivity extends AppCompatActivity {
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    private List<String> list = new ArrayList<String>();
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private Toolbar toolbar;



    private ImageView petPhoto;
    private EditText petName;
    private TextView petType;
    private TextView petAge;
    private EditText petAutograph;
    private Button btnUpdate;
    private TextView tvNum;
    private int index;
    private Pet pet;
    private int agex;
    private Bitmap bitmap;


    private OptionsPickerView pvAgeOptions;
    private OptionsPickerView pvTypeOptions;
    private ArrayList<Integer> ageItem = new ArrayList<>();
    private ArrayList<String> typeItem = new ArrayList<>();
    //输入框初始值
    private int num = 0;
    //输入框最大值
    public int mMaxNum=200;
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
                Toast.makeText(PetActivity.this,"上传成功",Toast.LENGTH_SHORT);
                upData();
            }else if(msg.what == 2){
                Log.e("up","6b");
                Toast.makeText(PetActivity.this,"上传失败了",Toast.LENGTH_SHORT);
            }else if(msg.what == 3){
                pet.setPicture(bitmap);
                Cache.myPetList.add(pet);
                Toast.makeText(PetActivity.this,"上传成功",Toast.LENGTH_SHORT);
            }else if(msg.what == 4) {
                Log.e("up","6b");
                Toast.makeText(PetActivity.this,"上传失败了",Toast.LENGTH_SHORT);
            }else if(msg.what == 5){
                Uri uri = Uri.parse(msg.obj.toString());
                bitmap = BitmapFactory.decodeFile(image_path);
                petPhoto.setImageBitmap(bitmap);
                //                petPhoto.setImageURI(uri);
            }else if(msg.what == 6) {
                Log.e("up","6b");
                Toast.makeText(PetActivity.this,"上传失败了",Toast.LENGTH_SHORT);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        upAge();
        upType();
        setViewContent();
    }

    private void setViewContent() {
        //设置
        pet = new Pet();
        initAge();
        initType();
        Log.e("upAge", "绑定成功");
        petPhoto = findViewById(R.id.pet_photo1);
        petName = findViewById(R.id.pet_name1);
        petType = findViewById(R.id.pet_type1);
        petAge = findViewById(R.id.pet_age1);
        petAutograph = findViewById(R.id.pet_autograph1);
        btnUpdate = findViewById(R.id.pet_add);
        tvNum = findViewById(R.id.tv_input1);
        countWords();

    }


    public void initAge(){
        for(int i = 1;i < 30;i++){
            ageItem.add(i);
        }
    }
    public void initType(){
        typeItem.add("哈士奇");
        typeItem.add("兔子");
        typeItem.add("加菲猫");
        typeItem.add("小白鼠");
        typeItem.add("无皮猫");
        typeItem.add("土狗");
        typeItem.add("仓鼠");
    }


    /**
     * 记录字数和限制最大输入字数
     */
    private void countWords() {
        petAutograph.addTextChangedListener(new TextWatcher() {
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
                    petAutograph.setText(s);
                    petAutograph.setSelection(tempSelection);//设置光标在最后
                    //吐司最多输入300字
                    Toast.makeText(PetActivity.this, "最多输入"+mMaxNum+"字", Toast.LENGTH_SHORT).show();

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
                tvNum.setText("" + number+"/" + mMaxNum);
                selectionStart=petAutograph.getSelectionStart();
                selectionEnd = petAutograph.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    petAutograph.setText(s);
                    petAutograph.setSelection(tempSelection);//设置光标在最后
                    //吐司最多输入300字
                    Toast.makeText(PetActivity.this, "最多输入"+mMaxNum+"字", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void close(View view) {
        PetActivity.this.finish();
    }

    /**
     * 上传图片
     * 未测试
     */
    public void upPhoto(String path) {
        pet.setPicturePath(image_path);
        Log.e("up", "1");
        File file = new File(path);
        Log.e("up", "2");
        String url = Cache.MY_URL + "UpDataPetInfoServlet";
        OkHttpClient okHttpClient = new OkHttpClient();
        if (file != null && file.exists()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",
                            Cache.userPhone +"" + Cache.myPetList.size() + ".jpg",
                            RequestBody.create(MediaType.parse("image/png"), file))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);

            Log.e("up", "3");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //失败
                    Log.e("up", "4b");
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = 1;
                    Log.e("up", "5b");
                    hd.sendMessage(msg);
                    Log.e("up", "6b");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //上传成功
                    Log.e("up", "4a");
                    Message msg = new Message();
                    String res = response.body().string();
                    if (res.equals("true")) {
                        msg.what = 1;
                        msg.obj = 1;
                        Log.e("updata", res);
                    } else {
                        msg.what = 2;
                        msg.obj = 1;
                        Log.e("updata", res);
                    }
                    hd.sendMessage(msg);

                }
            });

        } else {
            Toast.makeText(PetActivity.this, "请添加头像", Toast.LENGTH_SHORT);
        }
    }
    private void upData() {
        String type = petType.getText().toString();
//        String age = petAge.getText().toString();
        String autograph = petAutograph.getText().toString();
        String name = petName.getText().toString();
        Log.e("type:",type);
        Log.e("age",agex+"岁");
        Log.e("autogragh:",autograph);
        Log.e("name:",name);
        if(image_path != null &&  type != null && agex != 0 && autograph != null && name != null){
            //发送
//            pet.setPetId(Cache.myPetList.get(index).getPetId());
            pet.setPicturePath(Cache.userPhone +""+ Cache.myPetList.size()+ ".jpg");
            pet.setPetType(type);
            pet.setPetAutograph(autograph);
            pet.setPetName(name);
            pet.setPetAge(agex);
            pet.setUserId(Cache.user.getUserId());
            Log.e("pet",pet.toString());
            String str = new Gson().toJson(pet);
            Log.e("petStr",str);
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = RequestBody.create(MediaType.parse(
                    "text/plain;charset=UTF-8"),str);
            //3.创建请求对象
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(Cache.MY_URL + "AddPetServlet")
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
        }else {
            Toast.makeText(PetActivity.this,"有信息未填，请填写！",Toast.LENGTH_SHORT);
        }
    }


    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.btn_close_add_pet:
                PetActivity.this.finish();
                break;
            case R.id.ll_pet_type1:
                pvTypeOptions.show();
                break;
            case R.id.ll_pet_age1:
                pvAgeOptions.show();
                break;
            case R.id.pet_photo1:
                upPic();
                break;
            case R.id.pet_add:
//                upData();
                String type = petType.getText().toString();
//        String age = petAge.getText().toString();
                String autograph = petAutograph.getText().toString();
                String name = petName.getText().toString();
                Log.e("type:",type);
                Log.e("age",agex+"岁");
                Log.e("autogragh:",autograph);
                Log.e("name:",name);
                if(image_path != null){
                    upPhoto(image_path);
                }/*else {
                    upData();
                }*/
                break;


        }
    }

    private void upType() {
        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        Log.e("upAge","start");
        pvTypeOptions = new OptionsPickerBuilder(PetActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = typeItem.get(options1);
                petType.setText(tx);
                Log.e("type:::::::::::::",tx);
            }
        })
                .setLayoutRes(R.layout.picker_sex_bg, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        //自定义布局中的控件初始化及事件处理
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvCancel = v.findViewById(R.id.tv_cancel);
                        TextView tyTitle = v.findViewById(R.id.tv_title);
                        tyTitle.setText("宠物种类");
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTypeOptions.returnData();
                                pvTypeOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTypeOptions.dismiss();
                            }
                        });

                    }
                })
                .setLineSpacingMultiplier(1.5f)//滚轮字体间距
                .setContentTextSize(22)//字体大小
                .setOutSideCancelable(true)
                .build();
        pvTypeOptions.setPicker(typeItem);//添加数据
    }

//    private void upType() {
//        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
//        // 具体可参考demo 里面的两个自定义布局
//        Log.e("upAge","start");
//        pvTypeOptions = new OptionsPickerBuilder(PetActivity.this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                String tx = typeItem.get(options1);
//                petType.setText(tx);
//                Log.e("type:::::::::::::",tx);
//            }
//        })
//                .setLayoutRes(R.layout.picker_sex_bg, new CustomListener() {
//                    @Override
//                    public void customLayout(View v) {
//                        //自定义布局中的控件初始化及事件处理
//                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
//                        TextView tvCancel = v.findViewById(R.id.tv_cancel);
//                        TextView tyTitle = v.findViewById(R.id.tv_title);
//                        tyTitle.setText("宠物种类");
//                        tvSubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvTypeOptions.returnData();
//                                pvTypeOptions.dismiss();
//                            }
//                        });
//                        tvCancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvTypeOptions.dismiss();
//                            }
//                        });
//
//                    }
//                })
//                .setLineSpacingMultiplier(1.5f)//滚轮字体间距
//                .setContentTextSize(22)//字体大小
//                .setOutSideCancelable(true)
//                .build();
//        pvTypeOptions.setPicker(typeItem);//添加数据
//    }

    private void upAge() {
        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        Log.e("upAge","start");
        pvAgeOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                int tx = ageItem.get(options1);
                petAge.setText(tx + "岁");
                agex = tx;
            }
        })
                .setLayoutRes(R.layout.picker_sex_bg, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        //自定义布局中的控件初始化及事件处理
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvCancel = v.findViewById(R.id.tv_cancel);
                        TextView tyTitle = v.findViewById(R.id.tv_title);
                        tyTitle.setText("宠物年龄");
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvAgeOptions.returnData();
                                pvAgeOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvAgeOptions.dismiss();
                            }
                        });

                    }
                })
                .setLineSpacingMultiplier(1.5f)//滚轮字体间距
                .setContentTextSize(22)//字体大小
                .setOutSideCancelable(true)
                .build();
        pvAgeOptions.setPicker(ageItem);//添加数据
    }


    /**
     * 添加相片
     */
    private void upPic() {
        //动态申请权限
        verifyStoragePermissions(PetActivity.this);
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
                .synOrAsy(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //接受回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    image_path = selectList.get(0).getCompressPath();
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = image_path;
                    hd.sendMessage(msg);
                    Log.e("image_path",image_path);
                    //上传图片
                    Cache.myPetList.get(index).setPicturePath(image_path);
//                    Cache.user.setPicturePath(image_path);
                    break;
            }
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



    @Override
    protected void onResume() {
        super.onResume();
        initAge();
        initType();
    }
}