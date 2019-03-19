package com.example.androidadvanced201819.ServiceUtility;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
        @POST("login")
        Call<LoginResponse> login(@Body UserRequest request);
}
