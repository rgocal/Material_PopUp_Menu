package com.gsd.dynamicMenu.menus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.ColorUtils;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.gsd.dynamicMenu.PopupWindows;
import com.gsd.dynamicMenu.data.ActionItem;
import com.gsd.dynamicMenu.data.SimpleActionItem;
import com.gsd.dynamicMenu.utils.ColorProvider;
import com.gsd.dynamicMenu.views.ArrowDrawable;
import com.gsd.dynamicMenu.views.GoogleTextView;
import com.gsd.mpm.materialpopupmenu.R;

import java.util.ArrayList;
import java.util.List;

public class PixelPopupMenu extends PopupWindows implements PopupWindow.OnDismissListener {

    private View mArrowUp, mArrowDown;
    private Animation mStartTrackAnim, mEndTrackAnim;
    private LayoutInflater inflater;
    private ViewGroup mTrackVert, mTrackHoriz;
    private ScrollView mScrollViewVert;
    private HorizontalScrollView mScrollViewHoriz;
    private LinearLayoutCompat scrollHolder;
    private OnActionItemClickListener mItemClickListener;
    private OnDismissListener mDismissListener;

    private boolean mDidAction;
    private boolean hasFilter;
    private int mVerticalChildPos, mHorizontalChildPos;

    private final float iconDensity;
    private int backgroundColor;

    private final List<ActionItem> mActionItemVertList = new ArrayList<>();
    private final List<SimpleActionItem> mActionItemHorizontalList = new ArrayList<>();
    private MaterialShapeDrawable mDrawableBody;

    private ActionItem getActionVertItem(int index) {
        return mActionItemVertList.get(index);
    }

    private SimpleActionItem getActionHorizItem(int index) {
        return mActionItemHorizontalList.get(index);
    }

    public PixelPopupMenu(Context context) {
        super(context);
        setRootViewId();
        iconDensity = context.getResources().getDisplayMetrics().density * 42;
    }

    public void setBackgroundRadias(int radias){
        mDrawableBody.setCornerSize(radias);
    }


    public void setBackgroundColor(int scrollColor) {
        this.backgroundColor = scrollColor;
        mDrawableBody = new MaterialShapeDrawable();
        mDrawableBody.setTint(scrollColor);

        scrollHolder.setBackground(mDrawableBody);

        mArrowDown.setBackground(new ArrowDrawable(ArrowDrawable.getArrowDown(), scrollColor, 1, ColorProvider.getDarkerShadeColor(scrollColor)));
        mArrowUp.setBackground(new ArrowDrawable(ArrowDrawable.getArrowUp(), scrollColor, 1, ColorProvider.getDarkerShadeColor(scrollColor)));
    }

    private int getMenuColor(){
        return this.backgroundColor;
    }

    private void setRootViewId() {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mStartTrackAnim = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
        mStartTrackAnim.setInterpolator(new Interpolator() {
            public float getInterpolation(float t) {
                final float inner = (t * 1.55f) - 1.1f;
                return 1.2f - inner * inner;
            }
        });

        mEndTrackAnim = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
        mEndTrackAnim.setInterpolator(new Interpolator() {
            public float getInterpolation(float t) {
                final float inner = (t * 1.55f) - 1.1f;
                return 1.2f - inner * inner;
            }
        });

        mRootView = inflater.inflate(R.layout.pixel_quick_action_menu, null);

        mTrackVert = mRootView.findViewById(R.id.tracks_vertical);
        mTrackHoriz = mRootView.findViewById(R.id.tracks_horizontal);

        mScrollViewHoriz = mRootView.findViewById(R.id.scroll_horizontal);
        mScrollViewVert = mRootView.findViewById(R.id.scroll_vertical);

        Drawable horizontalTint;
        if(ColorProvider.isDark(getMenuColor())){
            int drawableTint = ColorUtils.setAlphaComponent(Color.WHITE, 128);
            horizontalTint = new ColorDrawable(drawableTint);
        }else{
            int drawableTint = ColorUtils.setAlphaComponent(Color.BLACK, 128);
            horizontalTint = new ColorDrawable(drawableTint);
        }

        mScrollViewHoriz.setBackground(horizontalTint);

        scrollHolder = mRootView.findViewById(R.id.scroll_holder);

        mArrowDown = mRootView.findViewById(R.id.arrow_down);
        mArrowUp = mRootView.findViewById(R.id.arrow_up);

        setContentView(mRootView);
    }


