package com.innov.testchat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageManager {

    private Context mContext;

    public ImageManager(Context mContext) {
        this.mContext = mContext;
        addImagesToList();
    }

    public List<Bitmap> avatar_images = new ArrayList<Bitmap>();

    private void addImagesToList(){
        avatar_images.add(getImage(mContext, R.drawable.image_1));
        avatar_images.add(getImage(mContext, R.drawable.image_2));
        avatar_images.add(getImage(mContext, R.drawable.image_3));
        avatar_images.add(getImage(mContext, R.drawable.image_4));
        avatar_images.add(getImage(mContext, R.drawable.image_5));
        avatar_images.add(getImage(mContext, R.drawable.image_6));
        avatar_images.add(getImage(mContext, R.drawable.image_7));
        avatar_images.add(getImage(mContext, R.drawable.image_8));
        avatar_images.add(getImage(mContext, R.drawable.image_9));
        avatar_images.add(getImage(mContext, R.drawable.image_10));
        avatar_images.add(getImage(mContext, R.drawable.image_11));
        avatar_images.add(getImage(mContext, R.drawable.image_12));
        avatar_images.add(getImage(mContext, R.drawable.image_13));
        avatar_images.add(getImage(mContext, R.drawable.image_14));
        avatar_images.add(getImage(mContext, R.drawable.image_15));
        avatar_images.add(getImage(mContext, R.drawable.image_16));
        avatar_images.add(getImage(mContext, R.drawable.image_17));
        avatar_images.add(getImage(mContext, R.drawable.gds));
    }

    private Bitmap getImage(Context getContext, int id){
        return BitmapFactory.decodeResource(getContext.getResources(), id);
    }

    private String mEncodedImage;

    public String imageEncoding(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return mEncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public Bitmap imageDecoding(String bm) {
        byte[] decodedString = Base64.decode(bm, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
