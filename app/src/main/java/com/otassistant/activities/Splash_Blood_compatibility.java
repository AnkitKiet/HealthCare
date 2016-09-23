package com.otassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.otassistant.R;

/**
 * Created by Ankit on 22/09/16.
 */
public class Splash_Blood_compatibility extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_bloodcompat);


        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(Splash_Blood_compatibility.this, Select_blood_group.class);
                    startActivity(intent);

                }
            }
        };

        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    public void onBackPressed() {
    }
}