    public void setHasIconColorFilter(boolean enableFilter){
        this.hasFilter = enableFilter;
    }

    private boolean hasIconFilter(){
        return this.hasFilter;
    }

    public void addHorizontalActionItem(SimpleActionItem action) {
        mActionItemHorizontalList.add(action);

        Drawable icon = action.getIcon();

        View container = inflater.inflate(R.layout.icon_list_quick_action_item, null);
        AppCompatImageView actionIconTop = container.findViewById(R.id.icon_view);

        if(ColorProvider.isDark(getMenuColor())) {
            icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }else{
            icon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }

        actionIconTop.setImageDrawable(icon);

        final int pos = mHorizontalChildPos;
        final int actionId = action.getActionId();

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(PixelPopupMenu.this, pos, actionId);
                }
                if (!getActionHorizItem(pos).isSticky()) {
                    mDidAction = true;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            mTrackHoriz.startAnimation(mEndTrackAnim);
                            dismiss();
                        }
                    });
                }
            }
        });

        mTrackHoriz.addView(container, mHorizontalChildPos);
        mHorizontalChildPos++;
    }

    public void addVerticalActionItem(ActionItem action) {
        mActionItemVertList.add(action);

        String title = String.format(" %s ", action.getTitle());
        Drawable icon = action.getIcon();

        View container = inflater.inflate(R.layout.vertical_list_quick_action_item, null);
        GoogleTextView actionTitle = container.findViewById(R.id.action_title);
        AppCompatImageView actionIconTop = container.findViewById(R.id.action_icon_top);

        if(ColorProvider.isDark(getMenuColor())) {
            actionTitle.setTextColor(Color.WHITE);
        }else{
            actionTitle.setTextColor(Color.BLACK);
        }

        if(hasIconFilter()){
            if(ColorProvider.isDark(getMenuColor())) {
                icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            }else{
                icon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            }
        }

        actionIconTop.setImageDrawable(icon);

        actionTitle.setText(title);

        final int pos = mVerticalChildPos;
        final int actionId = action.getActionId();

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(PixelPopupMenu.this, pos, actionId);
                }
                if (!getActionVertItem(pos).isSticky()) {
                    mDidAction = true;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            mTrackVert.startAnimation(mEndTrackAnim);
                            dismiss();
                        }
                    });
                }
            }
        });

        mTrackVert.addView(container, mVerticalChildPos);
        mVerticalChildPos++;
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

        DisplayMetrics displaymetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displaymetrics);

        mRootView.measure(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        int rootWidth = mRootView.getMeasuredWidth();
        int rootHeight = mRootView.getMeasuredHeight();

        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        // automatically get X coord of quick_action_vertical (top left)
        int arrowPos;
        int xPos;
        if ((anchorRect.left+rootWidth) > screenWidth) {
            xPos = anchorRect.left-(rootWidth-anchor.getWidth());
            xPos = Math.max(xPos, 0);

            arrowPos = anchorRect.centerX()- xPos;
        } else {
            if (anchor.getWidth() > rootWidth) {
                xPos = anchorRect.centerX()-(rootWidth/2);
            } else {
                xPos = anchorRect.left;
            }

            arrowPos = anchorRect.centerX()- xPos;
        }

        int dyTop = anchorRect.top;
        int dyBottom = screenHeight-anchorRect.bottom;

        boolean onTop = dyTop > dyBottom;

        int yPos;
        if (onTop) {
            if (rootHeight > dyTop) {
                yPos = 15;
                ViewGroup.LayoutParams l = scrollHolder.getLayoutParams();
                l.height = dyTop-anchor.getHeight();
            } else {
                yPos = anchorRect.top-rootHeight;
            }
        } else {
            yPos = anchorRect.bottom;

            if (rootHeight > dyBottom) {
                ViewGroup.LayoutParams l = scrollHolder.getLayoutParams();
                l.height = dyBottom;
            }
        }

        showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), arrowPos);

        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        scrollHolder.startAnimation(mStartTrackAnim);
    }

    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;
        final int arrowWidth = (int) iconDensity;
        showArrow.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)
                showArrow.getLayoutParams();
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
        void onItemClick(PixelPopupMenu source, int pos, int actionId);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}