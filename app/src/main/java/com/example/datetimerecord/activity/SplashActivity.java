package com.example.datetimerecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.example.datetimerecord.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final String COMMON_TAG ="mAppLog";
    private static final int time = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try{
                            sleep(time);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(COMMON_TAG,TAG+" onDestroy");
    }
}
