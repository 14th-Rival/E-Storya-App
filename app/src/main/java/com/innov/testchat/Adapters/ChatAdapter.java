package com.innov.testchat.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.DataModels.ChatUser;
import com.innov.testchat.ImageManager;
import com.innov.testchat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private String TAG = "CHAT_ADAPTER";
    private SentMessage mSentMsg;
    private ReceivedMessage mReceivedMsg;
    private Context mContext;
    private ImageManager imageManager;
    private List<ChatUser> mChatUserList = new ArrayList<>();

    public ChatAdapter(Context mContext, List<ChatUser> mChatUserList) {
        this.mContext = mContext;
        this.imageManager = new ImageManager(mContext);
        this.mChatUserList = mChatUserList;
    }


    @Override
    public int getItemViewType(int position) {
        switch (mChatUserList.get(position).getMessageType()){
            case "1":
                return VIEW_TYPE_MESSAGE_SENT;
            case "2":
                return VIEW_TYPE_MESSAGE_RECEIVED;
        }

        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        switch (mChatUserList.get(position).getMessageType()){
            case "1":
                return VIEW_TYPE_MESSAGE_SENT;
            case "2":
                return VIEW_TYPE_MESSAGE_RECEIVED;
        }
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mChatUserList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType){
            case VIEW_TYPE_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_one, parent, false);
                mSentMsg = new SentMessage(view);
            return mSentMsg;

            case VIEW_TYPE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_two, parent, false);
                mReceivedMsg = new ReceivedMessage(view);
            return mReceivedMsg;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatUser mChatuser = mChatUserList.get(position);


        if (Objects.equals(mChatuser.getMessageType(), "1")){
            mSentMsg = (SentMessage) holder;
            mSentMsg.mUsernameText.setText(mChatuser.getUser_name());
            mSentMsg.mMsgContent.setText(mChatuser.getUser_message());
        }

        else if (Objects.equals(mChatuser.getMessageType(), "2")) {

            mReceivedMsg = (ReceivedMessage) holder;

            Bitmap bm;

            if (Objects.equals(mChatuser.getUser_image(), "") | mChatuser.getUser_image().isEmpty()){
                bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.image_17);
            }

            else {
                bm = imageManager.imageDecoding(mChatuser.getUser_image());
            }

            mReceivedMsg.mUserProfile.setImageBitmap(bm);
            mReceivedMsg.mUsername.setText(mChatuser.getUser_name());
            mReceivedMsg.mContent.setText(mChatuser.getUser_message());
        }
    }

    public static class SentMessage extends RecyclerView.ViewHolder {

        public TextView mUsernameText, mMsgContent;

        public SentMessage(@NonNull View itemView) {
            super(itemView);

            mUsernameText = itemView.findViewById(R.id.msg_userName);
            mMsgContent = itemView.findViewById(R.id.msg_content);
        }
    }


    public static class ReceivedMessage extends RecyclerView.ViewHolder {
        public TextView mUsername, mContent;
        public ImageView mUserProfile;

        public ReceivedMessage(@NonNull View itemView) {
            super(itemView);

            mUserProfile = itemView.findViewById(R.id.chathead_profile);
            mUsername = itemView.findViewById(R.id.msg_userName2);
            mContent = itemView.findViewById(R.id.msg_content2);
        }
    }
}
