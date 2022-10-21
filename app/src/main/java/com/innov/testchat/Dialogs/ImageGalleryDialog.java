package com.innov.testchat.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.Adapters.AvatarRosterAdapter;
import com.innov.testchat.DataModels.AvatarModels;
import com.innov.testchat.DataModels.SelectedAvatar;
import com.innov.testchat.MainPages.MainActivity;
import com.innov.testchat.MainPages.SignupActivity;
import com.innov.testchat.R;
import com.innov.testchat.ScaleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class ImageGalleryDialog extends Dialog{

    private MainActivity activity;

    // List and Adapter
    private List<AvatarModels> mAvatarModels = new ArrayList<>();
    private AvatarRosterAdapter avatarRosterAdapter;

    // RecyclerView
    private RecyclerView recyclerViewAvatar;
    private RecyclerView.LayoutManager mLayoutManager;

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


    public ImageGalleryDialog(@NonNull Context context, MainActivity activity) {
        super(context);
        this.activity = activity;
        init(context);
    }

    public ImageGalleryDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ImageGalleryDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }


    private void init(Context mContext){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setContentView(R.layout.dialog_avatar_selection);


        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        this.setCancelable(true);

        // Recycler
        recyclerViewAvatar = this.findViewById(R.id.recycler_avatar);
        recyclerViewAvatar.setHasFixedSize(true);

        // Setup for RecyclerView
        avatarRosterAdapter = new AvatarRosterAdapter(getImageModels(), mContext);
        mLayoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewAvatar.setAdapter(avatarRosterAdapter);
        recyclerViewAvatar.setLayoutManager(mLayoutManager);
//        this.show();

        ScaleButton btn_change = this.findViewById(R.id.button_select);
        btn_change.setOnClickListener(v -> {
            activity.setImageProfile(avatarRosterAdapter.selectedAvatar.getSelected_avatar());
            dismiss();
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

}
