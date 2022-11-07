package com.innov.testchat.DataModels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SelectedAvatar {
    private Bitmap selected_avatar;

    public SelectedAvatar(Bitmap selected_avatar) {
        this.selected_avatar = selected_avatar;
    }

    public Bitmap getSelected_avatar() {
        return selected_avatar;
    }

    public void setSelected_avatar(Bitmap selected_avatar) {
        this.selected_avatar = selected_avatar;
    }
}
