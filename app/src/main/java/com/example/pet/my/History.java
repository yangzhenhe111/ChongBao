package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.forum.New_post_detail;
import com.example.pet.forum.RecyAdapter;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Article;
import com.example.pet.other.entity.Tips;
import com.example.pet.other.entity.User;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private ArrayList<Tips> myPostList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyAdapter recyAdapter;
    private Toolbar toolbar;
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            recyAdapter = new RecyAdapter(History.this,myPostList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(History.this);
            linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recyAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initData();
        setView();
        RecyclerItemClickSupport.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Intent intent = new Intent(History.this, New_post_detail.class);
                intent.putExtra("id",myPostList.get(i).getId());
                startActivity(intent);
            }
        });
    }
    private void setView() {

        recyclerView = findViewById(R.id.lv_history_article);

        toolbar = findViewById(R.id.history_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History.this.finish();
            }
        });
    }

    private void initData() {
        myPostList.clear();
        for (Tips u : Cache.myPostSet) {
            myPostList.add(u);
        }
        Message message = new Message();
        handler.sendMessage(message);
        Log.e("History", myPostList.size() + "");
    }
}