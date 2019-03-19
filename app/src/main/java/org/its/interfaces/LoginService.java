package org.its.interfaces;

import org.its.entity.Login;
import org.its.entity.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("login")
    Call<LoginResponse> login(@Body Login login);
}
