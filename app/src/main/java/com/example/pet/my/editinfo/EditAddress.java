package com.example.pet.my.editinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pet.R;
import com.example.pet.nursing.HisAddress;

public class EditAddress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
       Intent intent = getIntent();
        HisAddress address = (HisAddress) intent.getSerializableExtra("index");
    }
}