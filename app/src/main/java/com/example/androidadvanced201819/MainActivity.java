package com.example.androidadvanced201819;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

        final EditText usernameEditText = findViewById(R.id.editTextUser);

        final EditText passwordEditText = findViewById(R.id.editTextPsw);

        final Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<LoginResponse> repos = service.login(new UserRequest(usernameEditText.getText().toString(),passwordEditText.getText().toString()));
                loginButton.setClickable(false);
                repos.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(

                            Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body().getBody().equals("OK") && response.body().getStatusCode().equals("200")) {

                            Toast.makeText(MainActivity.this, getString(R.string.Login_success), Toast.LENGTH_LONG).show();
                            image.setImageResource(R.mipmap.verde);
                            Intent intent=new Intent(getApplicationContext(), ListaProfili.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(MainActivity.this, getString(R.string.Login_failed), Toast.LENGTH_LONG).show();
                            image.setImageResource(R.mipmap.rosso);
                            loginButton.setClickable(true);

                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                        usernameEditText.setText(t.getMessage()); // can also do textv.setText(t.getMessage()) to display error reason
//                        t.printStackTrace();
                        loginButton.setClickable(true);
                    }
                });
            }
        });
    }

}



