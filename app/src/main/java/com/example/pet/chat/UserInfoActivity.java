package com.example.pet.chat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pet.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户资料
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.userinfo_scroll)
    ScrollView mUserinfoScroll;
    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.title_bar_left)
    LinearLayout mTitleBarLeft;
    @BindView(R.id.title_options_tv)
    TextView mTitleOptionsTv;
    @BindView(R.id.title_options_img)
    ImageView mTitleOptionsImg;
    @BindView(R.id.title_bar_right)
    RelativeLayout mTitleBarRight;
    @BindView(R.id.bottom_bar_tv1)
    TextView mBottomBarTv1;
    @BindView(R.id.bottom_bar_left)
    RelativeLayout mBottomBarLeft;
    @BindView(R.id.bottom_bar_tv2)
    TextView mBottomBarTv2;
    @BindView(R.id.bottom_bar_right)
    RelativeLayout mBottomBarRight;
    @BindView(R.id.title)
    LinearLayout mTitle;
    @BindView(R.id.userinfo_nikename)
    TextView mUserinfoNikename;
    @BindView(R.id.userinfo_username)
    TextView mUserinfoUsername;
    @BindView(R.id.userinfo_gender)
    TextView mUserinfoGender;
    @BindView(R.id.userinfo_mtime)
    TextView mUserinfoMtime;
    private SharedPrefHelper helper;
    private String getUserName = "";

    @Override
    protected int setContentView() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initView() {
        getUserName = getIntent().getStringExtra("USERNAME");
        initBar();
        initUserInfo(getUserName);
        mTitleBarTitle.setText("个人资料");
        JAnalyticsInterface.onPageStart(this, this.getClass().getCanonicalName());
    }

    /*获取用户资料*/
    private void initUserInfo(final String userName) {
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    Picasso.with(com.example.pet.chat.UserInfoActivity.this)
                            .load(userInfo.getAvatarFile())
                            .placeholder(R.mipmap.icon_user);
                    mUserinfoGender.setText(StringUtils.constant2String(userInfo.getGender() + ""));
                    mUserinfoMtime.setText("上次互动：" + TimeUtils.unix2Date("yyyy-MM-dd HH:mm", userInfo.getmTime()));
                    mUserinfoNikename.setText(userInfo.getNickname() + "");
                    mUserinfoUsername.setText(userInfo.getUserName() + "");
                    CountEvent cEvent = new CountEvent("event_userId_" + userInfo.getUserID());
                    JAnalyticsInterface.onEvent(getContext(), cEvent);
                } else {
                }
            }
        });


    }

    private void initBar() {
        helper = SharedPrefHelper.getInstance();
        mTitleBarBack.setImageDrawable(getResources().getDrawable(R.drawable.back1));
        mTitleBarTitle.setText("");
        mBottomBarLeft.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JAnalyticsInterface.onPageEnd(this, this.getClass().getCanonicalName());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_bar_back, R.id.title_options_tv, R.id.bottom_bar_tv1, R.id.bottom_bar_tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_options_tv:
                Intent intent1 = new Intent(com.example.pet.chat.UserInfoActivity.this, UserInfoOptionsActivity.class);
                intent1.putExtra("NAKENAME", getIntent().getStringExtra("NICKNAME"));
                startActivity(intent1);
                break;
            case R.id.bottom_bar_tv2:
                /*创建会话*/
                Conversation conv = JMessageClient.getSingleConversation(getIntent().getStringExtra("USERNAME"), "7153b7916be94ab289793e76");
                //如果会话为空，使用EventBus通知会话列表添加新会话
                if (conv == null) {
                    Conversation.createSingleConversation(getIntent().getStringExtra("USERNAME"), "7153b7916be94ab289793e76");
                }
                JMessageClient.getConversationList().add(conv);
                Intent intent = new Intent(com.example.pet.chat.UserInfoActivity.this, ChatMsgActivity.class);
                intent.putExtra("NAKENAME", getIntent().getStringExtra("NICKNAME"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
