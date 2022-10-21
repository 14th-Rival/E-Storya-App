package com.innov.testchat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScaleImageButton extends androidx.appcompat.widget.AppCompatImageButton {

    public ScaleImageButton(@NonNull Context context) {
        super(context);
    }

    public ScaleImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setScale(true);
                break;
            case MotionEvent.ACTION_UP:
                setScale(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setScale(boolean action){
        animate().scaleX(action?0.8f:1.0f).scaleY(action?0.8f:1.0f).setDuration(50).start();
    }
}
