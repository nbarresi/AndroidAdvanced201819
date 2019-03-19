package org.its.login.interfaces;



import org.its.login.entity.Login;
import org.its.login.entity.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("login")
    Call<LoginResponse> login(@Body Login login);
}
