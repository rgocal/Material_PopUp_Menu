package com.gocalsd.dynamicpopupmenu.library;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupWindows {

    public Context mContext;
    public PopupWindow mWindow;
    public View mRootView;
    private Drawable mBackground = null;
    public WindowManager mWindowManager;

    public PopupWindows(Context context) {
        super();
        mContext = context;
        mWindow = new PopupWindow(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    protected void onDismiss() {
    }

    private void onShow() {
    }

    public void preShow() {
        if (mRootView == null)
            throw new IllegalStateException("Needs a root view!");
        onShow();
        mWindow.setBackgroundDrawable(mBackground);
        mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);
        mWindow.setContentView(mRootView);
    }

    public void setBackgroundDrawable(Drawable background) {
        mBackground = background;
    }

    public void setContentView(View root) {
        mRootView = root;
        mWindow.setContentView(root);
    }

    public void setContentView(int layoutResID) {
        LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflator != null;
        setContentView(inflator.inflate(layoutResID, null));
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mWindow.setOnDismissListener(listener);
    }

    protected void dismiss() {
        mWindow.dismiss();
    }
}