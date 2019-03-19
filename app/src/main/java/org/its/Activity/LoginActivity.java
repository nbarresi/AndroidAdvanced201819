package org.its.Activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import org.its.entity.Login;
import org.its.entity.LoginResponse;
import org.its.interfaces.LoginService;

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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create()).build();


        final LoginService service = retrofit.create(LoginService.class);

                accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<LoginResponse> responseLogin = service.login(new Login(String.valueOf(username.getText()), String.valueOf(password.getText())));

                responseLogin.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse result = response.body();
                        if (result.getStatusCode() == 200) {
                            image.setBackgroundColor(getResources().getColor(R.color.ok));
                            error.setVisibility(TextView.INVISIBLE);
                        } else {
                            image.setBackgroundColor(getResources().getColor(R.color.error));
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
