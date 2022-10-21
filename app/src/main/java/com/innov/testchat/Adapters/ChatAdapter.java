package com.innov.testchat.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.DataModels.ChatUser;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context mContext;
    private List<ChatUser> mChatUserList = new ArrayList<>();


    public ChatAdapter(Context mContext, List<ChatUser> mChatUserList) {
        this.mContext = mContext;
        this.mChatUserList = mChatUserList;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {

    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
