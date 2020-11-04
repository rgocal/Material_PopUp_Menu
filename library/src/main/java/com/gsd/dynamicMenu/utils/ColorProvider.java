package com.gsd.dynamicMenu.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;


public class ColorProvider {

    public static boolean isDark(int color){
        return ColorUtils.calculateLuminance(color) <0.5;
    }

    public static int getTextColor(Bitmap bitmap){
        try{
            Palette palette = Palette.from(bitmap).generate();
            Palette.Swatch dominantSwatch = palette.getDominantSwatch();
            if(dominantSwatch != null){
                return dominantSwatch.getTitleTextColor();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            return android.R.attr.textColorPrimary;
        }
    }

    public static int getLighterShadeColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 1.35f;
        color = Color.HSVToColor(hsv);
        return color;
    }

    public static int getDarkerShadeColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.80f;
        color = Color.HSVToColor(hsv);
        return color;
    }

    public static boolean isNullColor(int color){
        return String.format("#%06x", color & 0xffffff).equals("#000000");
    }

}