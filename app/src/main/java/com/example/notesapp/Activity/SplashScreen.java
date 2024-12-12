package com.example.notesapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.R;

public class SplashScreen extends AppCompatActivity {

    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        HandlerThread handlerThread = new HandlerThread("SplashScreenThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());

        backgroundHandler.postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundHandler != null) {
            backgroundHandler.getLooper().quit();
        }
    }
}
