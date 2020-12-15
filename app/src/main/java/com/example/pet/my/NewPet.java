package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Pet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewPet extends AppCompatActivity {
    private LinearLayout index;
    private List<Pet> myPetList;
    private ImageView[] indicaterViews;
    private final ViewGroup.LayoutParams params_WRAP_CONTENT = new ViewGroup.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            setView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);
        initData();

    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (myPetList != null) {
                        myPetList.clear();
                    } else {
                        myPetList = new ArrayList<>();
                    }
                    URL url = new URL(Cache.MY_URL + "MyPet?userId=" + Cache.user.getUserId());
                    InputStream in = url.openStream();
                    StringBuilder str = new StringBuilder();
                    byte[] bytes = new byte[256];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        str.append(new String(bytes, 0, len, "utf-8"));
                    }
                    in.close();
                    JSONArray jsonArray = new JSONArray(str.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject rs = jsonArray.getJSONObject(i);
                        Pet pet = new Gson().fromJson(rs.toString(), Pet.class);
                        Log.e("pet", pet.toString());
                        String path = rs.getString("picturePath");
                        myPetList.add(pet);

                    }
                    Message message = new Message();
                    handler.sendMessage(message);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void setView() {

        index = findViewById(R.id.pet_indicators);
        TextView addPet = findViewById(R.id.add_new_pet);
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                index = null;
                Intent intent = new Intent(NewPet.this, PetActivity.class);
                startActivityForResult(intent,1);
            }
        });
        ViewPager pager = findViewById(R.id.pet_pager);
        Toolbar toolbar = findViewById(R.id.pet_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPet.this.finish();
            }
        });
        initIndicaterDots(myPetList.size());
        List<PageFragment> list = new ArrayList<>();
        for (int i = 0; i < myPetList.size(); i++) {
            PageFragment fragment = new PageFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("index", myPetList.get(i));
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        pager.setOnPageChangeListener(new PageChangeListener());
    }

    private void initIndicaterDots(int size) {
        // 初始化索引指示器的容器Layout
        indicaterViews = new ImageView[size];
        // 循环取得小点图片
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params_WRAP_CONTENT);
            imageView.setImageResource(R.drawable.ic_buy_box_indicator_circle);
            imageView.setEnabled(false);// 都设为灰色
            imageView.setPadding(10, 5, 10, 5);
            indicaterViews[i] = imageView;
            indicaterViews[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
            index.addView(imageView);
        }
        if (indicaterViews.length > 0) indicaterViews[0].setEnabled(true);// 设置为白色，即选中状态
    }

    /**
     * 滑动横幅监听器
     */
    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int i) {
            // 设置底部小点选中状态
            setIndicaterIndex(i);
        }

        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }

    /**
     * 这只当前引导小点的选中
     */
    private void setIndicaterIndex(int position) {
        if (position < 0 || indicaterViews == null || position > indicaterViews.length - 1) return;
        for (int i = 0; i < indicaterViews.length; i++) {
            if (i == position)
                indicaterViews[i].setEnabled(true);
            else
                indicaterViews[i].setEnabled(false);
        }
        if (position == indicaterViews.length - 1) {
            index.setVisibility(View.VISIBLE);
        } else {
            index.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            myPetList = null;
            index.removeAllViews();
            indicaterViews = null;
            initData();
        }
    }
}