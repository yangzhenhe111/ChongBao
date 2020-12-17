package com.example.pet.my.editinfo;

import androidx.appcompat.app.AlertDialog;
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
import com.example.pet.nursing.HisAddress;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Address;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditAddress extends AppCompatActivity {
    private HisAddress hisAddress;
    private EditText etName;
    private EditText etCity;
    private EditText etDetail;
    private EditText etPhone;
    private TextView tvSave;
    private int isPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        Intent intent = getIntent();
        hisAddress = (HisAddress) intent.getSerializableExtra("index");
        setViewContent();
    }

    private void setViewContent() {
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddress.this.finish();
            }
        });
        etName= findViewById(R.id.address_name);
        etCity = findViewById(R.id.address_city);
        etDetail = findViewById(R.id.address_detail);
        etPhone = findViewById(R.id.address_phone);
        tvSave = findViewById(R.id.address_save);
        etName.setText(hisAddress.getName());
        String str = hisAddress.getAdd();
        int index = str.indexOf("市");
        etCity.setText(str.substring(0, index + 1));
        etDetail.setText(str.substring(index + 1));
        etPhone.setText(hisAddress.getTel());
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.address_delete:

                                DeleteAddress();


                break;
            case R.id.address_save:
                if (etName.getText().toString() != null
                        && etCity.getText().toString() != null
                        && etDetail.getText().toString() != null
                        && etPhone.getText().toString() != null) {
                    UpAddress();
                } else {
                    Toast.makeText(EditAddress.this, "所填信息不全", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void UpAddress() {
        Address address = new Address();
        address.setId(hisAddress.getId());
        address.setAddress(etCity.getText().toString() + etDetail.getText().toString());
        address.setPeople(etName.getText().toString());
        address.setTel(etPhone.getText().toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), new Gson().toJson(address));
        Request request = new Request.Builder().post(requestBody).url(Cache.MY_URL + "UpdateAddress").build();
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

    private void DeleteAddress() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Cache.MY_URL + "DeleteAddress?id=" + hisAddress.getId()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("EditActivity", "修改成功");
            }
        });

    }
}