package com.example.pet.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> commentList = new ArrayList<>();
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
    public Object getItem(int i) {
        if (commentList != null) {
            return commentList.get(i);
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
        ImageView head = view.findViewById(R.id.comment_head);
        TextView name = view.findViewById(R.id.comment_name);
        TextView time = view.findViewById(R.id.comment_time);
        TextView content = view.findViewById(R.id.comment_content);
        return view;
    }
}
