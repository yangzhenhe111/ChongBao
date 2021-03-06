package com.example.pet.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 发送申请请求
 */

public class AddFriendMsgActivity extends BaseActivity {
    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.title_options_tv)
    TextView mTitleOptionsTv;
    @BindView(R.id.add_msg_icon)
    ImageView mAddMsgIcon;
    @BindView(R.id.add_msg_name)
    TextView mAddMsgName;
    @BindView(R.id.add_msg_content)
    EditText mAddMsgContent;
    private String content;
    private SharedPrefHelper helper;
    @Override
    protected int setContentView() {
        return R.layout.activity_add_msg;
    }

    @Override
    protected void initView() {
        helper = SharedPrefHelper.getInstance();
        initTitleBar();
        initEdit();
    }

    private void initEdit() {
        mAddMsgContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                content = editable.toString();
            }
        });
    }

    private void initTitleBar() {
        mTitleOptionsTv.setText("发送");
        mTitleOptionsTv.setVisibility(View.VISIBLE);
        mTitleBarBack.setImageDrawable(getResources().getDrawable(R.drawable.back1));
        mTitleBarBack.setVisibility(View.VISIBLE);
        mTitleBarTitle.setText("添加好友");
    }

    @Override
    protected void initData() {
        mAddMsgName.setText(getIntent().getStringExtra("NAME"));
        mAddMsgContent.setText("你好！我是"+ JMessageClient.getMyInfo().getNickname());
        Picasso.with(com.example.pet.chat.AddFriendMsgActivity.this)
                .load(getIntent().getStringExtra("ICON"))
                .placeholder(R.drawable.chatmoren)
                .into(mAddMsgIcon);
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
                ContactManager.sendInvitationRequest(getIntent().getStringExtra("ID"),"",content, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                showToast(com.example.pet.chat.AddFriendMsgActivity.this, "发送成功");
                                finish();
                            }else {
                                showToast(com.example.pet.chat.AddFriendMsgActivity.this, "发送失败:"+s);
                                Log.e("id====", getIntent().getStringExtra("ID"));
                            }
                        }
                    });
                break;
        }
    }
}
