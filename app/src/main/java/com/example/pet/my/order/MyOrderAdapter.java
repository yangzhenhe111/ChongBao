package com.example.pet.my.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends BaseAdapter {

    private Context context ;
    private  List<Order> orders ;
    private int itrnLayoutRes;

    public MyOrderAdapter(Context context, List<Order> orders, int itrnLayoutRes) {
        this.context = context;
        this.orders = orders;
        this.itrnLayoutRes = itrnLayoutRes;
    }
    public MyOrderAdapter(){
    }

    @Override
    public int getCount() {
        if(null != orders){
            return orders.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null != orders){
            return orders.get(position);
        }
        return null;
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
            convertView = inflater.inflate(itrnLayoutRes,null);
            holder = new ViewHolder();
            holder.tvFlag = convertView.findViewById(R.id.tv_flag);
            holder.tvStart = convertView.findViewById(R.id.tv_start);
           holder.tvEnd = convertView.findViewById(R.id.tv_end);
           holder.tvTime = convertView.findViewById(R.id.tv_order_time);
            holder.tvState = convertView.findViewById(R.id.tv_order_state);
            holder.petInfo = convertView.findViewById(R.id.tv_pet_info);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.petInfo.setText(orders.get(position).getPet().getPetName()+"/"+orders.get(position).getPet().getPetWeight());
        holder.tvFlag.getBackground().setAlpha(60);
        holder.tvStart.setText(orders.get(position).getOrderStart());
        holder.tvEnd.setText(orders.get(position).getOrderEnd());
        holder.tvTime.setText(orders.get(position).getOrderTime());
        String state = orders.get(position).getOrderState();
        holder.tvState.setText(state);
        if("待支付".equals(state)){
            holder.tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab2));
        }else if("待接单".equals(state)){
            holder.tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab2));
        }else if("已接单".equals(state)){
            holder.tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab));
        }else if("已完成".equals(state)){
            holder.tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab));
        }
        return convertView;
    }
    private class ViewHolder{
        TextView tvFlag ;
        TextView tvStart ;
        TextView tvEnd ;
        TextView tvTime ;
        TextView tvState ;
        TextView petInfo;
    }
}
