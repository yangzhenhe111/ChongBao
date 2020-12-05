package com.example.pet.chat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Booking extends AppCompatActivity {
    private ListView listView;
    RecyclerView.Adapter adapter;
    private String[] name={"广东共青团","赛雷三分钟","30秒懂车","西子湖畔","虎扑"};
    private int[] headimg={R.drawable.headgong,R.drawable.sailei,R.drawable.car,R.drawable.xizi,R.drawable.hupu};
    private int[] image={R.drawable.gongqingtuan,R.drawable.sai,R.drawable.cars,R.drawable.meish,R.drawable.fin};
    private String[] time={"1小时前","2小时前","2小时前","一天前","一天前"};
    private SimpleAdapter simpleAdapter;
    private ArrayList<Map<String, Object>> arrayList;
    private Context context = null;
    private View view;
    private List<Book> bookList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        //沉浸式状态栏效果设置
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //返回键
        ImageView back=(ImageView)findViewById(R.id.booking_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView=(ListView) findViewById(R.id.booking_list);
        arrayList=new ArrayList<Map<String, Object>>();
        for(int i=0;i<name.length;i++){
            HashMap<String, Object> map=new HashMap<>();
            map.put("mingzi",name[i]);
            map.put("touxiang",headimg[i]);
            map.put("tupian",image[i]);
            map.put("shijian",time[i]);
            arrayList.add(map);
        }
        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.booking_listview,
                new String[]{"mingzi","touxiang","tupian","shijian"},
                new int[]{R.id.booking_name,R.id.booking_headimg,R.id.booking_img,R.id.booking_time}
        );
        listView.setAdapter(simpleAdapter);
        setListViewHeightBasedOnChildren(listView,view,simpleAdapter);
         //RecyclerView适配
        initBooks();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.booking_recycler);
        //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter=new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);


    }
    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, View view, SimpleAdapter adapter) {
        if(listView == null) return;
        if (adapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);

    }
    private void initBooks(){
        Book one=new Book("广东共青...",R.drawable.headgong);
        bookList.add(one);
        Book sailei=new Book("赛雷三分...",R.drawable.sailei);
        bookList.add(sailei);
        Book zhang=new Book("张佳玮写...",R.drawable.zhang);
        bookList.add(zhang);
        Book hupu=new Book("虎扑",R.drawable.hupu);
        bookList.add(hupu);
        Book car=new Book("30秒懂车",R.drawable.car);
        bookList.add(car);
        Book xizi=new Book("西子湖畔",R.drawable.xizi);
        bookList.add(xizi);
        Book ji=new Book("好机友",R.drawable.haojiyou);
        bookList.add(ji);
        Book lei=new Book("赛雷",R.drawable.lei);
        bookList.add(lei);

    }
}
