package com.innov.testchat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.Adapters.AvatarRosterAdapter;
import com.innov.testchat.DataModels.AvatarModels;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends Activity {

    // List and Adapter
    private List<AvatarModels> mAvatarModels = new ArrayList<>();
    private AvatarRosterAdapter avatarRosterAdapter;

    // RecyclerView
    private RecyclerView recyclerViewAvatar;
    private RecyclerView.LayoutManager mLayoutManager;

    // Dialog
    private Dialog dialogAvatarRoster;
    private Button button_select_avatar;

    private ImageButton imageButton_upload;
    private ImageView imageView_avatar;
    private Button button_register;

    private EditText editText_userName, editText_password, editText_passwordRepeat;
    private Animation scale_up, scale_down;

    int[] avatar_images ={
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
            R.drawable.image_6,
            R.drawable.image_7,
            R.drawable.image_8,
            R.drawable.image_9,
            R.drawable.image_10,
            R.drawable.image_11,
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
            R.drawable.image_16,
            R.drawable.image_17
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Open Dialog
        imageButton_upload = findViewById(R.id.imageButton_uploadAvatar);

        // EditText
        editText_userName = findViewById(R.id.editText_userName);
        editText_password = findViewById(R.id.editText_password);
        editText_passwordRepeat = findViewById(R.id.editText_passwordRepeat);

        imageView_avatar = findViewById(R.id.imageView_avatar);

        scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);


        imageButton_upload.setOnTouchListener((v, event) -> {
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    imageButton_upload.startAnimation(scale_up);



                    break;
                case MotionEvent.ACTION_UP:
                    imageButton_upload.startAnimation(scale_down);

                    showDialog();
                    break;

            }

            return true;
        });
    }

    private List<AvatarModels> getImageModels() {
        // Insert Images to List
        if (mAvatarModels.isEmpty()) {
            for (int i=0; i<avatar_images.length; i++){
                int avatarImage = avatar_images[i];
                AvatarModels avatarModels = new AvatarModels(avatarImage);
                mAvatarModels.add(avatarModels);
            }
        }
        return  mAvatarModels ;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showDialog(){
        if (null == dialogAvatarRoster) {
            dialogAvatarRoster = new Dialog(SignupActivity.this);
            dialogAvatarRoster.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogAvatarRoster.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogAvatarRoster.setContentView(R.layout.dialog_avatar_selection);

            // Controllers (Button)
            ImageButton imageButton_dismiss = dialogAvatarRoster.findViewById(R.id.imageButton_closeDialog);
            Button button_updateAvatar = dialogAvatarRoster.findViewById(R.id.button_select);

            //set listener
            imageButton_dismiss.setOnTouchListener((v, event) -> {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        imageButton_dismiss.startAnimation(scale_down);
                        dialogAvatarRoster.dismiss();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        imageButton_dismiss.startAnimation(scale_up);
                        break;
                }
                return true;
            });

            button_updateAvatar.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        button_updateAvatar.startAnimation(scale_down);
                        imageView_avatar.setImageResource(avatarRosterAdapter.selectedAvatar.getSelected_avatar());
                        dialogAvatarRoster.dismiss();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        button_updateAvatar.startAnimation(scale_up);

                        break;
                }
                return true;
            });

            // Recycler
            recyclerViewAvatar = dialogAvatarRoster.findViewById(R.id.recycler_avatar);
            recyclerViewAvatar.setHasFixedSize(true);

            // Setup for RecyclerView
            avatarRosterAdapter = new AvatarRosterAdapter(getImageModels(),SignupActivity.this);
            mLayoutManager = new GridLayoutManager(SignupActivity.this, 2, LinearLayoutManager.HORIZONTAL, false);

            recyclerViewAvatar.setAdapter(avatarRosterAdapter);
            recyclerViewAvatar.setLayoutManager(mLayoutManager);

            // Update Avatar

            // Dialog properties
            dialogAvatarRoster.setCancelable(false);


            // Match to Parent
            Window window = dialogAvatarRoster.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialogAvatarRoster.show();
    }
}
