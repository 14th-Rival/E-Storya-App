package com.innov.testchat.MainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.innov.testchat.Adapters.AvatarRosterAdapter;
import com.innov.testchat.DataModels.SelectedAvatar;
import com.innov.testchat.Dialogs.ImageGalleryDialog;
import com.innov.testchat.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivityLOG";
    private ImageGalleryDialog imageGalleryDialog;
    private ImageView img_profile_ava;
    private SelectedAvatar mSelectedAva;

    private TextView mSet_error_name, mSet_error_room;
    private TextInputEditText mUsername, mRoomname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews(){
        setContentView(R.layout.activity_main);


        img_profile_ava = findViewById(R.id.imgview_ava);


        /***
         * Username and Roomname holder
         */
        mUsername = findViewById(R.id.tv_input_name);
        mRoomname = findViewById(R.id.tv_input_room);



        /***
         *  Set Error Messages
         */
        mSet_error_name = findViewById(R.id.set_error_name);
        mSet_error_room = findViewById(R.id.set_error_room);


        /**
         * Set Text Listener
         *
         * */
        mUsername.addTextChangedListener(mEditUserName);
        mRoomname.addTextChangedListener(mEditRoomName);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:


                if (!isValidUser() | !isValidRoom()){
                    return;
                }

                String userName = mUsername.getText().toString();
                String roomName = mRoomname.getText().toString();
                ChatRoomActivity.startActivity(MainActivity.this, userName, roomName);
                Log.d(TAG, "====> Logged In!");

                break;

            case R.id.btn_change_ava:
                imageGalleryDialog = new ImageGalleryDialog(MainActivity.this, MainActivity.this);
                imageGalleryDialog.show();
                break;

            case R.id.imageButton_closeDialog:
                imageGalleryDialog.dismiss();
                break;
        }
    }

    public void setImageProfile(int ava){
        img_profile_ava.setImageResource(ava);
    }

    private TextWatcher mEditUserName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mSet_error_name.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO
        }
    };

    private TextWatcher mEditRoomName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mSet_error_room.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO
        }
    };



    private boolean isValidUser(){
        String s = mUsername.getText().toString();

        if (s.isEmpty()){
            mSet_error_name.setVisibility(View.VISIBLE);
            mSet_error_name.setText("Field is empty");

            return false;
        }

        if (s.length() <= 4){
            mSet_error_name.setVisibility(View.VISIBLE);
            mSet_error_name.setText("Username length must be atleast 5 characters long!");

            return false;
        }

        else{
            mSet_error_name.setVisibility(View.GONE);
            mSet_error_name.setText(null);
            return true;
        }
    }

    private boolean isValidRoom(){
        String s = mRoomname.getText().toString();

        if (s.isEmpty()){
            mSet_error_room.setVisibility(View.VISIBLE);
            mSet_error_room.setText("Field is empty");

            return false;
        }

        if (s.length() <= 3){
            mSet_error_room.setVisibility(View.VISIBLE);
            mSet_error_room.setText("Roomname length must be atleast 4 characters long!");

            return false;
        }

        else {
            mSet_error_room.setVisibility(View.GONE);
            mSet_error_room.setText(null);

            return true;
        }
    }

}