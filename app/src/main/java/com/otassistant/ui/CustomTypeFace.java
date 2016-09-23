package com.otassistant.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ankit on 18/09/16.
 */
public class CustomTypeFace {
    public static Typeface getTypeface(Context context){
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/robo_font.otf");
        return face;
    }

    public static Typeface getBoldTypeface(Context context){
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/robo_font_bold.otf");
        return face;
    }
}

