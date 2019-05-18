package com.gsd.mpm.materialpopupexample;

import android.graphics.drawable.Drawable;

public class DataItems {

    private String title, desc;
    private int color;
    private Drawable icon;
    long id;

    public DataItems(long id, String title, String desc, Drawable icon, int color) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.color = color;
    }

    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}