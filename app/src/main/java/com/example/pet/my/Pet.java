package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pet.R;

import java.util.ArrayList;
import java.util.List;

public class Pet extends AppCompatActivity {
    private List<String> list = new ArrayList<String>();
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        list.add("蓝色精灵");
        list.add("格斗小五");
        list.add("麋鹿");
        list.add("冰龙王");
        spinner = findViewById(R.id.spinner_pet);
        //定义适配器
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        //设置下拉菜单元样式
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //将适配器添加到下拉列表上
        spinner.setAdapter(adapter);
        //添加监听器
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        toolbar = findViewById(R.id.pet_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pet.this.finish();
            }
        });
    }
}