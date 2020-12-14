package com.example.pet.chat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.pet.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Friends extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.fm_contact_rv)
    RecyclerView mFmContactRv;
    Unbinder unbinder;
    @BindView(R.id.fm_contact_no)
    TextView mFmContactNo;
    @BindView(R.id.fm_contact_msg)
    RelativeLayout mFmContactMsg;
    @BindView(R.id.fm_contact_refresh)
    SwipeRefreshLayout mFmContactRefresh;
    private FriendsAdapter mFriendsAdapter;
    private UserInfo info;
    private String[] listUserName = new String[]{"1000", "1006"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        unbinder = ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        mFmContactRefresh.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mFmContactRv.setLayoutManager(layoutManager);
        mFriendsAdapter = new FriendsAdapter(FriendsAdapter.EnumMessageType.FRIENDS);
        mFriendsAdapter.setOnLoadMoreListener(this, mFmContactRv);
//        mFriendsAdapter.
        //分割线
        mFmContactRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mFmContactRv.setAdapter(mFriendsAdapter);
//        initGetList();
        initItemOnClick();
        initGetList();
    }
    /*监听item*/
    private void initItemOnClick() {
        mFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserInfo userInfo = (UserInfo) mFriendsAdapter.getData().get(position);
                Intent intent = new Intent(Friends.this, UserInfoActivity.class);
                intent.putExtra("USERNAME", userInfo.getUserName());
                startActivity(intent);
            }
        });

    }

    /*获取好友列表*/
    private void initGetList() {
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void gotResult(final int i, String s, List<UserInfo> list) {
                if (i == 0) {
                    LogUtils.e("Log:好友数" + list.size());
                    info = list.get(i);
                    mFmContactNo.setVisibility(View.GONE);
                    mFmContactRv.setVisibility(View.VISIBLE);
                    if (list.size() <= 0) {
                        mFmContactRv.setVisibility(View.GONE);
                        mFmContactNo.setVisibility(View.VISIBLE);
                        mFriendsAdapter.setEmptyView(new EmptyView(Friends.this));
                    }
                    Collections.reverse(list);
                    mFriendsAdapter.setNewData(list);
                    mFriendsAdapter.loadMoreEnd();
                } else {
                    mFmContactRv.setVisibility(View.GONE);
                    mFmContactNo.setVisibility(View.VISIBLE);
                }
                mFmContactRefresh.setRefreshing(false);
            }
        });

    }

    @OnClick({R.id.fm_contact_no, R.id.fm_contact_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fm_contact_no:
                Intent intent = new Intent(Friends.this, AddFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.fm_contact_msg:
                startActivity(new Intent(Friends.this, PullMsgListActivity.class));
                break;
            default:break;
        }
    }

    /*批量获取好友在线状态*/
    public void isFriendStateList(String[] listUserName) {

        NetWorkManager.isFriendStateList(listUserName, new Callback<UserStateListBean>() {
            @Override
            public void onResponse(Call<UserStateListBean> call, Response<UserStateListBean> response) {
            }

            @Override
            public void onFailure(Call<UserStateListBean> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onResume() {
//        isFriendStateList(listUserName);
        super.onResume();
    }
    @Override
    public void onRefresh() {
        initGetList();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}