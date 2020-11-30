package com.example.pet.nursing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.pet.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HisaddAdapter extends BaseAdapter {
    private viewHolder vh;
    private Context mContext;
    private int itemLayoutId;
    private List<HisAddress> adds = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case 1:
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("提示");//标题
                    builder.setMessage("确定删除历史地址吗？");//显示的提示内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            remove((String)msg.obj);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
                case 2:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
                    builder2.setTitle("提示");//标题
                    builder2.setMessage("历史地址已被删除");//显示的提示内容
                    builder2.setPositiveButton("确定", null);
                    AlertDialog alertDialog2 = builder2.create();
                    alertDialog2.show();
                    break;
                case 3:
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
                    builder3.setTitle("提示");//标题
                    builder3.setMessage("历史地址删除失败");//显示的提示内容
                    builder3.setPositiveButton("确定", null);
                    AlertDialog alertDialog3 = builder3.create();
                    alertDialog3.show();
                    break;
            }
        }
    };

    private class viewHolder{
        TextView add;
        TextView name;
        TextView tel;
        ImageView cha;
        ImageView de;
    }

    public HisaddAdapter(Context mContext, int itemLayoutId, List<HisAddress> adds) {
        this.mContext = mContext;
        this.itemLayoutId = itemLayoutId;
        this.adds = adds;
    }

    @Override
    public int getCount() {
        if (null != adds) {
            return adds.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != adds) {
            return adds.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(itemLayoutId, null);
            vh = new viewHolder();
            vh.add = convertView.findViewById(R.id.add);
            vh.name = convertView.findViewById(R.id.name);
            vh.tel = convertView.findViewById(R.id.tel);
            vh.cha = convertView.findViewById(R.id.chadd);
            vh.de = convertView.findViewById(R.id.deadd);
            convertView.setTag(vh);
        }else{
            vh = (viewHolder) convertView.getTag();
        }
        final HisAddress add = adds.get(position);
        vh.add.setText(add.getAdd());
        vh.name.setText(add.getName());
        vh.tel.setText(add.getTel());
        //设置监听器
        vh.cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ChangehisActivity.class);
                intent.putExtra("add",add.getAdd());
                intent.putExtra("name",add.getName());
                intent.putExtra("tel",add.getTel());
                intent.putExtra("id",add.getId());
                mContext.startActivity(intent);
            }
        });
        vh.de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = add.getId();
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }

    private void remove(final String s) {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL("Removecar" + s);
                    url.openStream();
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String str = reader.readLine();
                    reader.close();
                    in.close();
                    if(str != null) {
                        if (str.equals("移出成功")) {
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        } else {
                            Message msg = new Message();
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}