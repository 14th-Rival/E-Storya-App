package com.innov.testchat.MainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.innov.testchat.Adapters.AvatarRosterAdapter;
import com.innov.testchat.DataModels.SelectedAvatar;
import com.innov.testchat.Dialogs.ImageGalleryDialog;
import com.innov.testchat.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivityLOG";
    private ImageGalleryDialog imageGalleryDialog;
    private ImageView img_profile_ava;
    private SelectedAvatar mSelectedAva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews(){
        setContentView(R.layout.activity_main);
        img_profile_ava = findViewById(R.id.imgview_ava);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                ChatRoomActivity.startActivity(MainActivity.this);
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

}