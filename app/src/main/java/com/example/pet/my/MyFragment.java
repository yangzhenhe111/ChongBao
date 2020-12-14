package com.example.pet.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.chat.SharedPrefHelper;
import com.example.pet.my.editinfo.EditInfo;
import com.example.pet.my.order.MyOrderActivity;
import com.example.pet.nursing.AddressInfo;
import com.example.pet.nursing.StartActivity;
import com.example.pet.other.Cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.smssdk.ui.companent.CircleImageView;

public class MyFragment extends Fragment {
    private View view;
    private TextView login;
    private TextView count;
    private CircleImageView photo;
    private List<My> myList1 = new ArrayList<>();
    private List<My> myList2 = new ArrayList<>();
    private List<My> myList3 = new ArrayList<>();
    private SharedPrefHelper helper;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        helper = SharedPrefHelper.getInstance();
        login = view.findViewById(R.id.fragment_login);
        count = view.findViewById(R.id.fragment_count);
        photo = view.findViewById(R.id.user_photo);
        if (Cache.user != null) {
            login.setText(Cache.user.getUserName());
            count.setText(Cache.user.getUserPhone());
            if(Cache.user.getPhoto()!=null){
                photo.setImageBitmap(Cache.user.getPhoto());
            }
        }
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Cache.user !=null){
                Intent intent = new Intent(getContext(),EditInfo.class);
                startActivity(intent);}
                else {
                    showLoginToast();
                }
            }
        });

        ScrollView sv = (ScrollView) view.findViewById(R.id.act_solution_4_sv);
        sv.smoothScrollTo(0, 0);

        //适配第一个listView
        MyAdapter myAdapter1 = new MyAdapter(view.getContext(), R.layout.my_listview, myList1);
        ListView listView1 = (ListView) view.findViewById(R.id.my_listview1);
        listView1.setAdapter(myAdapter1);
        //适配第二个listView
        MyAdapter myAdapter2 = new MyAdapter(view.getContext(), R.layout.my_listview, myList2);
        ListView listView2 = (ListView) view.findViewById(R.id.my_listview2);
        listView2.setAdapter(myAdapter2);
        //适配第三个listView
        MyAdapter myAdapter3 = new MyAdapter(view.getContext(), R.layout.my_listview, myList3);
        ListView listView3 = (ListView) view.findViewById(R.id.my_listview3);
        listView3.setAdapter(myAdapter3);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Cache.user != null) {
                    switch (position) {
                        case 0:
                            Intent intent2 = new Intent(getContext(), EditInfo.class);
                            startActivity(intent2);
                            break;
                        default:
                            break;
                    }
                } else {
                    showLoginToast();
                }
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Cache.user != null) {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getContext(), Post.class);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent1 = new Intent(getContext(), MyOrderActivity.class);
                            startActivity(intent1);
                            break;
                        case 2:
                            Intent intent3 = new Intent(getContext(), History.class);
                            startActivity(intent3);
                            break;
                        case 3:
                            Intent intent4 = new Intent(getContext(), NewPet.class);
                            startActivity(intent4);
                            break;
                        default:
                            break;
                    }
                } else {
                    showLoginToast();
                }
            }
        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Cache.user != null) {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getContext(), Setting.class);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent1 = new Intent(getContext(), AboutUs.class);
                            startActivity(intent1);
                            break;
                    }
                }
                else {
                    switch (position) {
                        case 0:
                            showLoginToast();
                            break;
                        case 1:
                            JMessageClient.logout();
                            helper.setUserPW("");
                            helper.setNakeName("");
                            Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
                            intent2.putExtra("data","finish");
                            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
                            getActivity().sendBroadcast(intent2);
                            Intent intent1 = new Intent(getActivity(),Login.class);
                            getActivity().finish();
                            break;
                    }
                }
            }
        });
        return view;
    }

    //初始化数据
    private void init() {
        My my1 = new My(R.drawable.bianji, "修改信息", R.drawable.next);
        myList1.add(my1);
        My my2 = new My(R.drawable.mytie, "我的帖子", R.drawable.next);
        myList2.add(my2);
        My my3 = new My(R.drawable.myorder, "我的订单", R.drawable.next);
        myList2.add(my3);
        My my4 = new My(R.drawable.liulan, "浏览记录", R.drawable.next);
        myList2.add(my4);
        My my5 = new My(R.drawable.petlogo, "我的宠物", R.drawable.next);
        myList2.add(my5);
        My my6 = new My(R.drawable.setting, "个人设置", R.drawable.next);
        myList3.add(my6);
        My my7 = new My(R.drawable.we, "关于我们", R.drawable.next);
        myList3.add(my7);
    }

    //加载数据，根据生命周期
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void showLoginToast() {
        Toast.makeText(getContext(), "请先登录", Toast.LENGTH_LONG).show();
    }
}
