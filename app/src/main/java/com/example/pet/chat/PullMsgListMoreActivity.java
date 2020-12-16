package com.example.pet.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看更多好友请求
 */

public class PullMsgListMoreActivity extends BaseActivity {

    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.title_options_tv)
    TextView mTitleOptionsTv;
    @BindView(R.id.list_more_rv)
    RecyclerView mListMoreRv;
    private List<RequestList> list = new ArrayList<>();
    private List<MessageBean> list1 = new ArrayList<>();
    private MessageRecyclerAdapter adapter;
    private GreenDaoHelper daoHelper;
    private RequestListDao dao;

    @Override
    protected int setContentView() {
        return R.layout.activity_more_msglist;
    }

    @Override
    protected void initView() {
        initBar();

    }

    private void initBar() {
        mTitleBarBack.setImageDrawable(getResources().getDrawable(R.drawable.back1));
        mTitleBarTitle.setText("圈友通知");
    }

    @Override
    protected void initData() {
        {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mListMoreRv.setLayoutManager(layoutManager);
            adapter = new MessageRecyclerAdapter(list1, this);
            //分割线
            mListMoreRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mListMoreRv.setAdapter(adapter);

            daoHelper = new GreenDaoHelper(this);
            dao = daoHelper.initDao().getRequestListDao();
            //查询所有
            list = dao.queryBuilder().list();

            //这里用于判断是否有数据
            if (list.size() == 0) {
                mListMoreRv.setVisibility(View.GONE);
            } else {
                mListMoreRv.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < list.size(); i++) {
              MessageBean bean1 = new MessageBean();
                bean1.setType(2);
                bean1.setTitle(list.get(i).getNakeName());
                bean1.setContent("验证信息:" + list.get(i).getMsg());
                bean1.setUserName(list.get(i).getUserName());
                bean1.setImg(list.get(i).getImg());
                list1.add(bean1);
            }
            //list倒序排列
            Collections.reverse(list1);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_bar_back, R.id.title_options_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_options_tv:
                break;
        }
    }
}
