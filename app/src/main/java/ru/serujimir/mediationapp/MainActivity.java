package ru.serujimir.mediationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.serujimir.mediationapp.Adapters.MainViewPagerAdapter;
import ru.serujimir.mediationapp.Parsing.JsonParser;
import ru.serujimir.mediationapp.Parsing.Login;
import ru.serujimir.mediationapp.Parsing.LoginData;

public class MainActivity extends AppCompatActivity {

    ImageButton ibMenu;
    ImageView ivMainProfilePhoto;
    CardView cvMainProfile;
    ViewPager2 vp2Main;
    BottomNavigationView bnvMain;
    TextView tvMainExit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        ivMainProfilePhoto = findViewById(R.id.ivMainProfilePhoto);
        ibMenu = findViewById(R.id.ibMenu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        tvMainExit = findViewById(R.id.tvMainExit);
        tvMainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putBoolean("is_logged", false);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cvMainProfile = findViewById(R.id.cvMainProfile);
        cvMainProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp2Main.setCurrentItem(3);
                bnvMain.setSelectedItemId(R.id.profile);
            }
        });

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        Login login = new Login(sharedPreferences.getString("email", "null"), sharedPreferences.getString("password", "null"));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonParser jsonParser = retrofit.create(JsonParser.class);
        Call<LoginData> loginDataCall = jsonParser.getUserInfo(login);

        Bundle mainBundle = new Bundle();
        loginDataCall.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.body() != null) {
                    LoginData loginData = response.body();
                    Glide.with(MainActivity.this).load(loginData.getAvatar()).into(ivMainProfilePhoto);

                    mainBundle.putString("id", loginData.getId());
                    mainBundle.putString("email", loginData.getEmail());
                    mainBundle.putString("nickName", loginData.getNickName());
                    mainBundle.putString("avatar", loginData.getAvatar());
                    mainBundle.putString("token", loginData.getToken());
                    finalStep(mainBundle);
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }

    public void finalStep(Bundle mainBundle) {
        Fragment homeFragment = new HomeFragment();
        homeFragment.setArguments(mainBundle);

        Fragment navFragment = new NavFragment();
        navFragment.setArguments(mainBundle);

        Fragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(mainBundle);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(homeFragment);
        fragmentArrayList.add(navFragment);
        fragmentArrayList.add(profileFragment);

        vp2Main = findViewById(R.id.vp2Main);
        vp2Main.setUserInputEnabled(false);

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this, fragmentArrayList);
        vp2Main.setAdapter(mainViewPagerAdapter);


        vp2Main.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        cvMainProfile.setVisibility(View.VISIBLE);
                        tvMainExit.setVisibility(View.GONE);
                        bnvMain.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        cvMainProfile.setVisibility(View.VISIBLE);
                        tvMainExit.setVisibility(View.GONE);
                        bnvMain.setSelectedItemId(R.id.nav);
                        break;
                    case 2:
                        cvMainProfile.setVisibility(View.GONE);
                        tvMainExit.setVisibility(View.VISIBLE);
                        bnvMain.setSelectedItemId(R.id.profile);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        bnvMain = findViewById(R.id.bnvMain);
        bnvMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            @SuppressLint("NonConstantResourceId")
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        vp2Main.setCurrentItem(0);
                        break;
                    case R.id.nav:
                        vp2Main.setCurrentItem(1);
                        break;
                    case R.id.profile:
                        vp2Main.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }
}