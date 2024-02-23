package ru.serujimir.mediationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.serujimir.mediationapp.Parsing.JsonParser;
import ru.serujimir.mediationapp.Parsing.Login;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLoginLogin, btnLoginProfile;
    TextView tvLoginRegister;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (!sharedPreferences.getString("email", "").equals("") && !sharedPreferences.getString("password", "").equals("")) {
            etLoginEmail.setText(sharedPreferences.getString("email", ""));
            etLoginPassword.setText(sharedPreferences.getString("password", ""));
        }

        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnLoginProfile = findViewById(R.id.btnLoginProfile);
        btnLoginProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });

        tvLoginRegister = findViewById(R.id.tvLoginRegister);
        tvLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login() {
        if (etLoginEmail.getText().toString().length() > 1 && etLoginEmail.getText().toString().contains("@")) {
            if (etLoginPassword.getText().length() > 1) {

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                JsonParser jsonParser = retrofit.create(JsonParser.class);

                Login login = new Login(etLoginEmail.getText().toString(), etLoginPassword.getText().toString());

                Call<Void> voidCall = jsonParser.getLogin(login);
                voidCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {

                            sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("email", etLoginEmail.getText().toString());
                            editor.putString("password", etLoginPassword.getText().toString());
                            editor.putBoolean("is_logged", true);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(LoginActivity.this, "Excellent! " + response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("TEST", t.getMessage());
                        Toast.makeText(LoginActivity.this, "It's bad bro ):\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void profile() {
        Toast.makeText(this, "Not this time bro ):", Toast.LENGTH_SHORT).show();
    }
}