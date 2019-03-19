package org.its.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import org.its.login.entity.Login;
import org.its.login.entity.LoginResponse;
import org.its.login.interfaces.LoginService;
import org.its.utilities.StringCollection;

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

        final EditText username = (EditText) findViewById(R.id.loginEmail);
        final EditText password = (EditText) findViewById(R.id.loginPass);
        Button accedi = (Button) findViewById(R.id.accedi);

        final TextView error = findViewById(R.id.error);

        final ImageView image = findViewById(R.id.image);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(StringCollection.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();


        final LoginService service = retrofit.create(LoginService.class);

                accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<LoginResponse> responseLogin = service.login(new Login(String.valueOf(username.getText()), String.valueOf(password.getText())));

                responseLogin.enqueue(new Callback<LoginResponse>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse result = response.body();
                        if (result.getStatusCode() == 200) {
                            image.setBackground(getResources().getDrawable(R.drawable.succes));
                            error.setVisibility(TextView.INVISIBLE);
                            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                            startActivity(intent);
                        } else {
                            image.setBackground(getResources().getDrawable(R.drawable.error));
                            error.setVisibility(TextView.VISIBLE);
                            error.setText("errore durante il login");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        System.out.println("errore");
                    }
                });
            }
        });
    }
}
