package com.example.instgramclone;

import android.app.Application;

import com.parse.Parse;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("lLtF7DHrM087QOnZnkm9lShI8jl0Fls1mBwAqf3d")
                .clientKey("MBhKIKrBXlWRvMmDXb68HVQSgPJWUYoWVXxQuFfo")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
    }
