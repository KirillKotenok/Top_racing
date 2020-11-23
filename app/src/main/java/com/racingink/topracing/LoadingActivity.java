package com.racingink.topracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    private boolean isActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ImageView loading = findViewById(R.id.loading);
        Glide.with(this).load(R.drawable.loading).into(loading);
        load();
    }

    private void load() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isActive) {
                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 5000);
    }

    @Override
    protected void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        isActive = true;
        super.onResume();
    }
}