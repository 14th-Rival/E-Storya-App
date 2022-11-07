package com.innov.testchat.Adapters.ViewHolders;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.R;

public class ReceivedMessage extends RecyclerView.ViewHolder {

    private TextView mUsername, mContent;
    private ImageView mUserProfile;

    public ReceivedMessage(@NonNull View itemView) {
        super(itemView);

        mUserProfile = itemView.findViewById(R.id.chathead_profile);
        mUsername = itemView.findViewById(R.id.msg_userName2);
        mContent = itemView.findViewById(R.id.msg_content2);
    }

    public void bind(Bitmap bm, String mUsername, String mContent){
        this.mUserProfile.setImageBitmap(bm);
        this.mUsername.setText(mUsername);
        this.mContent.setText(mContent);
    }
}
