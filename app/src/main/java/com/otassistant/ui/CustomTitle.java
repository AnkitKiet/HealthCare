package com.otassistant.ui;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;

/**
 * Created by Ankit on 18/09/16.
 */
public class CustomTitle {
    public static SpannableString getTitle(Context context, String title){
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(context, "robo_font.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public static SpannableString getPlainTitle(Context context, String title){
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(context, "robo_font_bold.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }
}
