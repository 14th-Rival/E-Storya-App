package com.innov.testchat;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatPilot implements Runnable{

    private String URL = "http://192.168.100.89:4000";
    private String TAG = "CHATPILOT!";
    private Socket mSocket;


    // testing
    private String temp_user = "enzo_dev2";
    private String temp_room = "android";

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
                registerUser();
            }

        }
    };


    private void registerUser(){

        try {
            JSONObject initialData = new JSONObject();

            initialData.put("userName", temp_user);
            initialData.put("roomName", temp_room);
            Log.d(TAG, "====> Connected!");


            mSocket.emit("subscribe", initialData.toString());

        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
