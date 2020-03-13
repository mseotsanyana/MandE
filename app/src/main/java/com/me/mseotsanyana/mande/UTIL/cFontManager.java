package com.me.mseotsanyana.mande.UTIL;



import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by amit on 26/3/16.
 */
public class cFontManager {

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}
