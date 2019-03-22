package org.its.login.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androidadvanced201819.R;


import org.its.login.Servicies.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        Button myButton = (Button) findViewById(R.id.accedi);
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                Retrofit retrofit= new Retrofit.Builder()
                        .baseUrl("https://kx99i37oa2.execute-api.eu-west-2.amazonaws.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginService service =retrofit.create(LoginService.class);

                EditText usernameEdit = (EditText) findViewById(R.id.username);
                EditText passwordEdit = (EditText) findViewById(R.id.password);
                final String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                LoginRequest request = new LoginRequest(username,password);

                Call<LoginResponse> response = service.login(request);

                final ImageView loginImage = (ImageView) findViewById(R.id.imageView);

                response.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(
                            Call<LoginResponse> call, Response<LoginResponse> response) {
                        System.out.println(response.code());
                        if(response.body().getStatusCode() == 200){
                            Drawable image = getResources().getDrawable(R.drawable.successicon);
                            loginImage.setImageDrawable(image);
;
                                Intent intent = new Intent(LoginActivity.this, ProfileListActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);

                        }
                        else{
                            Drawable image = getResources().getDrawable(R.drawable.erroricon);
                            loginImage.setImageDrawable(image);
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
