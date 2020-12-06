package com.example.pet.forum;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<com.example.pet.other.entity.Comment> commentList = new ArrayList<>();
    private int itemLayoutRes;

    public CommentAdapter(android.content.Context context, List<Comment> commentList, int itemLayoutRes) {
        this.context = context;
        this.commentList = commentList;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (commentList != null) {
            return commentList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (commentList != null) {
            return commentList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(itemLayoutRes, null);
        Log.e("111111", commentList.toString());
        ImageView head = view.findViewById(R.id.comment_head);
        head.setImageBitmap(commentList.get(position).getHead());
        TextView name = view.findViewById(R.id.comment_name);
        name.setText(commentList.get(position).getName());
        TextView time = view.findViewById(R.id.comment_time);
        time.setText(commentList.get(position).getTime());
        TextView content = view.findViewById(R.id.comment_content);
        content.setText(commentList.get(position).getContent());
        return view;
    }
}
