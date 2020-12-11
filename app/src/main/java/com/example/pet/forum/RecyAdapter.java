package com.example.pet.forum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;
import com.luck.picture.lib.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyHolder> {
    Context context;
    ArrayList<Tips> tipsList;

    public RecyAdapter(Context context, ArrayList<Tips> tipsList) {
        this.context = context;
        this.tipsList = tipsList;
    }

    public Context getContext(){
        return context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_listview_item,parent,false);
        Log.e("----------------------", tipsList.toString());
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String title = tipsList.get(position).getTitle();
        String like = String.valueOf(tipsList.get(position).getLikes());
        String comment = String.valueOf(tipsList.get(position).getComments());
        String time = tipsList.get(position).getTime();
        holder.comment_no.setText(comment);
        holder.comment_img.setImageResource(R.drawable.comment);
        holder.like_no.setText(like);
        holder.like_img.setImageResource(R.drawable.like1);
        holder.thumbnail.setImageBitmap(tipsList.get(position).getThumbnail());
        holder.title.setText(title);
        holder.time.setText(time);

    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        ImageView like_img;
        ImageView comment_img;
        TextView like_no;
        TextView comment_no;
        TextView title;
        TextView time;

        public MyHolder(View itemView){
            super(itemView);
            thumbnail = itemView.findViewById(R.id.Content_pic);
            like_img = itemView.findViewById(R.id.img_one);
            comment_img = itemView.findViewById(R.id.img_two);
            like_no = itemView.findViewById(R.id.tv_one);
            comment_no = itemView.findViewById(R.id.tv_two);
            time = itemView.findViewById(R.id.post_time);
            title = itemView.findViewById(R.id.Content_title);
        }
    }
}