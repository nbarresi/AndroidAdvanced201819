package com.example.androidadvanced201819.interfaces;


import com.example.androidadvanced201819.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestService {


    @POST("academy/login")
    Call<User> login(@Body User user);

}
