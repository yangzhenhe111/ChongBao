package com.example.pet.chat;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pet.R;

import cn.jpush.im.android.api.model.UserInfo;

public class FriendsAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private EnumMessageType mMessageType;

    public FriendsAdapter(EnumMessageType messageType) {
        super(R.layout.item_main_message);
        this.mMessageType = messageType;
    }


    @Override
    protected void convert(BaseViewHolder helper, final T item) {
        final Button button = helper.getView(R.id.item_main_bt);
        final TextView time = helper.getView(R.id.item_main_time);
        final ImageView imageView = helper.getView(R.id.item_main_img);
        if (mMessageType != null) {
            switch (mMessageType) {
                case RECOMMEND:
                    break;
                case FRIENDS:
                    //好友列表
                    UserInfo data = (UserInfo) item;
                    if (helper.getAdapterPosition() == 0) {
                        LogUtils.e("nakeName:"+data.getNickname()+",userName:"+data.getUserName()+",id:"+data.getUserID());
                    }
                    String name = "";
                    if (!data.getNickname().isEmpty()) {
                        name = data.getNickname();
                    }else if (!data.getUserName().isEmpty()){
                        name = data.getUserName();
                    }else {
                        name =""+data.getUserID();
                    }
                    helper.setText(R.id.item_main_username, name)
                            .setText(R.id.item_main_content, data.getSignature())
                            .setText(R.id.item_main_time, TimeUtils.unix2Date("MM-dd HH:mm", data.getmTime()));
                    GlideUtil.loadUserHeadImg(mContext,data.getAvatar(),imageView);
                    break;
                case CERT:
                    break;
                case MESSAGE:
                    break;
                default:
                    break;
            }
        }
    }

    public enum EnumMessageType {
        FRIENDS,
        RECOMMEND,
        CERT,
        MESSAGE,
    }
}
