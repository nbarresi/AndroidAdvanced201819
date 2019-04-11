package org.its.login.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("academy/login")
    Call<LoginResponse> login(@Body LoginRequest body);
}
