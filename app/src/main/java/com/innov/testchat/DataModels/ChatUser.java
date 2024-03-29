package com.innov.testchat.DataModels;

import androidx.annotation.Nullable;

public class ChatUser {

    private String user_id;
    private String user_image;
    private String user_name;
    private String user_message;
    private String messageType;

    public ChatUser(String user_id, String user_image, String user_name, String user_message, @Nullable String messageType) {
        this.user_id = user_id;
        this.user_image = user_image;
        this.user_name = user_name;
        this.user_message = user_message;
        this.messageType = messageType;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_message() {
        return user_message;
    }

    public String getMessageType() {
        return messageType;
    }
}
