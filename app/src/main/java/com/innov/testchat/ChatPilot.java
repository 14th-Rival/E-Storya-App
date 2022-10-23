package com.innov.testchat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.AckWithTimeout;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatPilot implements Runnable{

    private String URL = "http://192.168.100.89:4000";
    private String TAG = "CHATPILOT!";
    private Socket mSocket;


    // testing
    private String temp_user = "enzo_dev1";
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

            mSocket.on("newUserToChatRoom", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, args[0].toString()+" joined the room");



//                    testChat();
                }
            });

        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void testChat(){

        try {
            JSONObject sendData = new JSONObject();

            sendData.put("messageContent", "Hello!");
            sendData.put("roomName", temp_room);


            mSocket.emit("newMessage", sendData.toString());

            mSocket.on("updateChat", args -> {
                Log.d(TAG, "====> args length: "+args.length);
                Log.d(TAG, "Chat details"+args[0]);

                Object object = null;
                for (Object arg : args) {


                    if (object == null) {
                        object = arg;

                        Log.d(TAG, "====> Chat!: ");


                        try {

                            JSONObject newObj = new JSONObject(object.toString());

                            String userName = newObj.getString("userName");
                            String messageContent = newObj.getString("messageContent");
                            String roomName = newObj.getString("roomName");

                            Log.d(TAG, "Chat details: " + "\n" + userName + "\n" + messageContent + "\n" + roomName);

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
}
