package com.example.pet.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pet.R;
import com.example.pet.my.editinfo.AddAddress;
import com.example.pet.nursing.HisAddress;
import com.example.pet.other.Cache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AddressFragment extends Fragment {
    private View view;
    private ListView listView;
    private TextView textView;
    private int isPost;
    private List addList = new ArrayList();
    private AddressAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("AddressFragment","handleMessage");
            setViewContent();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.address_fragment, container, false);
        setView();
        Bundle bundle = getArguments();
        isPost = bundle.getInt("isPost");
        initData(isPost);
        setViewContent();
        return view;
    }

    public void initData(int isPost) {
        new Thread() {
            public void run() {

                try {
                    URL url = new URL(Cache.MY_URL + "MyAddress?isPost=" + isPost + "&userId=" + Cache.user.getUserId());
                    InputStream in = url.openStream();
                    byte[] bytes = new byte[256];
                    StringBuilder builder = new StringBuilder();
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        builder.append(new String(bytes, 0, len, "utf-8"));
                    }
                    Log.e("AddressFragment", builder.toString());
                    addList.clear();
                    JSONArray array = new JSONArray(builder.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String add = object.getString("address");
                        String name = object.getString("people");
                        String tel = object.getString("tel");
                        int id = object.getInt("id");
                        HisAddress address = new HisAddress(add, name, tel, id);
                        addList.add(address);
                    }
                    Message msg = new Message();
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void setView() {
        listView = view.findViewById(R.id.address_list);
        textView = view.findViewById(R.id.address_manage_add);
        adapter = new AddressAdapter(addList, R.layout.address_item, getContext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAddress.class);
                intent.putExtra("isPost",isPost);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    private void setViewContent() {
       adapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        initData(isPost);
    }
}
