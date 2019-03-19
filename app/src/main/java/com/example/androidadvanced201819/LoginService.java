package com.example.androidadvanced201819;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
        @POST("login")
        Call<LoginResponse> login(@Body UserRequest request);
}
