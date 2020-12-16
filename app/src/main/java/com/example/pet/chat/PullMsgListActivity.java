package com.example.pet.chat;

import android.content.Intent;
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

public class PullMsgListActivity extends BaseActivity {
    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.pull_msg_rv)
    RecyclerView mPullMsgRv;
    @BindView(R.id.pull_msg_rv2)
    RecyclerView mPullMsgRv2;
    @BindView(R.id.pull_msg_push)
    TextView mPullMsgPush;
    @BindView(R.id.title_options_tv)
    TextView mTitleOptionsTv;
    @BindView(R.id.pull_msg_more)
    TextView mPullMsgMore;
    private MessageRecyclerAdapter adapter, adapter2;
    List<MessageBean> list2 = new ArrayList<>();
    private int TYPE_BUTTON = 0;
    private MessageBean bean, bean1;
    private SharedPrefHelper helper;
    private GreenDaoHelper daoHelper;
    private RequestListDao dao;
    private List<RequestList> list = new ArrayList<>();
    private List<MessageBean> list1 = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.activity_pull_msg;
    }

    @Override
    protected void initView() {
        mTitleBarBack.setImageDrawable(getResources().getDrawable(R.drawable.back1));
        mTitleBarTitle.setText("新圈友");
        mTitleOptionsTv.setVisibility(View.VISIBLE);
        helper = SharedPrefHelper.getInstance();
        adapter = new MessageRecyclerAdapter(list1, this);
        adapter2 = new MessageRecyclerAdapter(list2, this);
        initAdapter2();
    }

    @Override
    protected void onResume() {
        list.clear();
        list1.clear();
        adapter.clear();
        initAdapter();
        super.onResume();
    }

    private void initAdapter2() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPullMsgRv2.setLayoutManager(layoutManager);
        //分割线
        mPullMsgRv2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mPullMsgRv2.setAdapter(adapter2);
        final int[] id = {1000, 1001, 1006};
        /*item监听事件*/
        adapter2.setOnItemClickListener(new MessageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /*好友申请*/
    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPullMsgRv.setLayoutManager(layoutManager);
        //分割线
        mPullMsgRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mPullMsgRv.setAdapter(adapter);
        daoHelper = new GreenDaoHelper(this);
        dao = daoHelper.initDao().getRequestListDao();
        //查询所有
        list = dao.queryBuilder().list();
        Collections.reverse(list);
        //这里用于判断是否有数据
        if (list.size() == 0) {
            mPullMsgRv.setVisibility(View.GONE);
        } else {
            mPullMsgRv.setVisibility(View.VISIBLE);
        }
        int size = list.size();
        if (size > 3) {
            for (int i = 0; i < 3; i++) {
                bean1 = new MessageBean();
                bean1.setType(2);
                bean1.setTitle(list.get(i).getNakeName());
                bean1.setContent("验证信息:" + list.get(i).getMsg());
                bean1.setUserName(list.get(i).getUserName());
                bean1.setImg(list.get(i).getImg());
                list1.add(bean1);
            }
        } else {
            for (int i = 0; i < size; i++) {
                bean1 = new MessageBean();
                bean1.setType(2);
                bean1.setTitle(list.get(i).getNakeName());
                bean1.setContent("验证信息:" + list.get(i).getMsg());
                bean1.setUserName(list.get(i).getUserName());
                bean1.setImg(list.get(i).getImg());
                list1.add(bean1);
            }
            Collections.reverse(list1);
        }
        //list倒序排列
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//                JMessageClient.registerEventReceiver(this);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_bar_back, R.id.title_options_tv, R.id.pull_msg_push,R.id.pull_msg_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_options_tv:
                startActivity(new Intent(this, AddFriendsActivity.class));
                break;
            case R.id.pull_msg_push:
                startActivity(new Intent(this, AddFriendsActivity.class));
                break;
            case R.id.pull_msg_more:
                startActivity(new Intent(this,PullMsgListMoreActivity.class));
                break;
        }
    }

}
