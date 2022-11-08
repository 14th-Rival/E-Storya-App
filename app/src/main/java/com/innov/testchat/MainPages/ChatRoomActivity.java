package com.innov.testchat.MainPages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.innov.testchat.Adapters.ChatAdapter;
import com.innov.testchat.ChatPilot;
import com.innov.testchat.DataModels.ChatUser;
import com.innov.testchat.ImageManager;
import com.innov.testchat.R;
import com.innov.testchat.ScaleImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatRoomActivity extends Activity implements View.OnClickListener {

    public RecyclerView mChatRecycler;
    public RecyclerView.LayoutManager mChatLayoutManager;
    public ChatAdapter mChatRoomAdapter;

    private ImageManager mImageManager;
    private ChatPilot mChatpilot;

    // Static String
    private String TAG = "ChatRoomActivity";
    private String mUserProfile = "userProfile";
    private String mUserName = "userName";
    private String mRoomName = "roomName";


    private TextView mRoomNameHeader;
    private EditText mSendMessage;
    private ScaleImageButton mSendBtn;


    /***
     * I'm going to create another class for the socket.io
     * This is temporary
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initImageManager();
        initData();
    }


    private synchronized void initView(){
        setContentView(R.layout.activity_chatroom);

        mRoomNameHeader =  findViewById(R.id.room_id);
        mSendMessage = findViewById(R.id.edit_chat_text);
        mSendBtn = findViewById(R.id.btn_send);

        initializeChatView();
    }


    private synchronized void initData(){

        Intent intent = getIntent();
        String userProfile = intent.getStringExtra(mUserProfile);
        String userName = intent.getStringExtra(mUserName);
        String roomName = intent.getStringExtra(mRoomName);

        Log.d(TAG, "====> UserName: "+userName);
        Log.d(TAG, "====> RoomName: "+roomName);

        mRoomNameHeader.setText("Welcome to "+roomName+" room");

        initializeThread(userProfile, userName, roomName);
    }

    private synchronized void initializeThread(String mUserProfile, String mUserName, String mRoomName) {

        if (mUserName.isEmpty() | mRoomName.isEmpty()){
            NullPointerException e = new NullPointerException();
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        else {
            if (mChatpilot == null){
                mChatpilot = new ChatPilot(
                        ChatRoomActivity.this,
                        ChatRoomActivity.this,
                        mImageManager,
                        mUserProfile,
                        mUserName,
                        mRoomName
                );

                Thread chatThread = new Thread(mChatpilot);
                chatThread.start();
            }
        }
    }

    private synchronized void initializeChatView(){
        mChatRecycler = findViewById(R.id.chat_recycler);
    }

    public static void startActivity(Activity mActivity, String encodedProfile, String userName, String roomName){
        Intent i = new Intent(mActivity, ChatRoomActivity.class);
        i.putExtra("userProfile", encodedProfile);
        i.putExtra("userName", userName);
        i.putExtra("roomName", roomName);
        mActivity.startActivity(i);
        mActivity.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:

                if (mChatpilot != null){

                    if (!isThereAMessage()){
                        return;
                    }

                    mChatpilot.sendChat(mSendMessage.getText().toString());
                    mSendMessage.setText("");
                }

                break;
        }
    }


    private boolean isThereAMessage(){
        String s = mSendMessage.getText().toString();

        if (s.isEmpty()){
            Log.d(TAG, "====> Empty Message");
            return false;
        }

        else {
            return  true;
        }
    }

    private void initImageManager(){
        if (mImageManager == null){
            mImageManager = new ImageManager(ChatRoomActivity.this);
        }
    }
}
