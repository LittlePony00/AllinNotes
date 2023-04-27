package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Logo_Activity extends Activity {
    private TextView textView;
    private ImageView imageView;
    private Animation animIm, animTv;
    private final String s = "AllinNotes";
    int i = 0;

    MainActivity mainActivity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);


        animTv = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_textview_anim);
        animIm = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_image_anim);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        imageView.startAnimation(animIm);

        textView.setText("");

//        new CountDownTimer(s.length() * 100, 100) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                if (i < s.length()) {
//                    textView.setText(textView.getText().toString() + s.charAt(i));
//                    i++;
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                startMainActivity();
//            }
//        }.start();

        setTextView();
        startMainActivity();

    }

    private void startMainActivity() {
        new Thread(() -> {
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startActivity(new Intent(Logo_Activity.this, MainActivity.class));
            finish();
        }).start();
    }

    private void setTextView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("A");
            }
        }, 600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("l");
            }
        }, 800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("l");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("i");
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("n");
            }
        }, 1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("N");
            }
        }, 1200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("o");
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("t");
            }
        }, 1400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("e");
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("s");
            }
        }, 1600);
    }

}
