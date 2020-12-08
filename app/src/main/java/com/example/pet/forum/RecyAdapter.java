package com.example.pet.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.List;

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyHolder> {
    Context context;
    List<Tips> tipsList;

    public RecyAdapter(Context context, List<Tips> tipsList) {
        this.context = context;
        this.tipsList = tipsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_listview_item,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String title = tipsList.get(position).getTitle();
        String like = String.valueOf(tipsList.get(position).getLikes());
        String comment = String.valueOf(tipsList.get(position).getComments());
        //未写图片路径
        String time = tipsList.get(position).getTime();
        holder.comments.setText(comment);
        holder.comment.setImageResource(R.drawable.comment);
        holder.likes.setText(like);
        holder.like.setImageResource(R.drawable.like1);
//        holder.img.setImageResource(R.drawable.zhangwei);
        holder.title.setText(title);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView img;
        ImageView like;
        ImageView comment;
        TextView likes;
        TextView comments;
        TextView title;
        TextView time;

        public MyHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.Content_pic);
            like = itemView.findViewById(R.id.img_one);
            comment = itemView.findViewById(R.id.img_two);
            likes = itemView.findViewById(R.id.tv_one);
            comments = itemView.findViewById(R.id.tv_two);
            time = itemView.findViewById(R.id.post_time);
            title = itemView.findViewById(R.id.Content_title);
        }
    }
}