package com.example.pet.my;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.my.editinfo.Const;
import com.example.pet.my.editinfo.EditInfo;
import com.example.pet.my.editinfo.GlideEngine;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Pet;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PageFragment extends Fragment {

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
    public int mMaxNum = 200;
    private String image_path;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMER"};

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT);
                upData();
            } else if (msg.what == 2) {
                Log.e("up", "6b");
                Toast.makeText(getContext(), "上传失败了", Toast.LENGTH_SHORT);
            } else if (msg.what == 3) {
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT);
            } else if (msg.what == 4) {
                Log.e("up", "6b");
                Toast.makeText(getContext(), "上传失败了", Toast.LENGTH_SHORT);
            } else if (msg.what == 5) {
                Uri uri = Uri.parse(msg.obj.toString());
                bitmap = BitmapFactory.decodeFile(image_path);
//                petPhoto.setImageBitmap(bitmap);
                Glide.with(getContext())
                        .load(uri)
                        .into(petPhoto);

                //                petPhoto.setImageURI(uri);
            } else if (msg.what == 6) {
                Log.e("up", "6b");
                Toast.makeText(getContext(), "上传失败了", Toast.LENGTH_SHORT);
            } else if (msg.what == 7) {
                petPhoto.setImageBitmap(bitmap);
            }else if(msg.what == 6) {
                Log.e("up","6b");
                Toast.makeText(getContext(),"上传失败了",Toast.LENGTH_SHORT);
            }else if(msg.what == 7){
                Cache.myPetList.remove(index);
                Log.e("delete::::::::::::","true");
                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT);
                Intent intent = new Intent();
                intent.setClass(getContext(),NewPet.class);
                startActivity(intent);
            }else if(msg.what == 8){
                Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT);
            }
        }
    };

    /**
     * 上传图片
     * 未测试
     */
    public void upPhoto(String path) {
//        Cache.myPetList.get(index).setPicturePath(image_path);
//        Cache.myPetList.get(index).setPicture(bitmap);
        Log.e("up", "1");
        File file = new File(path);
        Log.e("up", "2");
        String url = Cache.MY_URL + "UpDataPetInfoServlet";
        OkHttpClient okHttpClient = new OkHttpClient();
        if (file != null && file.exists()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",
                            Cache.userPhone + "" + pet.getPetId() + ".jpg",
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
            Toast.makeText(getActivity(), "请添加头像", Toast.LENGTH_SHORT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pet, container, false);
        Bundle bundle = getArguments();
        pet = (Pet) bundle.get("index");
        getBitmap();
        //设置
        initAge();
        initType();
        MyListener myListener = new MyListener();
        LinearLayout llAge = view.findViewById(R.id.ll_pet_age);
        llAge.setOnClickListener(myListener);
        LinearLayout llType = view.findViewById(R.id.ll_pet_type);
        llType.setOnClickListener(myListener);
        Log.e("upAge", "绑定成功");
        ImageView delete = view.findViewById(R.id.delete_pet);
        delete.setOnClickListener(myListener);
        Log.e("upAge","绑定成功");
        petPhoto = view.findViewById(R.id.pet_photo);
        petPhoto.setOnClickListener(myListener);
        petName = view.findViewById(R.id.pet_name);
        petType = view.findViewById(R.id.pet_type);
        petAge = view.findViewById(R.id.pet_age);
        petAutograph = view.findViewById(R.id.pet_autograph);
        btnUpdate = view.findViewById(R.id.pet_update);
        tvNum = view.findViewById(R.id.tv_input);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upData();
                String type = petType.getText().toString();
//        String age = petAge.getText().toString();
                String autograph = petAutograph.getText().toString();
                String name = petName.getText().toString();
                Log.e("type:", type);
                Log.e("age", agex + "岁");
                Log.e("autogragh:", autograph);
                Log.e("name:", name);
                if (image_path != null) {
                    upPhoto(image_path);
                } else {
                    upData();
                }
            }
        });


