package com.example.pet.my.editinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pet.R;
import com.yiguo.adressselectorlib.AddressSelector;
import com.yiguo.adressselectorlib.CityInterface;
import com.yiguo.adressselectorlib.OnItemClickListener;

public class AddAddress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
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

    }
}