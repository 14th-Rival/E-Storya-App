package com.innov.testchat.DataModels;

public class ChatUser {
    private String user_name;
    private String user_message;
    private int user_image;

    public ChatUser(String user_name, String user_message, int user_image) {
        this.user_name = user_name;
        this.user_message = user_message;
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_message() {
        return user_message;
    }

    public int getUser_image() {
        return user_image;
    }
}