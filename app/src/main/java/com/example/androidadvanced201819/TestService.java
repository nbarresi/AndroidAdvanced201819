package com.example.androidadvanced201819;

import android.app.IntentService;
import android.content.Intent;

public class TestService extends IntentService {
    public TestService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();
    }
    
}
