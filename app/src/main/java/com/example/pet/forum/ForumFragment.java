package com.example.pet.forum;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pet.R;
import com.example.pet.other.entity.BaseUrl;
import com.example.pet.other.entity.Tips;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment {

    private EditText et_search;
    private Button btn_search;
    private Button btn_publish;
    private RelativeLayout forum_food;
    private RelativeLayout forum_articles;
    private RelativeLayout forum_daily;
    private RelativeLayout forum_cosmetology;
    private RelativeLayout forum_train;
    private RelativeLayout forum_deworming;
    private RelativeLayout forum_disease;
    private RelativeLayout forum_randomtalk;
    private RelativeLayout forum_question;
    private RelativeLayout forum_sharephotos;
    private TextView tv_newest;
    private TextView tv_hot;
    private TextView tv_essence;
    private ListView lv_tips;
    private Banner banner;
    private ArrayList<Tips> arrayList = new ArrayList<>();
    private PopupWindow popupWindow;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        init();
        Log.e("1","1");
        et_search = view.findViewById(R.id.et_main_search);
        btn_search = view.findViewById(R.id.btn_main_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_publish = view.findViewById(R.id.btn_publish_articles);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),PublishActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        forum_food = view.findViewById(R.id.forum_food);
        forum_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_articles = view.findViewById(R.id.forum_articles);
        forum_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_daily = view.findViewById(R.id.forum_daily);
        forum_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_cosmetology = view.findViewById(R.id.forum_cosmetology);
        forum_cosmetology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_train = view.findViewById(R.id.forum_train);
        forum_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_deworming = view.findViewById(R.id.forum_deworming);
        forum_deworming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_disease = view.findViewById(R.id.forum_disease);
        forum_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_randomtalk = view.findViewById(R.id.forum_randomtalk);
        forum_randomtalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_question = view.findViewById(R.id.forum_question);
        forum_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_sharephotos = view.findViewById(R.id.forum_sharephotos);
        forum_sharephotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        tv_newest = view.findViewById(R.id.forum_newest);
        TextPaint base = tv_newest.getPaint();
        base.setFakeBoldText(true);
        tv_hot = view.findViewById(R.id.forum_hot);
        tv_essence = view.findViewById(R.id.forum_essence);
        tv_newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_newest.setBackgroundColor(getResources().getColor(R.color.Blue));
                tv_hot.setBackgroundColor(getResources().getColor(R.color.White));
                tv_essence.setBackgroundColor(getResources().getColor(R.color.White));
                TextPaint newest = tv_newest.getPaint();
                newest.setFakeBoldText(true);
                TextPaint hot = tv_hot.getPaint();
                hot.setFakeBoldText(false);
                TextPaint essence = tv_essence.getPaint();
                essence.setFakeBoldText(false);
            }
        });
        tv_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_newest.setBackgroundColor(getResources().getColor(R.color.White));
                tv_hot.setBackgroundColor(getResources().getColor(R.color.Orange));
                tv_essence.setBackgroundColor(getResources().getColor(R.color.White));
                TextPaint newest = tv_newest.getPaint();
                newest.setFakeBoldText(false);
                TextPaint hot = tv_hot.getPaint();
                hot.setFakeBoldText(true);
                TextPaint essence = tv_essence.getPaint();
                essence.setFakeBoldText(false);
            }
        });
        tv_essence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_newest.setBackgroundColor(getResources().getColor(R.color.White));
                tv_hot.setBackgroundColor(getResources().getColor(R.color.White));
                tv_essence.setBackgroundColor(getResources().getColor(R.color.Purple));
                TextPaint newest = tv_newest.getPaint();
                newest.setFakeBoldText(false);
                TextPaint hot = tv_hot.getPaint();
                hot.setFakeBoldText(false);
                TextPaint essence = tv_essence.getPaint();
                essence.setFakeBoldText(true);
            }
        });


        ArrayList<Integer> images = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("新手推荐文章1");
        title.add("新手推荐文章2");
        title.add("新手推荐文章3");
        title.add("新手推荐文章4");
        title.add("新手推荐文章5");
        images.add(R.drawable.recommend1);
        images.add(R.drawable.recommend2);
        images.add(R.drawable.recommend3);
        images.add(R.drawable.recommend4);
        images.add(R.drawable.recommend5);

        banner = view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(title);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(getContext(),TipsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getContext(),TipsActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getContext(),TipsActivity.class);
                        startActivity(intent);
                        break;
                }
                Log.e("点击了第几张轮播图:",position+"");
            }
        });

        lv_tips = view.findViewById(R.id.lv_tips);
        for (int i = 0; i < 5; i++) {
            Tips tips = new Tips();
            tips.setId(1);
            tips.setUserName("名字" + i);
            tips.setTime("2020-11-28/16:36:0" + i);
            tips.setTopic("标签" + i);
            tips.setTitle("标题" + i);
            tips.setText("正文" + i);
            arrayList.add(tips);
        }
        MainForumTipsAdapter mainForumTipsAdapter = new MainForumTipsAdapter(getContext(),arrayList,R.layout.forum_tips_item);
        lv_tips.setAdapter(mainForumTipsAdapter);
        return view;
    }

    public void init(){
        Log.e("2","2");
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("3","3");
                    URL url = new URL("http://192.168.43.227:8080//LovePet/SearchAllPostServlet");
                    Log.e("5","5");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    Log.e("7","7");
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    Log.e("6","6");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    Log.e("4","4");
                    Log.e("4","4");
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    Log.e("9",jsonArray+"");
                    Log.e("10","10");
                    List<Tips> tipsList = new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        int usedid = jsonObject.getInt("userId");
                        String userName = jsonObject.getString("userName");
                        String userhead = jsonObject.getString("userHead");
                        String time = jsonObject.getString("time");
                        String topic = jsonObject.getString("topic");
                        String title = jsonObject.getString("title");
                        String text = jsonObject.getString("text");
                        int likes = jsonObject.getInt("like");
                        int comments = jsonObject.getInt("comments");
                        int forwards = jsonObject.getInt("forwards");
                        Tips tips = new Tips();
                        tips.setUserName(userName);
                        tips.setId(id);
                        tips.setUserId(usedid);
                        tips.setTime(time);
                        tips.setTopic(topic);
                        tips.setTitle(title);
                        tips.setText(text);
                        tips.setLikes(likes);
                        tips.setComments(comments);
                        tips.setForwards(forwards);
                        Log.e("userid+",jsonObject+"");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void search(){
        new Thread(){
            @Override
            public void run() {
                String search = et_search.getText().toString();
            }
        };
    }

}
