package com.example.pet.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;

import java.util.List;

public class AcountAdapter extends BaseAdapter {
    private List<User> list;
    private Context context;
    private int layoutId;

    public AcountAdapter(List<User> list, Context context, int layoutId) {
        this.list = list;
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if(list !=null)
            return  list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(list !=null)
            return  list.get(position);
        return null;
    }
    private class ViewHolder {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvPhone;
        ImageView ivChecked;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutId, null);
            holder = new ViewHolder();
            holder.ivChecked = convertView.findViewById(R.id.acount_item_checked);
            holder.ivPhoto = convertView.findViewById(R.id.acount_item_photo);
            holder.tvPhone = convertView.findViewById(R.id.acount_item_phone);
            holder.tvName = convertView.findViewById(R.id.acount_item_name);
            //缓存对象
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置内容
        holder.ivPhoto.setImageBitmap(list.get(position).getPhoto());
        holder.tvPhone.setText(list.get(position).getUserPhone());
        holder.tvName.setText(list.get(position).getUserName());
        if(list.get(position).getUserId()==Cache.user.getUserId()){
            holder.ivChecked.setVisibility(View.VISIBLE);
        }else{
            holder.ivChecked.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
