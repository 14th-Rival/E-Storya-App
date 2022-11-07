package com.innov.testchat.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.media.Image;
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
import com.innov.testchat.ImageManager;
import com.innov.testchat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final String user_id = "1";
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    private String TAG = "CHAT_ADAPTER";
    private SentMessage mSentMsg;
    private ReceivedMessage mReceivedMsg;


    private Context mContext;
    private ImageManager imageManager;
    private ArrayList<ChatUser> mChatUserList = new ArrayList<>();

    public ChatAdapter(Context mContext, ImageManager imageManager, ArrayList<ChatUser> mChatUserList) {
        this.mContext = mContext;
        this.imageManager = imageManager;
        this.mChatUserList = mChatUserList;
    }


    @Override
    public int getItemViewType(int position) {
        switch (mChatUserList.get(position).getUser_id()){
            case user_id:
                return VIEW_TYPE_MESSAGE_SENT;
            case "":
                return VIEW_TYPE_MESSAGE_RECEIVED;
            default:
                return -1;
        }

//        ChatUser mChatUser = mChatUserList.get(position);
//
//        if (!user_id.isEmpty()){
//            Log.d(TAG, "====> Main User Chats!");
//            return VIEW_TYPE_MESSAGE_SENT;
//        }
////
//        else {
//            Log.d(TAG, "=====> Other user Chats!");
//            return VIEW_TYPE_MESSAGE_RECEIVED;
//        }
//        return position;
    }

    @Override
    public long getItemId(int position) {
//        switch (mChatUserList.get(position).getUser_id()){
//            case user_id:
//                return VIEW_TYPE_MESSAGE_SENT;
//            case "":
//                return VIEW_TYPE_MESSAGE_RECEIVED;
//            default:
//                return -1;
//        }
//        ChatUser mChatUser = mChatUserList.get(position);
//
//        if (Objects.equals(mChatUser.getUser_id(), user_id)){
//            Log.d(TAG, "====> Main User Chats!");
//            return VIEW_TYPE_MESSAGE_SENT;
//        }
//
//        else {
//            Log.d(TAG, "=====> Other user Chats!");
//            return VIEW_TYPE_MESSAGE_RECEIVED;
//        }
        return position;
    }

    @Override
    public int getItemCount() {
        return mChatUserList.size();
    }

//    @Override
//    public void setHasStableIds(boolean hasStableIds) {
//        super.setHasStableIds(hasStableIds);
//    }

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


//        if (viewType == VIEW_TYPE_MESSAGE_SENT){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_one, parent, false);
//            mSentMsg = new SentMessage(view);
//            mSentMsg.setIsRecyclable(false);
//            return mSentMsg;
//        }

//        else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardview_two, parent, false);
//            mReceivedMsg = new ReceivedMessage(view);
//            mReceivedMsg.setIsRecyclable(false);
//            return mReceivedMsg;
//        }
//
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatUser mChatuser = mChatUserList.get(position);

        switch (mChatUserList.get(position).getUser_id()){

            case user_id:

                mSentMsg.bind(mChatuser.getUser_name(), mChatuser.getUser_message());
                Log.d(TAG, "====> Sender Message: "+ mChatuser.getUser_message());
                break;


            case "":

                Bitmap bm;

                if (Objects.equals(mChatuser.getUser_image(), "") | mChatuser.getUser_image().isEmpty()){
                    bm = BitmapFactory.decodeResource(holder.itemView.getContext().getResources(), R.drawable.image_17);
                }

                else {
                    bm = imageManager.imageDecoding(mChatuser.getUser_image());
                }

                mReceivedMsg.bind(
                        bm,
                        mChatuser.getUser_name(),
                        mChatuser.getUser_message()
                );


                Log.d(TAG, "====> Received Message: "+ mChatuser.getUser_message());
                break;
        }
    }
}
