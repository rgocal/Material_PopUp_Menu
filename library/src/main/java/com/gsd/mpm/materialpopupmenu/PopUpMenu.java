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
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class PopUpMenu extends PopupWindows implements OnDismissListener {

    private ImageView mArrowUp, mArrowDown;
    private Animation mTrackAnim;
    private LayoutInflater inflater;
    private ViewGroup mTrack;
    private OnActionItemClickListener mItemClickListener;
    private OnDismissListener mDismissListener;
    private TextView menuTitle, menuSubTitle;
    private OnClickListener subTitleClickListener;

    private View startTrack, endTrack;
    private Drawable mDrawableFooter, mDrawableHeader, wrappedHeader, wrappedFooter, mDrawableBody, wrappedBody, mDrawableTrack, wrappedTrack;
    private FrameLayout header, footer;
    private HorizontalScrollView scroll;

    private List<ActionItem> mActionItemList = new ArrayList<>();

    private boolean mDidAction;
    private boolean mAnimateTrack;
    private boolean isLight;
    private boolean isEnabled;
    private boolean hasTitles;
    private boolean isTrackEnabled;
    private boolean hasTitle;
    private boolean hasSubTitle;

    private int mChildPos;
    private int mAnimStyle;

    private static final int ANIM_GROW_FROM_LEFT = 1;
    private static final int ANIM_GROW_FROM_RIGHT = 2;
    private static final int ANIM_GROW_FROM_CENTER = 3;
    private static final int ANIM_AUTO = 4;

    private int mScrollColor;
    private int mBodyColor;
    private int mTrackColor;

    private int i;
    private boolean onTop;
    private int rootWidth, rootHeight, screenWidth, xPos, yPos;

    private ActionItem getActionItem(int index) {
        return mActionItemList.get(index);
    }

    public PopUpMenu(Context context) {
        super(context);
        setRootViewId(R.layout.quick_action_menu);
    }

    //Set Menu Title or Text
    public void setMenuTitle (String menuTitleString){
        menuTitle.setText(menuTitleString);
    }

    //Set Menu SubTitle or Text
    public void setSubMenuTitle (String menuSubTitleString){
        menuSubTitle.setText(menuSubTitleString);
    }

    public void setScrollColor(int scrollColor) {
        this.mScrollColor = scrollColor;

        mDrawableBody = ContextCompat.getDrawable(mContext, R.drawable.scroll_background);
        assert mDrawableBody != null;
        wrappedBody = DrawableCompat.wrap(mDrawableBody);
        DrawableCompat.setTint(wrappedBody, mScrollColor);
        wrappedBody.invalidateSelf();
        scroll.setBackground(wrappedBody);
    }

    public void setTrackColor(int trackColor) {
        this.mTrackColor = trackColor;

        mDrawableTrack = ContextCompat.getDrawable(mContext, R.drawable.track_background);
        assert mDrawableTrack != null;
        wrappedTrack = DrawableCompat.wrap(mDrawableTrack);
        DrawableCompat.setTint(wrappedTrack, mTrackColor);
        wrappedTrack.invalidateSelf();

        startTrack.setBackground(wrappedTrack);
        endTrack.setBackground(wrappedTrack);
    }

    public void setBodyColor(int bodyColor) {
        this.mBodyColor = bodyColor;

        mDrawableHeader = ContextCompat.getDrawable(mContext, R.drawable.qa_round_top);
        assert mDrawableHeader != null;
        wrappedHeader = DrawableCompat.wrap(mDrawableHeader);
        DrawableCompat.setTint(wrappedHeader, mBodyColor);
        wrappedHeader.invalidateSelf();

        header.setBackground(wrappedHeader);

        mDrawableFooter = ContextCompat.getDrawable(mContext, R.drawable.qa_round_bottom);
        assert mDrawableFooter != null;
        wrappedFooter = DrawableCompat.wrap(mDrawableFooter);
        DrawableCompat.setTint(wrappedFooter, mBodyColor);
        wrappedFooter.invalidateSelf();

        footer.setBackground(wrappedFooter);

    }

    public int getScrollColor(){
        return mScrollColor;
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

        //Set the pop colors by strings if desired
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollColor = mContext.getColor(R.color.popup_scroll_color);
            mTrackColor = mContext.getColor(R.color.popup_track_color);
            mBodyColor = mContext.getColor(R.color.popup_body_color);
        }

        //Initialize the IDs
        mArrowDown = mRootView.findViewById(R.id.arrow_down);
        mArrowUp = mRootView.findViewById(R.id.arrow_up);
        header = mRootView.findViewById(R.id.header2);
        footer = mRootView.findViewById(R.id.footer);
        startTrack = mRootView.findViewById(R.id.start_track);
        endTrack = mRootView.findViewById(R.id.end_track);
        scroll = mRootView.findViewById(R.id.scroll);

        menuTitle = mRootView.findViewById(R.id.menu_title);
        menuSubTitle = mRootView.findViewById(R.id.menu_subTitle);

        scroll.setHorizontalScrollBarEnabled(isEnabled);

        setContentView(mRootView);
    }

    //Animate the menu track
    public void mAnimateTrack(boolean mAnimateTrack) {
        this.mAnimateTrack = mAnimateTrack;
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
            menuSubTitle.setTextColor(Color.WHITE);
        }else{
            menuTitle.setTextColor(Color.BLACK);
            menuSubTitle.setTextColor(Color.BLACK);
        }
    }

    //Enable a Menu Title
    public void hasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
        if(hasTitle){
            menuTitle.setVisibility(View.VISIBLE);
        }else{
            menuTitle.setVisibility(View.GONE);
        }
    }

    //Enable a Clickable SubTitle for Menu
    public void hasSubTitle(boolean hasSubTitle) {
        this.hasSubTitle = hasSubTitle;
        if(hasSubTitle){
            menuSubTitle.setVisibility(View.VISIBLE);
        }else{
            menuSubTitle.setVisibility(View.GONE);
        }
    }

    //Toggle Tracks
    public void setEnableTracks(boolean enableTracks){
        this.isTrackEnabled = enableTracks;
        if(isTrackEnabled){
            startTrack.setVisibility(View.VISIBLE);
            endTrack.setVisibility(View.VISIBLE);
        }else {
            startTrack.setVisibility(View.GONE);
            endTrack.setVisibility(View.GONE);
        }
    }

    public void setHasTitles(boolean hasTitles){
        this.hasTitles = hasTitles;
    }

    //Set Scrollbar
    public void setScrollBar(boolean isEnabled) {
        this.isEnabled = isEnabled;
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
        mTrack.addView(container, mChildPos+1);
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
        rootWidth = mRootView.getMeasuredWidth();
        rootHeight = mRootView.getMeasuredHeight();
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        xPos = (screenWidth - rootWidth) / 2;
        yPos = anchorRect.top - rootHeight;
        onTop = true;
        if (rootHeight > anchor.getTop()) {
            yPos = anchorRect.bottom;
            onTop = false;
        }

        showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), anchorRect.centerX());
        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);
        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        if (mAnimateTrack) mTrack.startAnimation(mTrackAnim);
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
        }
    }

    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;
        final int arrowWidth = mArrowUp.getMeasuredWidth();
        showArrow.setVisibility(View.VISIBLE);
        ((ImageView) showArrow).setColorFilter(getBodyColor(), PorterDuff.Mode.SRC_IN);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)showArrow.getLayoutParams();
        param.leftMargin = requestedX - arrowWidth / 2;
        hideArrow.setVisibility(View.INVISIBLE);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        setOnDismissListener(this);

        mDismissListener = listener;
    }

    public void setSubTitleOnClickListener(OnClickListener subTitleOnClickListener){
        menuSubTitle.setOnClickListener(subTitleOnClickListener);
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
