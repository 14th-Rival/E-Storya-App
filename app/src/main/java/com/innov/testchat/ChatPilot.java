package com.innov.testchat;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.Adapters.ChatAdapter;
import com.innov.testchat.DataModels.ChatUser;
import com.innov.testchat.MainPages.ChatRoomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.socket.client.AckWithTimeout;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatPilot implements Runnable{

    private Context mContext;
    private ChatRoomActivity mActivity;

    private String URL = "http://10.146.11.124:4000";
    private String TAG = "ChatPilot";
    private Socket mSocket;

    // Chat List
    private List<ChatUser> mChatuserList = new ArrayList<>();

    // Chat Data
    public String SOCKET_ID = "";
    private String mUserName;
    private String mRoomName;

    public ChatPilot(
            Context mContext,
            ChatRoomActivity mActivity,
            String mUserName,
            String mRoomName
    ){
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mUserName = mUserName;
        this.mRoomName = mRoomName;
    }

    @Override
    public void run() {
        initializeConnection();
    }

    private void initializeConnection(){
        if (mSocket == null){
            try {
                mSocket = IO.socket(URL);
                connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    private void connect(){
        mSocket.connect();
        mSocket.on(Socket.EVENT_CONNECT, runConnection);
    }

    private Emitter.Listener runConnection = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (mSocket.connected()){
                Log.d(TAG, "=====> Connected to Server!");

                Log.d(TAG, "=====> Socket Id: "+mSocket.id());
                SOCKET_ID = mSocket.id();

                registerUser();
            }

        }
    };


    private void registerUser(){
        try {

            JSONObject initialData = new JSONObject();

            initialData.put("userName", mUserName);
            initialData.put("roomName", mRoomName);
            Log.d(TAG, "====> Connected!");

            mSocket.emit("subscribe", initialData.toString());
            mSocket.on("newUserToChatRoom", args -> Log.d(TAG, args[0].toString()+" joined the room"));


        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void sendChat(String message){

        try {
            JSONObject sendData = new JSONObject();

            sendData.put("messageContent", message);
            sendData.put("roomName", mRoomName);

            mSocket.emit("newMessage", sendData);


            ChatUser chatSend = new ChatUser(SOCKET_ID, mUserName, message);
            mChatuserList.add(chatSend);

            if (Objects.equals(chatSend.getUser_id(), mSocket.id())){
                runChatUI();
            }

            mSocket.on("updateChat", args -> {

                Object object = null;
                for (Object arg : args) {

                    if (object == null) {
                        object = arg;

                        try {

                            JSONObject newObj = new JSONObject(object.toString());

                            String userName = newObj.getString("userName");
                            String messageContent = newObj.getString("messageContent");
                            String roomName = newObj.getString("roomName");

                            Log.d(TAG, "Chat details: " + "\n" + userName + "\n" + messageContent + "\n" + roomName);

                            ChatUser chatReceived = new ChatUser("",userName, messageContent);
                            mChatuserList.add(chatReceived);

                            runChatUI();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void runChatUI(){
        mActivity.runOnUiThread(() -> {


            mActivity.mChatRoomAdapter = new ChatAdapter(
                    mContext,
                    mChatuserList,
                    ChatPilot.this
            );

            mActivity.mChatLayoutManager = new LinearLayoutManager(
                    mActivity.getApplicationContext(),
                    RecyclerView.VERTICAL, false);


            mActivity.mChatRecycler.setLayoutManager(mActivity.mChatLayoutManager);

            mActivity.mChatRoomAdapter.notifyDataSetChanged();
            mActivity.mChatRecycler.setAdapter(mActivity.mChatRoomAdapter);
        });
    }



}
