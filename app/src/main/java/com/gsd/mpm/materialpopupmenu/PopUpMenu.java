package com.gsd.mpm.materialpopupmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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

public class PopUpMenu extends PopupWindows implements OnDismissListener {

    private ImageView mArrowUp, mArrowDown;
    private Animation mTrackAnim;
    private LayoutInflater inflater;
    private ViewGroup mTrack;
    private OnActionItemClickListener mItemClickListener;
    private OnDismissListener mDismissListener;

    private List<ActionItem> mActionItemList = new ArrayList<>();

    private boolean mDidAction;
    private boolean mAnimateTrack;
    private boolean isLight;
    private boolean isEnabled;

    //Eventually we will get to pragmatically set colors
    //If I can get it to tint properly
    private int scrollColor;
    private int scrimColor;
    private int bodyColor;
    private int trackColor;

    private int mChildPos;
    private int mAnimStyle;

    private static final int ANIM_GROW_FROM_LEFT = 1;
    private static final int ANIM_GROW_FROM_RIGHT = 2;
    private static final int ANIM_GROW_FROM_CENTER = 3;
    private static final int ANIM_AUTO = 4;

    private ActionItem getActionItem(int index) {
        return mActionItemList.get(index);
    }

    public PopUpMenu(Context context) {
        super(context);
        inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTrackAnim 	= AnimationUtils.loadAnimation(context, R.anim.rail);
        mTrackAnim.setInterpolator(new Interpolator() {
            public float getInterpolation(float t) {
                final float inner = (t * 1.55f) - 1.1f;
                return 1.2f - inner * inner;
            }
        });

        setRootViewId(R.layout.quick_action_menu);

        isLight = true;
        mAnimStyle = ANIM_AUTO;
        mAnimateTrack = true;
        mChildPos = 0;
    }

    private void setRootViewId(int id) {
        mRootView = inflater.inflate(id, null);
        mTrack = mRootView.findViewById(R.id.tracks);

        //Popup Arrows
        mArrowDown = mRootView.findViewById(R.id.arrow_down);
        mArrowUp = mRootView.findViewById(R.id.arrow_up);

        //Popup Menu Body
        FrameLayout header = mRootView.findViewById(R.id.header2);
        FrameLayout footer = mRootView.findViewById(R.id.footer);

        //StartHeader
        header.setBackgroundResource(R.drawable.qa_round_top);
        Drawable drawableHeader = header.getBackground();
            drawableHeader.setBounds(300, 300, 300, 300);
        header.setBackground(drawableHeader);
        //EndHeader

        //StartFooter
        footer.setBackgroundResource(R.drawable.qa_round_bottom);
        Drawable drawableFooter = footer.getBackground();
            drawableFooter.setBounds(300, 300, 300, 300);
        footer.setBackground(drawableFooter);
        //EndFooter

        //Popup Track
        //To disable them, just keep them the same color as the scroll color
        View startTrack = mRootView.findViewById(R.id.start_track);
        View endTrack = mRootView.findViewById(R.id.end_track);

        //Popup Background
        HorizontalScrollView scroll = mRootView.findViewById(R.id.scroll);
        scroll.setBackgroundColor(mContext.getColor(R.color.popup_scroll_color));
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
        int[] location 	= new int[2];
        mDidAction 	= false;
        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
                + anchor.getHeight());

        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int rootWidth 		= mRootView.getMeasuredWidth();
        int rootHeight 		= mRootView.getMeasuredHeight();
        int screenWidth 	= mWindowManager.getDefaultDisplay().getWidth();
        int xPos 			= (screenWidth - rootWidth) / 2;
        int yPos	 		= anchorRect.top - rootHeight;
        boolean onTop		= true;
        if (rootHeight > anchor.getTop()) {
            yPos 	= anchorRect.bottom;
            onTop	= false;
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
