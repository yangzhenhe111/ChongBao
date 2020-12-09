package com.example.pet.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;
import java.util.List;

public class LandlordTipsAdapter extends BaseAdapter {
    private Context context;
    private List<Tips> tipsList = new ArrayList<>();
    private int itemLayoutRes;

    public LandlordTipsAdapter(Context context, List<Tips> myList, int itemLayoutRes) {
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
        ImageView landlord_tips_head = view.findViewById(R.id.landlord_tips_landlordhead);
        landlord_tips_head.setImageBitmap(tipsList.get(i).getUserHead());
        TextView landlord_tips_name = view.findViewById(R.id.landlord_tips_landlordname);
        landlord_tips_name.setText(tipsList.get(i).getUserName());
        TextView landlord_tips_time = view.findViewById(R.id.landlord_tips_tipstime);
        landlord_tips_time.setText(tipsList.get(i).getTime());
        Button landlord_tips_topic = view.findViewById(R.id.landlord_tips_topic);
        landlord_tips_topic.setText(tipsList.get(i).getTopic());
        TextView landlord_tips_title = view.findViewById(R.id.landlord_tips_tipstitle);
        landlord_tips_title.setText(tipsList.get(i).getTitle());
        ImageView landlord_tips_thumbnail = view.findViewById(R.id.landlord_tips_thumbnail);
        landlord_tips_thumbnail.setImageBitmap(tipsList.get(i).getThumbnail());
        TextView landlord_tips_text = view.findViewById(R.id.tv_tips_text);
        landlord_tips_text.setText(tipsList.get(i).getText());
        TextView landlord_tips_like = view.findViewById(R.id.landlord_like);
        landlord_tips_like.setText(tipsList.get(i).getLikes());
        TextView landlord_tips_comment = view.findViewById(R.id.landlord_comment);
        landlord_tips_comment.setText(tipsList.get(i).getComments());
        TextView landlord_tips_forward = view.findViewById(R.id.landlord_forward);
        landlord_tips_forward.setText(tipsList.get(i).getForwards());
        return view;
    }
}