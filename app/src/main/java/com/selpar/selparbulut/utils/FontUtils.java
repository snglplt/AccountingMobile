package com.selpar.selparbulut.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dev  on 10/03/2020.
 */
@SuppressWarnings("unused")
public class FontUtils {
    private FontUtils() {

    }

    public static Typeface sTypeface = null;


    public static Typeface loadFont(Context context, String fileName) {
        sTypeface = Typeface.createFromAsset(context.getAssets(), fileName);
        return sTypeface;
    }


    public static void setFont(ViewGroup group) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(sTypeface);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v);
        }
    }

    public static void setFont(View v) {
        if (v instanceof TextView) {
            ((TextView) v).setTypeface(sTypeface);
        }
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Typeface getTypefaceFromRaw(Context context, int resourceId) {
        InputStream inputStream = null;
        BufferedOutputStream bos = null;
        OutputStream os = null;
        Typeface typeface = null;
        try {
            // Load font(in res/raw) to memory
            inputStream = context.getResources().openRawResource(resourceId);

            // Output font to temporary file
            String fontFilePath = context.getCacheDir() + "/tmp" + System.currentTimeMillis() + ".raw";

            os = new FileOutputStream(fontFilePath);
            bos = new BufferedOutputStream(os);

            byte[] buffer = new byte[inputStream.available()];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }

            // When loading completed, delete temporary files
            typeface = Typeface.createFromFile(fontFilePath);
            new File(fontFilePath).delete();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            tryClose(bos);
            tryClose(os);
            tryClose(inputStream);
        }

        return typeface;
    }


    private static void tryClose(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}