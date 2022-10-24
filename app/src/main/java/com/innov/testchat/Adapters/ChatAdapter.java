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
import com.innov.testchat.ChatPilot;
import com.innov.testchat.DataModels.ChatUser;
import com.innov.testchat.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {


    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    private String TAG = "CHAT_ADAPTER";
    private SentMessage mSentMsg;
    private ReceivedMessage mReceivedMsg;


    private Context mContext;
    private List<ChatUser> mChatUserList = new ArrayList<>();
    private ChatPilot mChatPilot;

    public ChatAdapter(Context mContext, List<ChatUser> mChatUserList, ChatPilot mChatPilot) {
        this.mContext = mContext;
        this.mChatUserList = mChatUserList;
        this.mChatPilot = mChatPilot;
    }


    @Override
    public int getItemViewType(int position) {
        ChatUser mChatUser = mChatUserList.get(position);

        if (!mChatUser.getUser_id().isEmpty()){
            Log.d(TAG, "====> Main User Chats!");

            return VIEW_TYPE_MESSAGE_SENT;
        }

        else {
            Log.d(TAG, "=====> Other user Chats!");
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return mChatUserList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;


        if (viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_one, parent, false);
            mSentMsg = new SentMessage(view);
            return mSentMsg;

        }

        else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_two, parent, false);
            mReceivedMsg = new ReceivedMessage(view);
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
//                notifyDataSetChanged();
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                mReceivedMsg.bind(mChatuser.getUser_name(), mChatuser.getUser_message());
//                notifyDataSetChanged();
                break;
        }
    }


}
