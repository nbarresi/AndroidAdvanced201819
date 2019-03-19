package com.example.androidadvanced201819.model;

public class LoginResponse {

    private int statusCode;
    private String body;

    public LoginResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
