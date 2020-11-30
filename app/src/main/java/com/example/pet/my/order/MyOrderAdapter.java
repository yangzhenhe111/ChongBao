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
    private  List<Order> orders = new ArrayList<Order>();
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
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itrnLayoutRes,null);
        }
        TextView tvFlag = convertView.findViewById(R.id.tv_flag);
        TextView tvStart = convertView.findViewById(R.id.tv_start);
        TextView tvEnd = convertView.findViewById(R.id.tv_end);
        TextView tvTime = convertView.findViewById(R.id.tv_order_time);
        TextView tvState = convertView.findViewById(R.id.tv_order_state);

        tvFlag.getBackground().setAlpha(60);
        tvStart.setText(orders.get(position).getOrderStart());
        tvEnd.setText(orders.get(position).getOrderEnd());
        tvTime.setText(orders.get(position).getOrderTime());
        String state = orders.get(position).getOrderState();
        tvState.setText(state);
        if("待支付".equals(state)){
            tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab2));
        }else if("待接单".equals(state)){
            tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab2));
        }else if("已接单".equals(state)){
            tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab));
        }else if("已完成".equals(state)){
            tvState.setTextColor(convertView.getResources().getColor(R.color.change_tab));
        }
        return convertView;
    }
}
