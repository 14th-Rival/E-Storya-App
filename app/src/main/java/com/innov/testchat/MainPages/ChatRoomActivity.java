package com.innov.testchat.MainPages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.innov.testchat.Adapters.ChatAdapter;
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
    private String temp_user = "enzo_dev";
    private String temp_room = "android";


    /***
     * I'm going to create another class for the socket.io
     * This is temporary
     */
    private Socket mSocket;
    private Gson mGson = new Gson();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        setContentView(R.layout.activity_chatroom);
        initializeChatView();
        initializeConnection();
    }

    private void initializeChatView(){

//        if (mChatUserList.isEmpty()){
            mChatUserList = new ArrayList<>();
//        }

        mChatRecycler = findViewById(R.id.chat_recycler);

        mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mChatRoomAdapter = new ChatAdapter(ChatRoomActivity.this, mChatUserList);

        mChatRecycler.setLayoutManager(mChatLayoutManager);
        mChatRecycler.setAdapter(mChatRoomAdapter);
    }


    private void initializeConnection(){
        try {
            connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "====> Failed to Connect!");
        }
    }


    private void connect() throws URISyntaxException {
        mSocket = IO.socket("http://192.168.34.223:4000");
//        mSocket.connect();

//        if (mSocket.isActive()){
////            mSocket.on(Socket.EVENT_CONNECT, onConnect());
//            Log.d(TAG, "putangina mo kumonek ka!");
//        }
        runChatRoom();
    }

    private void runChatRoom(){
        mSocket.connect();
        Log.d(TAG, mSocket.id());

        if (mSocket.connected()){
            mSocket.on(Socket.EVENT_CONNECT, onConnect());
        }
    }

    private Emitter.Listener onConnect(){

        try {
            JSONObject initialData = new JSONObject();

            initialData.put("userName", temp_user);
            initialData.put("roomName", temp_room);
            Log.d(TAG, "====> Connected!");


            mSocket.emit("subscribe", initialData);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }


    public static void startActivity(Activity mActivity){
        Intent i = new Intent(mActivity, ChatRoomActivity.class);
        mActivity.startActivity(i);
    }
}
