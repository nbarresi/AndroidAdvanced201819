package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidadvanced201819.activities.profile.MainActivity;
import com.example.androidadvanced201819.dataaccess.DataAccessUtils;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.interfaces.RestService;
import com.example.androidadvanced201819.model.LoginResponse;
import com.example.androidadvanced201819.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (DataAccessUtils.getOnSharedPreferences(getApplicationContext())) {
            Intent toMainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(toMainActivity);
        }
    }

    public void remindMe(View view) {
        CheckBox remind = (CheckBox) view;
        if (remind.isChecked()) {
            DataAccessUtils.writeOnSharedPreferences(true, this);
        } else {
            DataAccessUtils.writeOnSharedPreferences(false, this);
        }
    }

    public void access(View view) {
        final EditText textUsername = findViewById(R.id.username);
        final EditText textPassword = findViewById(R.id.password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kx99i37oa2.execute-api.eu-west-2.amazonaws.com/").addConverterFactory(GsonConverterFactory.create())
                .build();

        RestService service = retrofit.create(RestService.class);
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();

        Call<LoginResponse> user = service.login(new User(username, password));

        user.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(
                    Call<LoginResponse> call, Response<LoginResponse> response) {
                ImageView defualtImg = findViewById(R.id.defualtImg);
                ImageView successImg = findViewById(R.id.successImg);
                ImageView errorImg = findViewById(R.id.errorImg);
                TextView errorLogin = findViewById(R.id.errorLogin);

                if (response.body().getStatusCode() == 200) {
                    defualtImg.setVisibility(View.GONE);
                    errorImg.setVisibility(View.GONE);
                    successImg.setVisibility(View.VISIBLE);
                    errorLogin.setVisibility(View.GONE);
                    Intent toMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(toMainActivity);
                } else {
                    defualtImg.setVisibility(View.GONE);
                    successImg.setVisibility(View.GONE);
                    errorImg.setVisibility(View.VISIBLE);
                    errorLogin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}

