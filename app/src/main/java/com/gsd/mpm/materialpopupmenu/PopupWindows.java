package com.gsd.mpm.materialpopupmenu;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

import android.widget.PopupWindow;
import android.content.Context;

public class PopupWindows {

    Context mContext;
    PopupWindow mWindow;
    View mRootView;
    private Drawable mBackground = null;
    WindowManager mWindowManager;
    PopupWindows(Context context) {
        mContext= context;
        mWindow = new PopupWindow(context);
        mWindow.setTouchInterceptor(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    protected void onDismiss() {
    }

    private void onShow() {
    }

    void preShow() {
        if (mRootView == null)
            throw new IllegalStateException("Needs a root view!");
        onShow();
        if (mBackground == null)
            mWindow.setBackgroundDrawable(new BitmapDrawable());
        else
            mWindow.setBackgroundDrawable(mBackground);
        mWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);

        mWindow.setContentView(mRootView);
    }

    public void setBackgroundDrawable(Drawable background) {
        mBackground = background;
    }

    void setContentView(View root) {
        mRootView = root;
        mWindow.setContentView(root);
    }

    public void setContentView(int layoutResID) {
        LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflator != null;
        setContentView(inflator.inflate(layoutResID, null));
    }

    void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mWindow.setOnDismissListener(listener);
    }

    void dismiss() {
        mWindow.dismiss();
    }
}