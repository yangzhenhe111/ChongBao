package com.example.pet.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pet.R;
import com.example.pet.my.editinfo.EditInfo;
import com.example.pet.my.order.MyOrderActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {
    private View view;
    private TextView login;
    private List<My> myList1 = new ArrayList<>();
    private List<My> myList2 = new ArrayList<>();
    private List<My> myList3 = new ArrayList<>();
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Login.class);
                startActivity(intent);
            }
        });
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
                switch (position) {
                    case 0:
                        Intent intent2 = new Intent(getContext(), EditInfo.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                        Intent intent4 = new Intent(getContext(), Pet.class);
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }
            }
        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getContext(), Post.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(), AboutUs.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my,container,false);
        //初始化
        setView();
        setViewContent();
        return view;
    }
//设置控件内容
    private void setViewContent() {

        if(Cache.user !=null){
            infoName.setText(Cache.user.getUserName());
        }else{
            infoName.setText("立即登录");
        }
        Intent intentOrder = new Intent(getContext(),MyDataService.class);
        getContext().startService(intentOrder);



    }

    //获得控件和监听器
    private void setView() {
        MyListener listener = new MyListener();
        infoArticle = view.findViewById(R.id.info_article);
        infoOrder = view.findViewById(R.id.info_order);
        infoArticle.setOnClickListener(listener);
        infoOrder.setOnClickListener(listener);
        infoUpdate = view.findViewById(R.id.info_update);
        infoUpdate.setOnClickListener(listener);
        infoHistory = view.findViewById(R.id.info_history);
        infoHistory.setOnClickListener(listener);
        infoPet = view.findViewById(R.id.info_pet);
        infoPet.setOnClickListener(listener);
        infoSetting = view.findViewById(R.id.info_set);
        infoSetting.setOnClickListener(listener);
        infoAboutUs = view.findViewById(R.id.info_about_us);
        infoAboutUs.setOnClickListener(listener);
        infoPhoto = view.findViewById(R.id.info_photo);
        infoName = view.findViewById(R.id.info_name);
        infoName.setOnClickListener(listener);

    }
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId() ){
                case R.id.info_article:
                    Intent intent = new Intent(getContext(), Post.class);
                    startActivity(intent);
                    break;
                case R.id.info_order:
                    Intent intent1 = new Intent(getContext(), MyOrderActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.info_update:
                    Intent intent2 = new Intent(getContext(), EditInfo.class);
                    startActivity(intent2);
                    break;
                case R.id.info_history:
                    Intent intent3 = new Intent(getContext(), History.class);
                    startActivity(intent3);
                    break;
                case R.id.info_pet:
                    Intent intent4 = new Intent(getContext(), Pet.class);
                    startActivity(intent4);
                    break;
                case R.id.info_set:
                    Intent intent5 = new Intent(getContext(), Setting.class);
                    startActivity(intent5);
                    break;
                case R.id.info_about_us:
                    Intent intent6 = new Intent(getContext(), AboutUs.class);
                    startActivity(intent6);
                    break;
                case R.id.info_name:
                    Intent intent7 = new Intent(getContext(),Login.class);
                    startActivity(intent7);
                    break;
            }
        });
        //调用摄像头
        ImageButton camera=(ImageButton) view.findViewById(R.id.mycamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        return view;
    }
    //初始化数据
    private void init() {
        My my1=new My(R.drawable.bianji,"修改信息",R.drawable.next);
        myList1.add(my1);
        My my2=new My(R.drawable.mytie,"我的帖子",R.drawable.next);
        myList2.add(my2);
        My my3=new My(R.drawable.myorder,"我的订单",R.drawable.next);
        myList2.add(my3);
        My my4=new My(R.drawable.liulan,"浏览记录",R.drawable.next);
        myList2.add(my4);
        My my5=new My(R.drawable.petlogo,"我的宠物",R.drawable.next);
        myList2.add(my5);
        My my6=new My(R.drawable.setting,"个人设置",R.drawable.next);
        myList3.add(my6);
        My my7=new My(R.drawable.we,"关于我们",R.drawable.next);
        myList3.add(my7);
    }
    //加载数据，根据生命周期
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
}
