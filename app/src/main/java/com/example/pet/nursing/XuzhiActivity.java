package com.example.pet.nursing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.utils.DistanceUtil;
import com.example.pet.R;

import java.text.DecimalFormat;

public class XuzhiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_xuzhi);
    }
    public void back(View view) {
        finish();
    }
}
