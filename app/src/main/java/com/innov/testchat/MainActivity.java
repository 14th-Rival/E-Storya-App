package com.innov.testchat;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_login, button_signup;
    private Animation scale_up, scale_down;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login = findViewById(R.id.button_login);
        button_signup = findViewById(R.id.button_register);

        scale_up = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_down);

        button_signup.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    button_signup.startAnimation(scale_up);
                    break;

                case MotionEvent.ACTION_UP:
                    button_signup.startAnimation(scale_down);

                    Intent i = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(i);

                    break;
            }
            return true;
        });


    }
}