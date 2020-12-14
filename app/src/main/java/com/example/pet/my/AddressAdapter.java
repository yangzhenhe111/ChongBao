package com.example.pet.my;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.my.editinfo.EditAddress;
import com.example.pet.nursing.HisAddress;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private List<HisAddress> list;
    private int layoutId;
    private Context context;

    public AddressAdapter(List<HisAddress> list, int layoutId, Context context) {
        this.list = list;
        this.layoutId = layoutId;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(list !=null)
            return  list.get(position);
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
            convertView = inflater.inflate(layoutId,null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.address_item_update);
            holder.phone = convertView.findViewById(R.id.address_item_phone);
            holder.city = convertView.findViewById(R.id.address_item_city);
            holder.town = convertView.findViewById(R.id.address_item_town);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置内容
        String str = list.get(position).getAdd();
        int index = str.indexOf("市");
        holder.city.setText(str.substring(0,index));
        holder.town.setText(str.substring(index+1));
        holder.phone.setText(list.get(position).getName()+"    "+list.get(position).getTel());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddress.class);
                intent.putExtra("index",list.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView city;
        TextView town;
        TextView phone;
    }
}
