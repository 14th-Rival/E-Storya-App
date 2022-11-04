package com.innov.testchat.Adapters.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.R;

public class SentMessage extends RecyclerView.ViewHolder {

    private TextView mUsernameText, mMsgContent;

    public SentMessage(@NonNull View itemView) {
        super(itemView);
        mUsernameText = itemView.findViewById(R.id.msg_userName);
        mMsgContent = itemView.findViewById(R.id.msg_content);
    }

    public void bind(String username, String message){
        mUsernameText.setText(username);
        mMsgContent.setText(message);
    }
}
