package com.example.pet.forum;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.pet.other.Cache;
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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

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
    private TextView tv_food;
    private TextView tv_articles;
    private TextView tv_daily;
    private TextView tv_cosmetology;
    private TextView tv_train;
    private TextView tv_deworming;
    private TextView tv_disease;
    private TextView tv_randomtalk;
    private TextView tv_question;
    private TextView tv_sharephotos;
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
                    Log.e("handler", "gethere----------------------");
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
        tv_food = view.findViewById(R.id.tv_food);
        tv_articles = view.findViewById(R.id.tv_articles);
        tv_daily = view.findViewById(R.id.tv_daily);
        tv_cosmetology = view.findViewById(R.id.tv_cosmetology);
        tv_train = view.findViewById(R.id.tv_train);
        tv_deworming = view.findViewById(R.id.tv_deworming);
        tv_disease = view.findViewById(R.id.tv_disease);
        tv_randomtalk = view.findViewById(R.id.tv_randomtalk);
        tv_question = view.findViewById(R.id.tv_question);
        tv_sharephotos = view.findViewById(R.id.tv_sharephotos);
        final String food = tv_food.getText().toString();
        final String articles = tv_articles.getText().toString();
        final String daily = tv_daily.getText().toString();
        final String cosmetology = tv_cosmetology.getText().toString();
        final String train = tv_train.getText().toString();
        final String deworming = tv_deworming.getText().toString();
        final String disease = tv_disease.getText().toString();
        final String randomtalk = tv_randomtalk.getText().toString();
        final String question = tv_question.getText().toString();
        final String sharephotos = tv_sharephotos.getText().toString();
        forum_food = view.findViewById(R.id.forum_food);
        forum_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",food);
                getContext().startActivity(intent);
            }
        });
        forum_articles = view.findViewById(R.id.forum_articles);
        forum_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",articles);
                getContext().startActivity(intent);
            }
        });
        forum_daily = view.findViewById(R.id.forum_daily);
        forum_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",daily);
                getContext().startActivity(intent);
            }
        });
        forum_cosmetology = view.findViewById(R.id.forum_cosmetology);
        forum_cosmetology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",cosmetology);
                getContext().startActivity(intent);
            }
        });
        forum_train = view.findViewById(R.id.forum_train);
        forum_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",train);
                getContext().startActivity(intent);
            }
        });
        forum_deworming = view.findViewById(R.id.forum_deworming);
        forum_deworming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",deworming);
                getContext().startActivity(intent);
            }
        });
        forum_disease = view.findViewById(R.id.forum_disease);
        forum_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",disease);
                getContext().startActivity(intent);
            }
        });
        forum_randomtalk = view.findViewById(R.id.forum_randomtalk);
        forum_randomtalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",randomtalk);
                getContext().startActivity(intent);
            }
        });
        forum_question = view.findViewById(R.id.forum_question);
        forum_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",question);
                getContext().startActivity(intent);
            }
        });
        forum_sharephotos = view.findViewById(R.id.forum_sharephotos);
        forum_sharephotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                intent.putExtra("classified",sharephotos);
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
        return view;
    }

    public void init(ArrayList arrayList){
        Log.e("init方法", "----------------------------");
        getImages(arrayList);
        getHeadImages(arrayList);
    }

    public void getImages(final ArrayList<Tips> arrayList) {
        final CountDownLatch latch = new CountDownLatch(arrayList.size());

        for (int i = 0; i < arrayList.size(); i ++ ) {
            final Tips tips = arrayList.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        String path = tips.getImagepath();
                        URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        tips.setThumbnail(bitmap);
                        in.close();
                        latch.countDown();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        try {
            latch.await();
            initAdapter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getHeadImages(final ArrayList<Tips> arrayList) {
        final CountDownLatch latch = new CountDownLatch(arrayList.size());

        for (int i = 0; i < arrayList.size(); i ++ ) {
            final Tips tips = arrayList.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        String path = tips.getHeadImagepath();
                        URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        tips.setUserHead(bitmap);
                        in.close();
                        latch.countDown();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        try {
            latch.await();
            initAdapter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void initAdapter() {
        Log.e("initAdapter方法", "-------------------------");
        MainForumTipsAdapter mainForumTipsAdapter = new MainForumTipsAdapter(getContext(),arrayList,R.layout.forum_tips_item);
        lv_tips.setAdapter(mainForumTipsAdapter);
    }

    public void getAllTips(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetAllPostServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    System.out.println(jsonArray);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int post_id = jsonObject.getInt("post_id");
                        String post_title = jsonObject.getString("post_title");
                        String post_time = jsonObject.getString("post_time");
                        String post_text = jsonObject.getString("post_text");
                        String topic = jsonObject.getString("post_topic");
                        String user_name = jsonObject.getString("user_name");
                        int count_likes = jsonObject.getInt("likes");
                        int count_comments = jsonObject.getInt("comments");
                        int count_forwards = jsonObject.getInt("forwards");
                        String img_path = jsonObject.getString("picture_path");
                        String head_img_path = jsonObject.getString("user_picture_path");
                        Tips tips = new Tips();
                        tips.setId(post_id);
                        tips.setTitle(post_title);
                        tips.setText(post_text);
                        tips.setTime(post_time);
                        tips.setUserName(user_name);
                        tips.setTopic(topic);
                        tips.setLikes(count_likes);
                        tips.setComments(count_comments);
                        tips.setForwards(count_forwards);
                        tips.setImagepath(img_path);
                        tips.setHeadImagepath(head_img_path);
                        arrayList.add(tips);
                        Log.e("post_id", i + "");
                    }
                    Log.e("pppppppppppppppppppppp", "------------------");
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = arrayList;
                    handler.sendMessage(message);
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
