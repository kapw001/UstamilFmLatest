package com.prmobiapp.ustamilfm;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.prmobiapp.ustamilfm.activity.MainActivity;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {



    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                finish();
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                // close this activity

            }
        }, SPLASH_TIME_OUT);
    }




    @Override
    public void onClick(View v) {

    }
}
