package com.selpar.selparbulut.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by Dev  on 10/03/2020.
 */
public class FontCache {

    private FontCache() {

    }

    private static HashMap<String, Typeface> varfontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = varfontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            varfontCache.put(fontname, typeface);
        }

        return typeface;
    }
}