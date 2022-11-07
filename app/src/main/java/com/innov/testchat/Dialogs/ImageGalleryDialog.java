package com.innov.testchat.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import com.innov.testchat.ImageManager;
import com.innov.testchat.MainPages.MainActivity;
import com.innov.testchat.MainPages.SignupActivity;
import com.innov.testchat.R;
import com.innov.testchat.ScaleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class ImageGalleryDialog extends Dialog{

    private MainActivity activity;
    private ImageManager imageManager;

    // List and Adapter
    private List<AvatarModels> mAvatarModels = new ArrayList<>();
    private AvatarRosterAdapter avatarRosterAdapter;

    // RecyclerView
    private RecyclerView recyclerViewAvatar;
    private RecyclerView.LayoutManager mLayoutManager;

    public ImageGalleryDialog(@NonNull Context context, MainActivity activity, ImageManager imageManager) {
        super(context);
        this.activity = activity;
        this.imageManager = imageManager;
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

            int imageArray = imageManager.avatar_images.size();
            for (int i=0; i<imageArray; i++){
                Bitmap avatarImage = imageManager.avatar_images.get(i);
                AvatarModels avatarModels = new AvatarModels(avatarImage);
                mAvatarModels.add(avatarModels);
            }
        }
        return mAvatarModels ;
    }

}
