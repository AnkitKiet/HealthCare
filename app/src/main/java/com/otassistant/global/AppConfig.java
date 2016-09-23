package com.otassistant.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

/**
 * Created by Ankit on 18/09/16.
 */
public class AppConfig {
    public static String ISLOGIN = "isLogin";
    public static Fragment currentFragment;


    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences;
    }


    public static SharedPreferences.Editor getSharedEditor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.edit();
    }
    public static Boolean isLogin(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean isLogin = sharedPreferences.getBoolean(AppConfig.ISLOGIN, false);
        return isLogin;
    }

    public static void login(Context context) {
        SharedPreferences.Editor editor = getSharedEditor(context);
        editor.putBoolean(AppConfig.ISLOGIN, true);

        editor.commit();
    }

    public static void logout(Context context) {
        SharedPreferences.Editor editor = getSharedEditor(context);
        editor.putBoolean(AppConfig.ISLOGIN, false);
        editor.commit();
    }

}

