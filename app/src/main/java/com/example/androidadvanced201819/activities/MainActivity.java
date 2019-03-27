package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.ServiceUtility.LoginResponse;
import com.example.androidadvanced201819.ServiceUtility.LoginService;
import com.example.androidadvanced201819.ServiceUtility.UserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kx99i37oa2.execute-api.eu-west-2.amazonaws.com/academy/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final LoginService service = retrofit.create(LoginService.class);
        final ImageView image = findViewById(R.id.loginImage);

        final EditText usernameEditText = findViewById(R.id.username);

        final EditText passwordEditText = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<LoginResponse> repos = service.login(new UserRequest(usernameEditText.getText().toString(),passwordEditText.getText().toString()));
                repos.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(

                            Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body().getBody().equals("OK") && response.body().getStatusCode().equals("200")) {
                            Toast.makeText(MainActivity.this, "Login effettuato", Toast.LENGTH_LONG).show();
                            image.setImageResource(R.mipmap.ok);


                            Intent listIntent = new Intent(getApplicationContext(),ListaProfiliActivity.class);
                            startActivity(listIntent);

                        }else{
                            Toast.makeText(MainActivity.this, "Login non effettuato", Toast.LENGTH_LONG).show();
                            image.setImageResource(R.mipmap.ko);

                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
    }

}
