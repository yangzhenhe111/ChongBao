package com.example.pet.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Tips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cn.smssdk.ui.companent.CircleImageView;

public class SearchResultAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    private int searchLayout;

    public SearchResultAdapter(Context context, ArrayList<Tips> tipsArrayList, int searchLayout) {
        this.context = context;
        this.tipsArrayList = tipsArrayList;
        this.searchLayout = searchLayout;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public int getCount() {
        if (tipsArrayList == null){
            return 0;
        }
        return tipsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (tipsArrayList == null){
            return null;
        }
        return tipsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(searchLayout,null);
        }
        CircleImageView landlordhead = view.findViewById(R.id.iv_tips_landlordhead);
        landlordhead.setImageBitmap(tipsArrayList.get(position).getUserHead());
        landlordhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),LandlordActivity.class);
                getContext().startActivity(intent);

            }
        });
        TextView landlordname = view.findViewById(R.id.tv_tips_landlordname);
        landlordname.setText(tipsArrayList.get(position).getUserName());
        TextView tipstime = view.findViewById(R.id.tv_tips_tipstime);
        tipstime.setText(tipsArrayList.get(position).getTime());
        Button tipstopic = view.findViewById(R.id.btn_tips_topic);
        tipstopic.setText(tipsArrayList.get(position).getTopic());
        TextView tipstitle = view.findViewById(R.id.tv_tips_tipstitle);
        tipstitle.setText(tipsArrayList.get(position).getTitle());
        ImageView tipsthumbnail = view.findViewById(R.id.iv_tips_thumbnail);
        tipsthumbnail.setImageBitmap(tipsArrayList.get(position).getThumbnail());
        TextView tipstext = view.findViewById(R.id.tv_tips_text);
        String str = tipsArrayList.get(position).getText();
        int i = 40;
        str = str.substring(0,i) + "...";
        tipstext.setText(str);
        TextView tipslikes = view.findViewById(R.id.tv_tips_like);
        tipslikes.setText(""+tipsArrayList.get(position).getLikes());
        TextView tipscomments = view.findViewById(R.id.tv_tips_comment);
        tipscomments.setText(""+tipsArrayList.get(position).getComments());
        TextView tipsforwards = view.findViewById(R.id.tv_tips_forward);
        tipsforwards.setText(""+tipsArrayList.get(position).getForwards());
        LinearLayout totips = view.findViewById(R.id.btn_tips_totips);
        totips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),TipsActivity.class);
                int i = tipsArrayList.get(position).getId();
                String id = Integer.toString(i);
                Log.e("St id", id);
                intent.putExtra("tipsid",id);
                context.startActivity(intent);
            }
        });
        LinearLayout tipslike = view.findViewById(R.id.tips_like);
        final ImageView iv_like = view.findViewById(R.id.iv_tips_like);
        final TextView tv_like = view.findViewById(R.id.tv_tips_like);
        String s = tv_like.getText().toString();
        final int likes = Integer.parseInt(s);
        tipslike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = likes;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (iv_like.getDrawable().getConstantState().equals(getContext().getDrawable(R.drawable.like1).getConstantState())){
                        i = i + 1;
                        Log.e("点赞后", i+"");
                        tv_like.setText(String.valueOf(i));
                        iv_like.setImageResource(R.drawable.like2);
                        int id = tipsArrayList.get(position).getId();
                        publishLikes(id,i);
                    }else{
                        Toast toast = Toast.makeText(context,"您已经点赞过了"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        LinearLayout tipscomment = view.findViewById(R.id.tips_comment);
        tipscomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),TipsActivity.class);
                intent.putExtra("tipsid",tipsArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
        LinearLayout tipsforward = view.findViewById(R.id.tips_forward);
        final ImageView iv_forward = view.findViewById(R.id.iv_tips_forward);
        tipsforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (iv_forward.getDrawable().getConstantState().equals(getContext().getDrawable(R.drawable.forward1).getConstantState())){
                        iv_forward.setImageResource(R.drawable.forward2);
                    }else{
                        Toast toast = Toast.makeText(context,"您已经转发过了"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        return view;
    }
    public void publishLikes(final int id, final int likes){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "likes?post_id="+id+"&likes="+likes);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String isPublish = bufferedReader.readLine();
                    Log.e("返回的东西", isPublish);
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
