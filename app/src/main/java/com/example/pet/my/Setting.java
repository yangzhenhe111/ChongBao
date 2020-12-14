package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.my.editinfo.EditInfo;
import com.example.pet.other.Cache;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {
    private Toolbar toolbar;
    private List<My> myList1 = new ArrayList<>();
    private List<My> myList2 = new ArrayList<>();
    private List<My> myList3 = new ArrayList<>();
    private List<My> myList4 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //获得控件
        setView();
        //生成数据
        initData();
    }

    private void initData() {
        My my1 = new My(R.drawable.acount, "账号管理", R.drawable.next);
        myList1.add(my1);
        My my2 = new My(R.drawable.address, "地址管理", R.drawable.next);
        myList2.add(my2);
        My my3 = new My(R.drawable.pay, "支付设置", R.drawable.next);
        myList3.add(my3);
        My my4 = new My(R.drawable.bianji, "修改信息", R.drawable.next);
        myList4.add(my4);
    }

    private void setView() {
        toolbar = findViewById(R.id.setting_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.this.finish();
            }
        });
        //适配第四个listView
        MyAdapter myAdapter4 = new MyAdapter(this, R.layout.my_listview, myList4);
        ListView listView4 = (ListView)findViewById(R.id.my_listview4);
        listView4.setAdapter(myAdapter4);
        //适配第一个listView
        MyAdapter myAdapter1 = new MyAdapter(this, R.layout.my_listview, myList1);
        ListView listView1 = (ListView) findViewById(R.id.setting_listview1);
        listView1.setAdapter(myAdapter1);
        //适配第二个listView
        MyAdapter myAdapter2 = new MyAdapter(this, R.layout.my_listview, myList2);
        ListView listView2 = (ListView) findViewById(R.id.setting_listview2);
        listView2.setAdapter(myAdapter2);
        //适配第三个listView
        MyAdapter myAdapter3 = new MyAdapter(this, R.layout.my_listview, myList3);
        ListView listView3 = (ListView) findViewById(R.id.setting_listview3);
        listView3.setAdapter(myAdapter3);
        //账号管理
listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Intent intent = new Intent(Setting.this,AcountManage.class);
                startActivity(intent);

                break;
        }

    }
});
//修改信息
        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch (position) {
                        case 0:
                            Intent intent2 = new Intent(Setting.this, EditInfo.class);
                            startActivity(intent2);
                            break;
                        default:
                            break;
                    }

            }
        });
        //地址管理
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Setting.this,AddressManage.class);
                startActivity(intent);
            }
        });
    }




}