package com.innov.testchat.MainPages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.innov.testchat.Adapters.ChatAdapter;
import com.innov.testchat.ChatPilot;
import com.innov.testchat.DataModels.ChatUser;
import com.innov.testchat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatRoomActivity extends Activity {

    private RecyclerView mChatRecycler;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private ChatAdapter mChatRoomAdapter;
    private List<ChatUser> mChatUserList;


    // Static String
    private String TAG = "ChatRoomTAG:";
    private String temp_user = "enzo_dev2";
    private String temp_room = "android";


    /***
     * I'm going to create another class for the socket.io
     * This is temporary
     */



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        setContentView(R.layout.activity_chatroom);
        initializeChatView();




        Thread tite = new Thread(new ChatPilot());
        tite.start();
//        initializeConnection();
    }

    private synchronized void initializeChatView(){

//        if (mChatUserList.isEmpty()){
            mChatUserList = new ArrayList<>();
//        }

        mChatRecycler = findViewById(R.id.chat_recycler);

        mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mChatRoomAdapter = new ChatAdapter(ChatRoomActivity.this, mChatUserList);

        mChatRecycler.setLayoutManager(mChatLayoutManager);
        mChatRecycler.setAdapter(mChatRoomAdapter);
    }

    public static void startActivity(Activity mActivity){
        Intent i = new Intent(mActivity, ChatRoomActivity.class);
        mActivity.startActivity(i);
    }
}
