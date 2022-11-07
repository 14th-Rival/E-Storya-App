package com.innov.testchat.DataModels;

import android.graphics.Bitmap;

public class AvatarModels {
    private Bitmap avatar_image;

    public AvatarModels(Bitmap avatar_image) {
        this.avatar_image = avatar_image;
    }

    public Bitmap getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(Bitmap avatar_image) {
        this.avatar_image = avatar_image;
    }
}
