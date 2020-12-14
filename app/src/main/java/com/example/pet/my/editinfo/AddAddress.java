package com.example.pet.my.editinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Address;
import com.google.gson.Gson;
import com.yiguo.adressselectorlib.AddressSelector;
import com.yiguo.adressselectorlib.CityInterface;
import com.yiguo.adressselectorlib.OnItemClickListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddAddress extends AppCompatActivity {
    private EditText etName;
    private EditText etCity;
    private EditText etDetail;
    private EditText etPhone;
    private TextView tvSave;
private int isPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Intent intent = getIntent();
        isPost = intent.getIntExtra("isPost",1);
//        AddressSelector selector = (AddressSelector) findViewById(R.id.address_select);
//        selector.setTabAmount(3);
//        selector.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition) {
//
//            }
//        });
//        selector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {
//
//            }
//        });
        setView();
    }

    private void setView() {
        Toolbar toolbar = findViewById(R.id.add_address_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddress.this.finish();
            }
        });
        etName = findViewById(R.id.address_name);
        etCity = findViewById(R.id.address_city);
        etDetail = findViewById(R.id.address_detail);
        etPhone = findViewById(R.id.address_phone);
        tvSave = findViewById(R.id.address_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传地址
                if(etName.getText().toString()!=null
                        && etCity.getText().toString()!=null
                        && etDetail.getText().toString()!=null
                        &&etPhone.getText().toString()!=null){
                    UpAddress();
                }else{
                    Toast.makeText(AddAddress.this,"所填信息不全",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void UpAddress() {
        Address address = new Address();
        address.setAddress(etCity.getText().toString() +etDetail.getText().toString());
        address.setPeople(etName.getText().toString());
        address.setTel(etPhone.getText().toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody =RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), new Gson().toJson(address));
        Request request = new Request.Builder().post(requestBody).url(Cache.MY_URL+"AddAddress?isPost="+isPost+"&userId="+Cache.user.getUserId()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("StartActivity","上传地址失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("StartActivity","上传地址成功");
            }
        });

    }
}