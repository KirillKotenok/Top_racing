package com.racingink.topracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TIME_KEY = "TIME";
    private Point point;
    private Intent intent;
    private TextView textView;
    private Button start_btn;
    private Button exit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        textView = findViewById(R.id.welcome);
        start_btn = findViewById(R.id.start_btn);
        exit_btn = findViewById(R.id.exit_btn);
        start_btn.setOnClickListener(v -> {
            setContentView(new GameView(this, point.x, point.y));
        });
        exit_btn.setOnClickListener(v -> {
            finish();
        });
    }

    public void startEndActivity(double game_time) {
        intent = new Intent(this, EndGameActivity.class);
        intent.putExtra(TIME_KEY, game_time);
        startActivity(intent);
        finish();
    }
}