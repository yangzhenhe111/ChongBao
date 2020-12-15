package com.example.pet.chat;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.pet.R;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.BaseMessageViewHolder;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.messages.MsgListAdapter.DefaultMessageViewHolder;



public class MyViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {
    protected TextView mMsgTv;
    protected TextView mDateTv;
    protected TextView mDisplayNameTv;
    protected cn.jiguang.imui.view.CircleImageView mAvatarIv;
    protected ImageButton mResendIb;
    protected ProgressBar mSendingPb;
    protected boolean mIsSender;

    public MyViewHolder(View itemView, boolean isSender) {
        super(itemView);
        this.mIsSender = isSender;
        this.mMsgTv = (TextView)itemView.findViewById(R.id.aurora_tv_msgitem_message);
        this.mDateTv = (TextView)itemView.findViewById(R.id.aurora_tv_msgitem_date);
        this.mAvatarIv = (cn.jiguang.imui.view.CircleImageView)itemView.findViewById(R.id.aurora_iv_msgitem_avatar);
        this.mResendIb = (ImageButton)itemView.findViewById(R.id.aurora_ib_msgitem_resend);
        this.mSendingPb = (ProgressBar)itemView.findViewById(R.id.aurora_pb_msgitem_sending);
    }

    @Override
    @SuppressLint("WrongConstant")
    public void onBind(final MESSAGE message) {
        this.mMsgTv.setText(message.getText());
        if(message.getTimeString() != null) {
            this.mDateTv.setText(message.getTimeString());
        }

        boolean isAvatarExists = message.getFromUser().getAvatarFilePath() != null && !message.getFromUser().getAvatarFilePath().isEmpty();
        if(isAvatarExists && this.mImageLoader != null) {
            this.mImageLoader.loadAvatarImage(this.mAvatarIv, message.getFromUser().getAvatarFilePath());
        } else if(this.mImageLoader == null) {
            this.mAvatarIv.setVisibility(8);
        }

        if(!this.mIsSender) {
            if(this.mDisplayNameTv.getVisibility() == 0) {
                this.mDisplayNameTv.setText(message.getFromUser().getDisplayName());
            }
        } else {
        }

        this.mMsgTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(com.example.pet.chat.MyViewHolder.this.mMsgClickListener != null) {
                    com.example.pet.chat.MyViewHolder.this.mMsgClickListener.onMessageClick(message);
                }

            }
        });

        this.mAvatarIv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(com.example.pet.chat.MyViewHolder.this.mAvatarClickListener != null) {
                    com.example.pet.chat.MyViewHolder.this.mAvatarClickListener.onAvatarClick(message);
                }

            }
        });
    }

    @Override
    @SuppressLint("WrongConstant")
    public void applyStyle(MessageListStyle style) {
        this.mMsgTv.setMaxWidth((int)((float)style.getWindowWidth() * style.getBubbleMaxWidth()));
        if(this.mIsSender) {
            this.mMsgTv.setBackground(style.getSendBubbleDrawable());
            this.mMsgTv.setTextColor(style.getSendBubbleTextColor());
            this.mMsgTv.setTextSize(style.getSendBubbleTextSize());
            this.mMsgTv.setPadding(style.getSendBubblePaddingLeft(), style.getSendBubblePaddingTop(), style.getSendBubblePaddingRight(), style.getSendBubblePaddingBottom());
            if(style.getSendingProgressDrawable() != null) {
                this.mSendingPb.setProgressDrawable(style.getSendingProgressDrawable());
            }

            if(style.getSendingIndeterminateDrawable() != null) {
                this.mSendingPb.setIndeterminateDrawable(style.getSendingIndeterminateDrawable());
            }
        } else {
            this.mMsgTv.setBackground(style.getReceiveBubbleDrawable());
            this.mMsgTv.setTextColor(style.getReceiveBubbleTextColor());
            this.mMsgTv.setTextSize(style.getReceiveBubbleTextSize());
            this.mMsgTv.setPadding(style.getReceiveBubblePaddingLeft(), style.getReceiveBubblePaddingTop(), style.getReceiveBubblePaddingRight(), style.getReceiveBubblePaddingBottom());
        }

        this.mDateTv.setTextSize(style.getDateTextSize());
        this.mDateTv.setTextColor(style.getDateTextColor());
        ViewGroup.LayoutParams layoutParams = this.mAvatarIv.getLayoutParams();
        layoutParams.width = style.getAvatarWidth();
        layoutParams.height = style.getAvatarHeight();
        this.mAvatarIv.setLayoutParams(layoutParams);
    }

    public TextView getMsgTextView() {
        return this.mMsgTv;
    }

    public cn.jiguang.imui.view.CircleImageView getAvatar() {
        return this.mAvatarIv;
    }
}