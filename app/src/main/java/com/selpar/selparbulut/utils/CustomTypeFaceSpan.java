package com.selpar.selparbulut.utils;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by Dev  on 10/03/2020.
 */
@SuppressLint("ParcelCreator")
public class CustomTypeFaceSpan extends TypefaceSpan {
    private final Typeface type;

    /**
     * @param family The font family for this typeface.  Examples include
     *               "monospace", "serif", and "sans-serif".
     */
    public CustomTypeFaceSpan(String family, Typeface typeface) {
        super(family);
        this.type = typeface;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        applyCustomFont(ds, type);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        super.updateMeasureState(paint);
        applyCustomFont(paint, type);
    }

    private void applyCustomFont(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }
        int fake = oldStyle & ~tf.getStyle();

        paint.setFakeBoldText(true);

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(tf);
    }
}
