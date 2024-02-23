package ru.serujimir.mediationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;

import java.util.TimerTask;

public class LaunchActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
//                if (!sharedPreferences.getString("email", "null").equals("null") && !sharedPreferences.getString("password", "null").equals("null") && sharedPreferences.getBoolean("is_logged", false)) {
                if (sharedPreferences.getBoolean("is_logged", false)) {
                    intent = new Intent(LaunchActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(LaunchActivity.this, OnBoardingActivity.class);
                }

                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}