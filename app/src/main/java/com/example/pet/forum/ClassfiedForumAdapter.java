package com.example.pet.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;

public class ClassfiedForumAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    private int tipslayout;

    public ClassfiedForumAdapter(Context context, ArrayList<Tips> tipsArrayList, int tipslayout) {
        this.context = context;
        this.tipsArrayList = tipsArrayList;
        this.tipslayout = tipslayout;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public int getCount() {
        if (tipsArrayList == null){
            return 0;
        }
        return tipsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (tipsArrayList == null){
            return null;
        }
        return tipsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(tipslayout,null);
        }
        ImageView landlordhead = convertView.findViewById(R.id.iv_tips_landlordhead);
        landlordhead.setImageBitmap(tipsArrayList.get(position).getHead());
        TextView landlordname = convertView.findViewById(R.id.tv_tips_landlordname);
        landlordname.setText(tipsArrayList.get(position).getName());
        TextView tipstime = convertView.findViewById(R.id.tv_tips_tipstime);
        tipstime.setText(tipsArrayList.get(position).getTime());
        Button tipstopic = convertView.findViewById(R.id.btn_tips_topic);
        tipstopic.setText(tipsArrayList.get(position).getTopic());
        TextView tipstitle = convertView.findViewById(R.id.tv_tips_tipstitle);
        tipstitle.setText(tipsArrayList.get(position).getTitle());
        ImageView tipsthumbnail = convertView.findViewById(R.id.iv_tips_thumbnail);
        tipsthumbnail.setImageBitmap(tipsArrayList.get(position).getThumbnail());
        TextView tipstext = convertView.findViewById(R.id.tv_tips_text);
        tipstext.setText(tipsArrayList.get(position).getText());
        LinearLayout totips = convertView.findViewById(R.id.btn_tips_totips);
        totips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),TipsActivity.class);
                intent.putExtra("tipsid",tipsArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
        LinearLayout tipslike = convertView.findViewById(R.id.tips_like);
        final ImageView iv_like = convertView.findViewById(R.id.iv_tips_like);
        final TextView tv_like = convertView.findViewById(R.id.tv_tips_like);
        final int[] tips_like = {Integer.parseInt(tv_like.getText().toString())};
        tipslike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (iv_like.getDrawable().getConstantState().equals(getContext().getDrawable(R.drawable.like1).getConstantState())){
                        iv_like.setImageResource(R.drawable.like2);
                    }else{
                        Toast toast = Toast.makeText(context,"您已经点赞过了"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        LinearLayout tipscomment = convertView.findViewById(R.id.tips_comment);
        tipscomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),TipsActivity.class);
                intent.putExtra("tipsid",tipsArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
        LinearLayout tipsforward = convertView.findViewById(R.id.tips_forward);
        final ImageView iv_forward = convertView.findViewById(R.id.iv_tips_forward);
        tipsforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (iv_forward.getDrawable().getConstantState().equals(getContext().getDrawable(R.drawable.forward1).getConstantState())){
                        iv_forward.setImageResource(R.drawable.forward2);
                    }else{
                        Toast toast = Toast.makeText(context,"您已经转发过了"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        return convertView;
    }
}
