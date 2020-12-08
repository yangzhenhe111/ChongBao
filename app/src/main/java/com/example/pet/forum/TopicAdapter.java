package com.example.pet.forum;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.entity.Topic;

import java.util.ArrayList;

public class TopicAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Topic> topicArrayList = new ArrayList<>();
    private int topiclayout;

    public TopicAdapter(Context context, ArrayList<Topic> topicArrayList, int topiclayout) {
        this.context = context;
        this.topicArrayList = topicArrayList;
        this.topiclayout = topiclayout;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public int getCount() {
        if (topicArrayList == null){
            return 0;
        }
        return topicArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (topicArrayList == null){
            return null;
        }
        return topicArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(topiclayout,null);
        }
        LinearLayout topic = convertView.findViewById(R.id.topic);
        TextView name = convertView.findViewById(R.id.topic_name);
        name.setText(topicArrayList.get(position).getName());
        TextView count = convertView.findViewById(R.id.topic_count);
        count.setText(topicArrayList.get(position).getCount());
        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(),PublishActivity.class);
                intent.putExtra("topic",topicArrayList.get(position).getName());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
