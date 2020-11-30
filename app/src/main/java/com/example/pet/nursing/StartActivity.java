package com.example.pet.nursing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private ListView addlv;
    private HisaddAdapter hadapter;
    private RadioGroup yesno;
    private List<HisAddress> addlist = new ArrayList<>();
    private TextView histv;
    private EditText add;
    private EditText men;
    private EditText peo;
    private EditText tel;
    private Button btnsure;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            histv.setText("历史地址");
            findViews();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getViews();
        histv.setText(null);
        initData("");
        btnsure.setEnabled(false);
        yesno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yes:
                        btnsure.setEnabled(true);
                        //上传数据库位置信息
                        break;
                    case R.id.no:
                        btnsure.setEnabled(true);

                        break;
                }
            }
        });
    }

    private void getViews() {
        btnsure = findViewById(R.id.btnsure);
        yesno = findViewById(R.id.yesno);
        addlv = findViewById(R.id.lvhis);
        add = findViewById(R.id.startadd);
        men = findViewById(R.id.startmen);
        peo = findViewById(R.id.startpeo);
        tel = findViewById(R.id.starttel);
        histv = findViewById(R.id.tvhis);
    }

    public void sureadd(View view) {
        if(add.getText().toString().equals("") || men.getText().toString().equals("") || peo.getText().toString().equals("") || tel.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");//标题
            builder.setMessage("信息填写不全");//显示的提示内容
            builder.setPositiveButton("确定", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else{
            Log.e("msg",add.getText().toString()+men.getText().toString()+peo.getText().toString()+tel.getText().toString());
            AddressInfo.START = add.getText().toString() + men.getText().toString();
            AddressInfo.STARTPE = peo.getText().toString();
            AddressInfo.STARTTEL = tel.getText().toString();
            Intent intent = new Intent();
            setResult(999, intent);
            finish();
        }
    }
    public void initData(final String s) {
        new Thread() {
            public void run() {
                //try {
                    /*URL url = new URL( s);
                    url.openStream();
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));*/
                    String str = AddressInfo.START + "!"+ AddressInfo.STARTPE + "!" + AddressInfo.STARTTEL +"!4#";
                    /*reader.close();
                    in.close();*/
                    if (str != null) {
                        String[] infos = str.split("#"); //分割字符串获取数据
                        for (int i = 0; i < infos.length; i++) {
                            String[] finfos = infos[i].split("!"); //分割字符串获取数据
                            String add = finfos[0];
                            String name = finfos[1];
                            String tel = finfos[2];
                            int id = Integer.parseInt(finfos[3]);
                            HisAddress ha = new HisAddress(add,name,tel,id);
                            addlist.add(ha);
                        }
                        Message msg = new Message();
                        handler.sendMessage(msg);
                    }
                } /*catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            //}
        }.start();
    }

    public void findViews() {
        addlv = findViewById(R.id.lvhis);
        hadapter = new HisaddAdapter(this, R.layout.adapter_historyadd, addlist);
        addlv.setAdapter(hadapter);
        addlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressInfo.START = addlist.get(position).getAdd();
                AddressInfo.STARTPE = addlist.get(position).getName();
                AddressInfo.STARTTEL = addlist.get(position).getTel();
                Intent intent = new Intent();
                setResult(999, intent);
                finish();
            }
        });
    }
    
    public void back(View view) {
        finish();
    }
}