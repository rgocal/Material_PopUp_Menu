package com.gocalsd.dynamicpopupmenu.library.menus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.gocalsd.dynamicpopupmenu.R;
import com.gocalsd.dynamicpopupmenu.library.PopupWindows;
import com.gocalsd.dynamicpopupmenu.library.data.ActionItem;
import com.gocalsd.dynamicpopupmenu.library.data.SimpleActionItem;
import com.gocalsd.dynamicpopupmenu.library.utils.ColorProvider;
import com.gocalsd.dynamicpopupmenu.library.views.ArrowDrawable;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.ArrayList;
import java.util.List;

import static com.gocalsd.dynamicpopupmenu.library.views.ArrowDrawable.ARROW_DOWN;
import static com.gocalsd.dynamicpopupmenu.library.views.ArrowDrawable.ARROW_UP;

public class VerticalPopupMenu extends PopupWindows implements PopupWindow.OnDismissListener {

    private View mArrowUp, mArrowDown;
    private Animation mStartTrackAnim, mEndTrackAnim;
    private LayoutInflater inflater;
    private ViewGroup mTrack;
    private ScrollView mScrollView;
    private OnActionItemClickListener mItemClickListener;
    private OnDismissListener mDismissListener;

    private boolean mDidAction;
    private int mChildPos;

    private final float iconDensity;
    private int backgroundColor;

    private final List<SimpleActionItem> mActionItemList = new ArrayList<>();
    private MaterialShapeDrawable mDrawableBody;

    private SimpleActionItem getActionItem(int index) {
        return mActionItemList.get(index);
    }

    public VerticalPopupMenu(Context context) {
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
        mScrollView.setBackground(mDrawableBody);

        mArrowDown.setBackground(new ArrowDrawable(ARROW_DOWN, scrollColor, 1, ColorProvider.getDarkerShadeColor(scrollColor)));
        mArrowUp.setBackground(new ArrowDrawable(ARROW_UP, scrollColor, 1, ColorProvider.getDarkerShadeColor(scrollColor)));
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

        mRootView = inflater.inflate(R.layout.vertical_quick_action_menu, null);
        mTrack = mRootView.findViewById(R.id.tracks);
        mScrollView = mRootView.findViewById(R.id.scroll);

        mArrowDown = mRootView.findViewById(R.id.arrow_down);
        mArrowUp = mRootView.findViewById(R.id.arrow_up);

        setContentView(mRootView);
    }

    public void addActionItem(SimpleActionItem action) {
        mActionItemList.add(action);

        Drawable icon = action.getIcon();

        View container = inflater.inflate(R.layout.icon_list_quick_action_item, null);
        AppCompatImageView actionIconTop = container.findViewById(R.id.icon_view);

        if(ColorProvider.isDark(getMenuColor())) {
            icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }else{
            icon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }

        actionIconTop.setImageDrawable(icon);

        final int pos = mChildPos;
        final int actionId = action.getActionId();

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(VerticalPopupMenu.this, pos, actionId);
                }
                if (!getActionItem(pos).isSticky()) {
                    mDidAction = true;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            mTrack.startAnimation(mEndTrackAnim);
                            dismiss();
                        }
                    });
                }
            }
        });

        mTrack.addView(container, mChildPos);
        mChildPos++;
    }

    public void setOnActionItemClickListener(VerticalPopupMenu.OnActionItemClickListener listener) {
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
                //subtract some of this value to reposition arrow correctly
                xPos = anchorRect.centerX()-(rootWidth/2) - 42;
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
                ViewGroup.LayoutParams l = mTrack.getLayoutParams();
                l.height = dyTop-anchor.getHeight();
            } else {
                yPos = anchorRect.top-rootHeight;
            }
        } else {
            yPos = anchorRect.bottom;

            if (rootHeight > dyBottom) {
                ViewGroup.LayoutParams l = mTrack.getLayoutParams();
                l.height = dyBottom;
            }
        }

        showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), arrowPos);

        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        mTrack.startAnimation(mStartTrackAnim);
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

    public void setOnDismissListener(VerticalPopupMenu.OnDismissListener listener) {
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
        void onItemClick(VerticalPopupMenu source, int pos, int actionId);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}