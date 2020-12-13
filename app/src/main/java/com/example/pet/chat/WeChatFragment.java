package com.example.pet.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.pet.R;
import com.example.pet.forum.LandlordActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;


public class WeChatFragment extends Fragment {

    @BindView(R.id.fragment_main_group)
    RelativeLayout mFragmentMainGroup;
    @BindView(R.id.fragment_main_none)
    TextView mFragmentMainNone;
    @BindView(R.id.fragment_main_rf)
    SwipeRefreshLayout mFragmentMainRf;
    private List<MessageBean> data = new ArrayList<>();
    private List<Conversation> list=new ArrayList<>();

    Conversation conversation;
    @BindView(R.id.fragment_main_rv)
    RecyclerView mFragmentMainRv;
    Unbinder unbinder;
    MessageRecyclerAdapter adapter;
    @BindView(R.id.fragment_main_header)
    RecyclerViewHeader mFragmentMainHeader;
    @BindView(R.id.item_main_img)
    ImageView mItemMainImg;
    @BindView(R.id.item_main_username)
    TextView mItemMainUsername;
    @BindView(R.id.item_main_content)
    TextView mItemMainContent;
    @BindView(R.id.item_main_time)
    TextView mItemMainTime;
    private int groupID = 0;
    MessageBean bean;
    //接收撤回的消息
    private Message retractMsg;
    Handler handler = new Handler();
    //漫游
    HandlerThread mThread;
    private static final int REFRESH_CONVERSATION_LIST = 0x3000;
    private static final int DISMISS_REFRESH_HEADER = 0x3001;
    private static final int ROAM_COMPLETED = 0x3002;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.wechat_fragment, null);
        LinearLayout ll_follow = view.findViewById(R.id.ll_follow);
        ll_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(),MyFollowActivity.class);
                getContext().startActivity(intent);
            }
        });
        unbinder = ButterKnife.bind(this, view);
        JMessageClient.registerEventReceiver(this);
        list= JMessageClient.getConversationList();
        initView();
        return view;
    }

    private void initView() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updataData();
            }
        }, 2000);
        initRefresh();
        initData();
        onClickItem();
    }

    /*下拉刷新*/
    private void initRefresh() {
        mFragmentMainRf.setColorSchemeResources(
                R.color.oriange
                , R.color.color_shape_right
                , R.color.aurora_msg_receive_bubble_default_color
                , R.color.oriange);
        //开启一个刷新的线程
        mFragmentMainRf.post(new Runnable() {
            @Override
            public void run() {
                //开始
                mFragmentMainRf.setRefreshing(true);
            }
        });
        //监听刷新状态操作
        mFragmentMainRf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新数据
                        updataData();
                    }
                }, 1000);
            }
        });
    }
    private void updataData(){
        data.clear();
        adapter.clear();
        initDataBean();
    }

    @Override
    public void onResume() {
        updataData();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    /*接收消息*/
    public void onEvent(final MessageEvent event) {
        final Message msg = event.getMessage();
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (JMessageClient.getMyInfo().getUserName() == "1006" || JMessageClient.getMyInfo().getUserName().equals("1006")) {
                    final Message message1 = JMessageClient.createSingleTextMessage(((UserInfo)msg.getTargetInfo()).getUserName(), SharedPrefHelper.getInstance().getAppKey(), "[自动回复]你好，我是机器人");
                    JMessageClient.sendMessage(message1);
                }
                updataData();
            }
        });

    }

    /*接收撤回消息*/
    public void onEvent(MessageRetractEvent event) {
        retractMsg = event.getRetractedMessage();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updataData();
            }
        },300);
    }
    /**
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        Log.e("refreshOffline=====", ":" + conv);
        updataData();
    }
    /**
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEvent(ConversationRefreshEvent event) {
        final Conversation conv = event.getConversation();
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("refresh", "漫游："+conv);
                updataData();
            }
        });
    }

    /*监听item*/
    private void onClickItem() {
        adapter.setOnItemClickListener(new MessageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view != null) {
                    Intent intent = new Intent(getActivity(), ChatMsgActivity.class);
                    intent.putExtra("USERNAME", data.get(position).getUserName());
                    intent.putExtra("NAKENAME", data.get(position).getTitle());
                    intent.putExtra("MSGID", data.get(position).getMsgID());
                    intent.putExtra("AVATAR",data.get(position).getImg());
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                String[] strings = {"删除会话"};
                MyAlertDialog dialog = new MyAlertDialog(getActivity(), strings,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    JMessageClient.deleteSingleConversation(data.get(position).getUserName());
                                    data.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dialog.initDialog();
            }
        });
    }


    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFragmentMainRv.setLayoutManager(layoutManager);
        adapter = new MessageRecyclerAdapter(data, getActivity());
        //分割线
        mFragmentMainRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mFragmentMainRv.setAdapter(adapter);
        mFragmentMainHeader.attachTo(mFragmentMainRv);
    }

    private void initDataBean() {
        list= JMessageClient.getConversationList();
        if (list.size() <= 0) {
            mFragmentMainNone.setVisibility(View.VISIBLE);
            mFragmentMainRv.setVisibility(View.GONE);
        } else {
            mFragmentMainNone.setVisibility(View.GONE);
            mFragmentMainRv.setVisibility(View.VISIBLE);
            for (int i = 0; i < list.size(); i++) {
                bean = new MessageBean();
                try {
                    if (list.get(i).getLatestMessage().getContent().getContentType()== ContentType.prompt) {
                        bean.setContent(((PromptContent) (list.get(i).getLatestMessage()).getContent()).getPromptText());
                    }else {
                        bean.setContent(((TextContent) (list.get(i).getLatestMessage()).getContent()).getText());
                    }
                } catch (Exception e) {
                    bean.setContent("最近没有消息！");
                    Log.e("Exception:MessageFM", e.getMessage());
                }
                bean.setMsgID(list.get(i).getId());
                bean.setUserName(((UserInfo) list.get(i).getTargetInfo()).getUserName());
                bean.setTitle(list.get(i).getTitle());
                bean.setTime(list.get(i).getUnReadMsgCnt() + "");
                bean.setConversation(list.get(i));
                try {
                    bean.setImg(list.get(i).getAvatarFile().toURI() + "");
                } catch (Exception e) {
                }
                data.add(bean);
            }
        }
        mFragmentMainRf.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("finish");
                if("refresh".equals(msg)){
                    getActivity().finish();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_main_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_main_group:
                break;
        }
    }
}
