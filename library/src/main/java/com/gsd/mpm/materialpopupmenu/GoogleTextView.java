package com.gsd.mpm.materialpopupmenu;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class GoogleTextView extends android.support.v7.widget.AppCompatTextView {

    public GoogleTextView(Context context) {
        super(context);
    }

    public GoogleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();

    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/GoogleSans-Regular.ttf");
        setTypeface(font);
    }

    public GoogleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();

    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if(focused)
            super.onWindowFocusChanged(focused);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}