package com.otassistant.activities;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.otassistant.R;
import com.otassistant.global.AppConfig;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    //DO NOT OVERRIDE onCreate()!
    //if you need to start some services do it in initSplash()!


    @Override
    public void initSplash(ConfigSplash configSplash) {
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimaryDark); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms


        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logo1); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Shake); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)




        //Customize Title
        configSplash.setTitleSplash("HealthCare");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);

    }

   @Override
    public void animationsFinished() {
       if (AppConfig.isLogin(SplashActivity.this)) {
           Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
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