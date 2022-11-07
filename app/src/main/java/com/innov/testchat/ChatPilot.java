package com.innov.testchat;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
    private ImageManager mImageManager;

    // Local
//    private String URL = "http://10.146.11.124:4000";

    // Tethered Address
//    private String URL = R.string.server_url_tethered;
    private String TAG = "ChatPilot";
    private Socket mSocket;


    private ChatAdapter mChatAdapter;

    // Chat List
    private ArrayList<ChatUser> mChatuserList = new ArrayList<>();

    // Chat Data
    public String User_ID = "1";
    private String mUserProfile;
    private String mUserName;
    private String mRoomName;


    public ChatPilot(
            Context mContext,
            ChatRoomActivity mActivity,
            ImageManager mImageManager,
            String mUserProfile,
            String mUserName,
            String mRoomName
    ){
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mImageManager = mImageManager;
        this.mUserProfile = mUserProfile;
        this.mUserName = mUserName;
        this.mRoomName = mRoomName;

        initializeChatUI();
    }

    @Override
    public void run() {
        initializeConnection();
    }

    private void initializeConnection(){
        if (mSocket == null){
            try {
                mSocket = IO.socket(mContext.getResources().getString(R.string.server_url_tethered));
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


                registerUser();

                /***
                 * Initialize listeners
                 */
                onNewUserJoined();
                updateChatConnection();
                onUserLeftRoom();
            }
        }
    };

    private void updateChatConnection(){
        mSocket.on("updateChat", args -> {

            Object object = null;
            String userProfile, userName, messageContent;

            userProfile=userName=messageContent="";

            for (Object arg : args) {

                if (object == null) {
                    object = arg;

                    try {

                        JSONObject newObj = new JSONObject(object.toString());

                        userProfile = newObj.optString("userProfileImage");
                        userName = newObj.getString("userName");
                        messageContent = newObj.getString("messageContent");
                        String roomName = newObj.getString("roomName");

                        Log.d(TAG, "Chat details: " + "\n"+ userProfile + "\n" + userName + "\n" + messageContent + "\n" + roomName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            /***
             * Updates the Chat UI ChatRoomActivity
             */
            updateChatUI(null, userProfile, userName, messageContent);
        });
    }


    private void registerUser(){
        try {

            JSONObject initialData = new JSONObject();

            Log.d(TAG, "====> userProfile Image: "+ mUserProfile);

            initialData.put("userProfileImage", mUserProfile);
            initialData.put("userName", mUserName);
            initialData.put("roomName", mRoomName);
            Log.d(TAG, "====> Connected!");

            mSocket.emit("subscribe", initialData.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void onNewUserJoined(){
        mSocket.on("newUserToChatRoom", args -> {
            Log.d(TAG, args[0].toString()+" joined the room");
        });
    }

    private void onUserLeftRoom(){
        mSocket.on("userLeftChatRoom", args ->
                Log.d(TAG, args[0].toString()+" left the room"));
    }


    public void sendChat(String message){

        try {
            JSONObject sendData = new JSONObject();

            sendData.put("messageContent", message);
            sendData.put("roomName", mRoomName);

            mSocket.emit("newMessage", sendData);

            Log.d(TAG, "====> ChatPilot Send Message: "+message);
            updateChatUI(User_ID, mUserProfile, mUserName, message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initializeChatUI(){
        mActivity.runOnUiThread(() -> {

            mChatAdapter = new ChatAdapter(mActivity.getApplicationContext(), mImageManager, mChatuserList);
            mActivity.mChatLayoutManager = new LinearLayoutManager(
                    mActivity.getApplicationContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );

            mActivity.mChatRecycler.setLayoutManager(mActivity.mChatLayoutManager);
            mActivity.mChatRecycler.setAdapter(mChatAdapter);
        });
    }

    private void updateChatUI(@Nullable String SOCKETID, String userProfile, @Nullable String username, String message){

        mActivity.runOnUiThread(() -> {

            if (SOCKETID == null){
                mChatuserList.add(new ChatUser("", userProfile, username, message));
            }

            else {
                mChatuserList.add(new ChatUser(User_ID, this.mUserProfile, this.mUserName, message));
            }

            mChatAdapter.notifyItemInserted(mChatuserList.size());
            mActivity.mChatRecycler.scrollToPosition(mChatuserList.size() - 1);
        });
    }
}
