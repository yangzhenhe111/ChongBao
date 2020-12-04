package com.example.pet.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;

import java.util.List;

public class MyAdapter extends ArrayAdapter<My> {
    private int resourceID;
    public MyAdapter(Context context, int textViewResourceID, List<My> objects){
        super(context,textViewResourceID,objects);
        resourceID=textViewResourceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        My my=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        ImageView imageView1=(ImageView)view.findViewById(R.id.my_img);
        ImageView arrow=(ImageView)view.findViewById(R.id.my_arrow);
        TextView textView1=(TextView)view.findViewById(R.id.my_text);
        imageView1.setImageResource(my.getImg());
        arrow.setImageResource(my.getArrow());
        textView1.setText(my.getText());
        return view;
    }
}
