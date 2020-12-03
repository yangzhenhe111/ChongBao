package com.example.pet.forum;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Map;

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
    private ArrayList<Tips> tipsArrayList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    init((ArrayList) message.obj);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        getAllTips();
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
//        for (int i = 0; i < 5; i++) {
//            Tips tips = new Tips();
//            tips.setId(1);
//            tips.setUserName("名字" + i);
//            tips.setTime("2020-11-28/16:36:0" + i);
//            tips.setTopic("标签" + i);
//            tips.setTitle("标题" + i);
//            tips.setText("正文" + i);
//            arrayList.add(tips);
//        }
        return view;
    }

    public void init(ArrayList arrayList){
        MainForumTipsAdapter mainForumTipsAdapter = new MainForumTipsAdapter(getContext(),arrayList,R.layout.forum_tips_item);
        lv_tips.setAdapter(mainForumTipsAdapter);
    }

    public void getAllTips(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http:172.20.10.2:8080/ChongBao_war_exploded/GetAllPostServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    Log.e("3", "sybs");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    Log.e("4", "sybs");
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    Log.e("5", "sybs");
                    tipsArrayList = new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String post_title = jsonObject.getString("post_title");
                        String post_time = jsonObject.getString("post_time");
                        String post_text = jsonObject.getString("post_text");
                        String topic = jsonObject.getString("post_topic");
                        String user_name = jsonObject.getString("user_name");
                        int count_likes = jsonObject.getInt("likes");
                        int count_comments = jsonObject.getInt("comments");
                        int count_forwards = jsonObject.getInt("forwards");
                        Tips tips = new Tips();
                        tips.setTitle(post_title);
                        tips.setText(post_text);
                        tips.setTime(post_time);
                        tips.setUserName(user_name);
                        tips.setTopic(topic);
                        tips.setLikes(count_likes);
                        tips.setComments(count_comments);
                        tips.setForwards(count_forwards);
                        arrayList.add(tips);

                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = arrayList;
                        handler.sendMessage(message);
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
