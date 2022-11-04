package com.innov.testchat.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.Adapters.ViewHolders.ReceivedMessage;
import com.innov.testchat.Adapters.ViewHolders.SentMessage;
import com.innov.testchat.DataModels.ChatUser;
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
    private ArrayList<ChatUser> mChatUserList = new ArrayList<>();

    public ChatAdapter(Context mContext, ArrayList<ChatUser> mChatUserList) {s
        this.mContext = mContext;
        this.mChatUserList = mChatUserList;
    }


    @Override
    public int getItemViewType(int position) {
        ChatUser mChatUser = mChatUserList.get(position);

        if (!mChatUser.getUser_id().isEmpty()){
            Log.d(TAG, "====> Main User Chats!");
            return VIEW_TYPE_MESSAGE_SENT;
        }

        else if (Objects.equals(mChatUser.getUser_id(), "")) {
            Log.d(TAG, "=====> Other user Chats!");
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }

        else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return mChatUserList.size();
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_one, parent, false);
            mSentMsg = new SentMessage(view);
            mSentMsg.setIsRecyclable(false);
            return mSentMsg;
        }

        else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_two, parent, false);
            mReceivedMsg = new ReceivedMessage(view);
            mReceivedMsg.setIsRecyclable(false);
            return mReceivedMsg;
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatUser mChatuser = mChatUserList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                mSentMsg.bind(mChatuser.getUser_name(), mChatuser.getUser_message());
                Log.d(TAG, "====> Sender Message: "+ mChatuser.getUser_message());
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                mReceivedMsg.bind(mChatuser.getUser_name(), mChatuser.getUser_message());
                Log.d(TAG, "====> Received Message: "+ mChatuser.getUser_message());
                break;
        }
    }

//    public static class ReceivedMessage extends RecyclerView.ViewHolder {
//
//        TextView mUsername, mContent;
//        public ReceivedMessage(@NonNull View itemView) {
//            super(itemView);
//
//            mUsername = itemView.findViewById(R.id.msg_userName2);
//            mContent = itemView.findViewById(R.id.msg_content2);
//        }
//
//        public void bind(String mUsername, String mContent){
//            this.mUsername.setText(mUsername);
//            this.mContent.setText(mContent);
//        }
//    }
//
//    public static class SentMessage extends RecyclerView.ViewHolder {
//
//        private TextView mUsernameText, mMsgContent;
//
//        public SentMessage(@NonNull View itemView) {
//            super(itemView);
//            mUsernameText = itemView.findViewById(R.id.msg_userName);
//            mMsgContent = itemView.findViewById(R.id.msg_content);
//        }
//
//        public void bind(String username, String message){
//            mUsernameText.setText(username);
//            mMsgContent.setText(message);
//        }
//    }
}
