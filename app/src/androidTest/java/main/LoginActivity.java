package main;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androidadvanced201819.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        Button myButton = (Button) findViewById(R.id.accedi);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Retrofit retrofit= new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .build();
                LoginService service =retrofit.create(LoginService.class);

                EditText usernameEdit = (EditText) findViewById(R.id.username);
                EditText passwordEdit = (EditText) findViewById(R.id.password);
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                LoginRequest request = new LoginRequest(username,password);

                Call<LoginResponse> response = service.login(request);

                final ImageView loginImage = (ImageView) findViewById(R.id.imageView);

                response.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(
                            Call<LoginResponse> call, Response<LoginResponse> response) {
                        Drawable image;
                        if(response.body().getStatusCode() == 200){
                            image = getResources().getDrawable(R.drawable.successicon);
                        }
                        else{
                            image = getResources().getDrawable(R.drawable.erroricon);
                        }
                        loginImage.setImageDrawable(image);
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
