package com.gsd.mpm.materialpopupmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class PopUpMenu extends PopupWindows implements OnDismissListener {

    private ImageView mArrowUp, mArrowDown;
    private Animation mTrackAnim;
    private LayoutInflater inflater;
    private ViewGroup mTrack, mVertTrack;
    private OnActionItemClickListener mItemClickListener;
    private OnDismissListener mDismissListener;
    private TextView menuTitle;

    private LinearLayout header;
    private HorizontalScrollView scroll;
    private ScrollView vertScroll;
    private View menu_header, menu_footer, top_menu_footer, top_menu_header;

    private List<ActionItem> mActionItemList = new ArrayList<>();
    private List<ActionItem> mActionItemVerticalList = new ArrayList<>();

    private boolean mDidAction;
    private boolean mAnimateTrack;
    private boolean isLight;
    private boolean isEnabled;
    private boolean hasTitles;
    private boolean ThemeVertIcons;

    private int mChildPos, mVertChildPos;
    private int mAnimStyle;

    private static final int ANIM_GROW_FROM_LEFT = 1;
    private static final int ANIM_GROW_FROM_RIGHT = 2;
    private static final int ANIM_GROW_FROM_CENTER = 3;
    private static final int ANIM_AUTO = 4;
    private static final int ANIM_NONE = 5;

    private int mScrollVertColor, mScrollHorizontalColor, mTitleBkgColor;
    private int mBodyColor;
    private boolean enableHeaderTitle;

    private ActionItem getActionItem(int index) {
        return mActionItemList.get(index);
    }

    private ActionItem getVertActionItem(int index) {
        return mActionItemVerticalList.get(index);
    }

    public PopUpMenu(Context context) {
        super(context);
        setRootViewId(R.layout.quick_action_menu);
    }

    //Set Menu Title or Text
    public void setMenuTitle (String menuTitleString){
        menuTitle.setText(menuTitleString);
    }

    public void setTitleBackgroundColor(int scrollColor) {
        this.mTitleBkgColor = scrollColor;

        Drawable mDrawableBody = ContextCompat.getDrawable(mContext, R.drawable.scroll_background);
        assert mDrawableBody != null;
        Drawable wrappedBody = DrawableCompat.wrap(mDrawableBody);
        DrawableCompat.setTint(wrappedBody, mTitleBkgColor);
        wrappedBody.invalidateSelf();

        menuTitle.setBackground(wrappedBody);
    }

    public void setHorizontalScrollColor(int scrollColor) {
        this.mScrollHorizontalColor = scrollColor;

        Drawable mDrawableBody = ContextCompat.getDrawable(mContext, R.drawable.scroll_background);
        assert mDrawableBody != null;
        Drawable wrappedBody = DrawableCompat.wrap(mDrawableBody);
        DrawableCompat.setTint(wrappedBody, mScrollHorizontalColor);
        wrappedBody.invalidateSelf();

        scroll.setBackground(wrappedBody);
        menuTitle.setBackground(wrappedBody);
    }

    public void setVertScrollColor(int scrollColor) {
        this.mScrollVertColor = scrollColor;

        Drawable mDrawableBody = ContextCompat.getDrawable(mContext, R.drawable.scroll_background);
        assert mDrawableBody != null;
        Drawable wrappedBody = DrawableCompat.wrap(mDrawableBody);
        DrawableCompat.setTint(wrappedBody, mScrollVertColor);
        wrappedBody.invalidateSelf();

        vertScroll.setBackground(wrappedBody);
    }

    public void setOuterColor(int bodyColor) {
        this.mBodyColor = bodyColor;

        Drawable mDrawableHeader = ContextCompat.getDrawable(mContext, R.drawable.qa_round_top);
        assert mDrawableHeader != null;
        Drawable wrappedHeader = DrawableCompat.wrap(mDrawableHeader);
        DrawableCompat.setTint(wrappedHeader, mBodyColor);
        wrappedHeader.invalidateSelf();

        top_menu_header.setBackground(wrappedHeader);
        menu_header.setBackground(wrappedHeader);

        Drawable mDrawableFooter = ContextCompat.getDrawable(mContext, R.drawable.qa_round_bottom);
        assert mDrawableFooter != null;
        Drawable wrappedFooter = DrawableCompat.wrap(mDrawableFooter);
        DrawableCompat.setTint(wrappedFooter, mBodyColor);
        wrappedFooter.invalidateSelf();

        menu_footer.setBackground(wrappedFooter);
        top_menu_footer.setBackground(wrappedFooter);

    }

    private int getBodyColor(){
        return mBodyColor;
    }

    private void setRootViewId(int id) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTrackAnim = AnimationUtils.loadAnimation(mContext, R.anim.rail);
        mTrackAnim.setInterpolator(new Interpolator() {
            public float getInterpolation(float t) {
                final float inner = (t * 1.55f) - 1.1f;
                return 1.2f - inner * inner;
            }
        });

        mRootView = inflater.inflate(id, null);
        mTrack = mRootView.findViewById(R.id.tracks);
        mVertTrack = mRootView.findViewById(R.id.vert_tracks);

        //Set the pop colors by strings if desired
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollVertColor = mContext.getColor(R.color.popup_scroll_color);
            mScrollHorizontalColor = mContext.getColor(R.color.popup_scroll_color);
            mTitleBkgColor = mContext.getColor(R.color.popup_scroll_color);
            mBodyColor = mContext.getColor(R.color.popup_body_color);
        }

        //Initialize the IDs
        mArrowDown = mRootView.findViewById(R.id.arrow_down);
        mArrowUp = mRootView.findViewById(R.id.arrow_up);

        header = mRootView.findViewById(R.id.header2);

        top_menu_header = mRootView.findViewById(R.id.top_menu_header);
        menu_header = mRootView.findViewById(R.id.menu_header);
        top_menu_footer = mRootView.findViewById(R.id.top_menu_footer);
        menu_footer = mRootView.findViewById(R.id.menu_footer);

        scroll = mRootView.findViewById(R.id.scroll);
        vertScroll = mRootView.findViewById(R.id.vert_scroll);

        menuTitle = mRootView.findViewById(R.id.menu_title);

        if(enableHeaderTitle) {
            menuTitle.setVisibility(View.GONE);
        }

        scroll.setHorizontalScrollBarEnabled(isEnabled);
        vertScroll.setVerticalScrollBarEnabled(isEnabled);

        setContentView(mRootView);
    }

    //Animate the menu track
    public void mAnimateTrack(boolean mAnimateTrack) {
        this.mAnimateTrack = mAnimateTrack;
    }

    //Theme vertical icons (user may want to use colored vector icons "Like me!")
    public void themeVerticalIcons(boolean enable) {
        this.ThemeVertIcons = enable;
    }

    //Set a custom anim style
    public int setAnimStyle(int mAnimStyle) {
        this.mAnimStyle = mAnimStyle;
        return mAnimStyle;
    }

    //Set Light or Dark styled icons and text
    public void setLightTheme(boolean isLight) {
        this.isLight = isLight;
        if(isLight){
            menuTitle.setTextColor(Color.WHITE);
        }else{
            menuTitle.setTextColor(Color.BLACK);
        }
    }

    //Enable a Header
    public void hasVerticalExpansion(boolean hasHeader) {
        if(hasHeader){
            header.setVisibility(View.VISIBLE);
        }else{
            header.setVisibility(View.GONE);
        }
    }

    public void setHasActionTitles(boolean hasTitles){
        this.hasTitles = hasTitles;
    }

    public void setHasHeaderTitle(boolean hasTitle){
        this.enableHeaderTitle = hasTitle;
    }

    //Set Scrollbar
    public void setScrollBar(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    //Here we're adding a vertical menu for longer titled actions
    //Must have header enabled first
    public void addVerticalActionItem(ActionItem vertAction){
        mActionItemVerticalList.add(vertAction);

        String title = vertAction.getTitle();
        Drawable icon = vertAction.getIcon();
        View container = inflater.inflate(R.layout.quick_action_list_item, null);

        ImageView img = container.findViewById(R.id.iv_icon);
        ImageView item = container.findViewById(R.id.iv_item);
        TextView text = container.findViewById(R.id.tv_title);

        if(isLight) {
            item.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

            text.setTextColor(Color.WHITE);
        }else{
            item.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            text.setTextColor(Color.BLACK);
        }

        if(ThemeVertIcons){
            if(isLight) {
                img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            }else{
                img.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            }
        }

        if (icon != null) {
            img.setImageDrawable(icon);
        } else {
            img.setVisibility(View.GONE);
        }
        if (title != null) {
            text.setText(title);
        } else {
            text.setVisibility(View.GONE);
        }

        final int pos = mVertChildPos;
        final int actionId = vertAction.getActionId();
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(PopUpMenu.this, pos, actionId);
                }
                if (!getVertActionItem(pos).isSticky()) {
                    mDidAction = true;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                }
            }
        });
        container.setFocusable(true);
        container.setClickable(true);
        mVertTrack.addView(container, mVertChildPos);
        mVertChildPos++;
    }

    public void addActionItem(ActionItem action) {
        mActionItemList.add(action);

        String title = action.getTitle();
        Drawable icon = action.getIcon();
        View container = inflater.inflate(R.layout.quick_action_item, null);
        ImageView img = container.findViewById(R.id.iv_icon);
        TextView text = container.findViewById(R.id.tv_title);
        if(isLight) {
            img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            text.setTextColor(Color.WHITE);
        }else{
            img.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            text.setTextColor(Color.BLACK);
        }
        if (icon != null) {
            img.setImageDrawable(icon);
        } else {
            img.setVisibility(View.GONE);
        }
        if (title != null) {
            text.setText(title);
        } else {
            text.setVisibility(View.GONE);
        }

        if(hasTitles){
            text.setVisibility(View.VISIBLE);
        }else{
            text.setVisibility(View.GONE);
        }

        final int pos = mChildPos;
        final int actionId = action.getActionId();
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(PopUpMenu.this, pos, actionId);
                }
                if (!getActionItem(pos).isSticky()) {
                    mDidAction = true;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                }
            }
        });
        container.setFocusable(true);
        container.setClickable(true);
        mTrack.addView(container, mChildPos);
        mChildPos++;
    }

    public void setOnActionItemClickListener(OnActionItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void show (View anchor) {
        preShow();
        int[] location = new int[2];
        mDidAction = false;
        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
                + anchor.getHeight());

        DisplayMetrics displayMetrics = new DisplayMetrics();

        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int rootWidth = mRootView.getMeasuredWidth();
        int rootHeight = mRootView.getMeasuredHeight();
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int xPos = (screenWidth - rootWidth) / 2;
        int yPos = anchorRect.top - rootHeight;
        boolean onTop = true;
        if (rootHeight > anchor.getTop()) {
            yPos = anchorRect.bottom;
            onTop = false;
        }

        showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), anchorRect.centerX());
        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);
        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        if (mAnimateTrack)
            mTrack.startAnimation(mTrackAnim);
    }

    private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
        int arrowPos = requestedX - mArrowUp.getMeasuredWidth()/2;

        switch (mAnimStyle) {
            case ANIM_GROW_FROM_LEFT:
                mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
                break;

            case ANIM_GROW_FROM_RIGHT:
                mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
                break;

            case ANIM_GROW_FROM_CENTER:
                mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
                break;

            case ANIM_AUTO:
                if (arrowPos <= screenWidth/4) {
                    mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
                } else if (arrowPos > screenWidth/4 && arrowPos < 3 * (screenWidth/4)) {
                    mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
                } else {
                    mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopDownMenu_Right : R.style.Animations_PopDownMenu_Right);
                }

                break;

            case ANIM_NONE:
                mAnimateTrack = false;
                break;
        }
    }

    private void showArrow(int whichArrow, int requestedX) {
        final ImageView showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;
        final int arrowWidth = mArrowUp.getMeasuredWidth();
        showArrow.setVisibility(View.VISIBLE);
        showArrow.setColorFilter(getBodyColor(), PorterDuff.Mode.SRC_IN);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)showArrow.getLayoutParams();
        param.leftMargin = requestedX - arrowWidth / 2;
        hideArrow.setVisibility(View.INVISIBLE);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        setOnDismissListener(this);
        mDismissListener = listener;
    }

    @Override
    public void onDismiss() {
        if (!mDidAction && mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    public interface OnActionItemClickListener {
        void onItemClick(PopUpMenu source, int pos, int actionId);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
