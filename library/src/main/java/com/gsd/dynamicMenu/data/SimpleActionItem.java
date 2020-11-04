package com.gocalsd.dynamicpopupmenu.library.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class SimpleActionItem  {

        private Drawable icon;
        private Bitmap thumb;
        private int actionId = -1;
        private boolean selected;
        private boolean sticky;

        public SimpleActionItem(int actionId, Drawable icon, boolean isSticky) {
            this.icon = icon;
            this.actionId = actionId;
            this.sticky = isSticky;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public int getActionId() {
            return actionId;
        }

        public void setSticky(boolean sticky) {
            this.sticky = sticky;
        }

        public boolean isSticky() {
            return sticky;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setThumb(Bitmap thumb) {
            this.thumb = thumb;
        }

        public Bitmap getThumb() {
            return this.thumb;
        }
}