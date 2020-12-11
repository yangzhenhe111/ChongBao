package com.example.pet.forum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;
import java.util.List;

public class LandlordTipsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Tips> tipsList = new ArrayList<>();
    private int itemLayoutRes;

    public LandlordTipsAdapter(Context context, ArrayList<Tips> tipsList, int itemLayoutRes) {
        this.context = context;
        this.tipsList = tipsList;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (tipsList != null) {
            return tipsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (tipsList != null) {
            return tipsList.get(i);
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
        Log.e("chuanguolaidedongxi", tipsList.toString());
        LinearLayout ll_item = view.findViewById(R.id.landlord_item);
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context,New_post_detail.class);
                context.startActivity(intent);
            }
        });
        TextView landlord_tips_time = view.findViewById(R.id.post_time);
        landlord_tips_time.setText(tipsList.get(i).getTime());
        TextView landlord_tips_title = view.findViewById(R.id.Content_title);
        landlord_tips_title.setText(tipsList.get(i).getTitle());
        ImageView landlord_tips_thumbnail = view.findViewById(R.id.Content_pic);
        landlord_tips_thumbnail.setImageBitmap(tipsList.get(i).getThumbnail());
        ImageView lv_one = view.findViewById(R.id.img_one);
        lv_one.setImageResource(R.drawable.like1);
        TextView landlord_tips_like = view.findViewById(R.id.tv_one);
        landlord_tips_like.setText(tipsList.get(i).getLikes()+"");
        ImageView lv_two = view.findViewById(R.id.img_two);
        lv_two.setImageResource(R.drawable.comment);
        TextView landlord_tips_comment = view.findViewById(R.id.tv_two);
        landlord_tips_comment.setText(tipsList.get(i).getComments()+"");
        return view;
    }
}