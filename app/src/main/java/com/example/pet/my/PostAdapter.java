package com.example.pet.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.entity.Article;

import java.util.List;

public class PostAdapter extends BaseAdapter {
    private List<Article> list;
    private Context context;
    private int layoutId;

    public PostAdapter(List<Article> list, Context context, int layoutId) {
        this.list = list;
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView tvTitle;
        TextView tvContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutId, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_article_photo);
            holder.tvContent = convertView.findViewById(R.id.tv_article_content);
            holder.tvTitle = convertView.findViewById(R.id.tv_article_title);
            //缓存对象
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置内容
        holder.imageView.setImageBitmap(list.get(position).getBitmap());
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvContent.setText(list.get(position).getContent());

        return convertView;
    }
}
