package com.example.pet.chat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FollowMeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<com.example.pet.other.entity.User> userArrayList = new ArrayList<>();
    private int itemLayoutRes;

    public FollowMeAdapter(Context context, ArrayList<User> userArrayList, int itemLayoutRes) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.itemLayoutRes = itemLayoutRes;

    }

    @Override
    public int getCount() {
        if (userArrayList != null){
            return userArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (userArrayList != null) {
            return userArrayList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(itemLayoutRes, null);
        ImageView iv_head = view.findViewById(R.id.follow_head);
        iv_head.setImageBitmap(userArrayList.get(i).getPhoto());
        TextView tv_name = view.findViewById(R.id.follow_name);
        tv_name.setText(userArrayList.get(i).getUserName());
        TextView tv_autograph = view.findViewById(R.id.follow_autograph);
        tv_autograph.setText(userArrayList.get(i).getUserAutograph());
        Button btn_follow = view.findViewById(R.id.btn_follow_item);
        String str = userArrayList.get(i).getIsFollow();
        if (str.equals("true")){
            btn_follow.setText("☰已关注");
            btn_follow.setBackgroundColor(context.getResources().getColor(R.color.followGray));
        }else{
            btn_follow.setText("＋关注");
            btn_follow.setBackgroundColor(context.getResources().getColor(R.color.followBlue));
        }
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_follow.getText().equals("☰已关注")){
                    btn_follow.setText("＋关注");
                    btn_follow.setBackgroundColor(context.getResources().getColor(R.color.followBlue));
                    Toast.makeText(context,"取关成功!",Toast.LENGTH_SHORT).show();
                    deleteFollow(i);
                }else {
                    btn_follow.setText("☰已关注");
                    btn_follow.setBackgroundColor(context.getResources().getColor(R.color.followGray));
                    Toast.makeText(context,"关注成功!",Toast.LENGTH_SHORT).show();
                    addFollow(i);
                }
            }
        });
        Button btn_letter = view.findViewById(R.id.btn_pri_letter);
        btn_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }


    public void addFollow(int i){
        new Thread(){
            @Override
            public void run() {
                try {
                    int follow_id = userArrayList.get(i).getUserId();
                    int user_id = 2;
                    URL url = new URL(Cache.MY_URL + "AddFollowServlet?user_id="+user_id+"&follow_id="+follow_id);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String a = bufferedReader.readLine();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void deleteFollow(int i){
        new Thread(){
            @Override
            public void run() {
                try {
                    int follow_id = userArrayList.get(i).getUserId();
                    int user_id = 2;
                    URL url = new URL(Cache.MY_URL + "DeleteFollowServlet?user_id="+user_id+"&follow_id="+follow_id);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String b = bufferedReader.readLine();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
