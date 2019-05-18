package com.gsd.mpm.materialpopupexample;

import android.view.View;

public interface CustomClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}