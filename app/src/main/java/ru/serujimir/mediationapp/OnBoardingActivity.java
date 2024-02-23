package ru.serujimir.mediationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity {

    Button btnOnBoardingLogin;
    TextView tvOnBoardingRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        init();
    }

    private void init() {
        btnOnBoardingLogin = findViewById(R.id.btnLoginLogin);
        btnOnBoardingLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tvOnBoardingRegistration = findViewById(R.id.tvOnBoardingRegistration);
        tvOnBoardingRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}