//        Intent intent = new Intent(getContext(),MyDataService.class);
//        getContext().startService(intent);
        setViewContent();
        countWords();
        return view;
    }

    //下载宠物图片
    private void getBitmap() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url1 = new URL(Cache.MY_URL + "img/" + pet.getPicturePath());
                    InputStream in1 = url1.openStream();
                    bitmap = BitmapFactory.decodeStream(in1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 7;
                hd.sendMessage(message);
                Log.e("MyDataService", pet.toString());
            }
        }.start();
    }

    private void upData() {
        String type = petType.getText().toString();
//        String age = petAge.getText().toString();
        String autograph = petAutograph.getText().toString();
        String name = petName.getText().toString();
        Log.e("type:", type);
        Log.e("age", agex + "岁");
        Log.e("autogragh:", autograph);
        Log.e("name:", name);
//        Cache.myPetList.get(index).setPetAge(agex);
//        Cache.myPetList.get(index).setPetName(name);
//        Cache.myPetList.get(index).setPetAutograph(autograph);
//        Cache.myPetList.get(index).setPetType(type);
        if (image_path != null && type != null && agex != 0 && autograph != null && name != null) {
            //发送
            pet.setPetId(pet.getPetId());
            pet.setPicturePath(Cache.userPhone + "" + pet.getPetId() + ".jpg");
            pet.setPetType(type);
            pet.setPetAutograph(autograph);
            pet.setPetName(name);
            pet.setPetAge(agex);
            Log.e("pet", pet.toString());
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = RequestBody.create(MediaType.parse(
                    "text/plain;charset=UTF-8"), new Gson().toJson(pet));
            //3.创建请求对象
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(Cache.MY_URL + "UpDataPetInfo2Servlet")
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
                    Log.e("up", "5b");
                    hd.sendMessage(msg);
                    Log.e("up", "6b");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //上传成功
                    Message msg = new Message();
                    String res = response.body().string();
                    if (res.equals("true")) {
                        msg.what = 3;
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
            Toast.makeText(getActivity(), "有信息未填，请填写！", Toast.LENGTH_SHORT);
        }
    }

    public void setViewContent() {
        petAutograph.setText(pet.getPetAutograph(), TextView.BufferType.EDITABLE);
        petName.setText(pet.getPetName(), TextView.BufferType.EDITABLE);
        petType.setText(pet.getPetType(), TextView.BufferType.EDITABLE);
        agex = pet.getPetAge();
        petAge.setText(agex + "岁", TextView.BufferType.EDITABLE);

    }

    public class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_pet_type:
                    pvTypeOptions.show();
                    break;
                case R.id.ll_pet_age:
                    pvAgeOptions.show();
                    Log.e("upAge", "over");
                    break;
                case R.id.pet_photo:
                    upPic();
                    break;
                case R.id.delete_pet:
                    Toast.makeText(getContext(),"开始删除",Toast.LENGTH_SHORT);
                    deletePet();
                    break;
            }
        }

    }

    private void deletePet() {
        Log.e("delete","start");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Cache.MY_URL + "DeletePetServlet?petid=" + Cache.myPetList.get(index).getPetId())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e("delete","22222222");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                Log.e("delete:::::","1111");
                String str = response.body().string();
                Message msg = new Message();
                msg.what = Integer.valueOf(str);
                hd.sendMessage(msg);
            }
        });
    }

    private void upType() {
        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        Log.e("upAge", "start");
        pvTypeOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = typeItem.get(options1);
                petType.setText(tx);
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

    private void upAge() {
        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        Log.e("upAge", "start");
        pvAgeOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
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

    public void initAge() {
        for (int i = 1; i < 30; i++) {
            ageItem.add(i);
        }
    }

    public void initType() {
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
                tvNum.setText("" + count + "/" + mMaxNum);
                if (wordNum.length() > mMaxNum) {
                    int tempSelection = selectionEnd;
                    petAutograph.setText(s);
                    petAutograph.setSelection(tempSelection);//设置光标在最后
                    //吐司最多输入300字
                    Toast.makeText(getContext(), "最多输入" + mMaxNum + "字", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                wordNum = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                //TextView显示剩余字数
                tvNum.setText("" + number + "/" + mMaxNum);
                selectionStart = petAutograph.getSelectionStart();
                selectionEnd = petAutograph.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    petAutograph.setText(s);
                    petAutograph.setSelection(tempSelection);//设置光标在最后
                    //吐司最多输入300字
                    Toast.makeText(getContext(), "最多输入" + mMaxNum + "字", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     * 添加相片
     */
    private void upPic() {
        //动态申请权限
        verifyStoragePermissions(getActivity());
        PictureSelector.create(PageFragment.this)
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = image_path;
                    hd.sendMessage(msg);
                    Log.e("image_path", image_path);
                    //上传图片
                    //      Cache.myPetList.get(index).setPicturePath(image_path);
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
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        Intent intent = new Intent(getContext(),MyDataService.class);
//        getContext().startService(intent);
//        setViewContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        upAge();
        upType();
    }
}
