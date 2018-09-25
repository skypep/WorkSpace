package com.android.toro.src;

import android.app.Activity;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.dialer.R;
import com.android.dialer.animation.AnimUtils;
import com.android.dialer.common.Assert;

public class ToroDialpadFloatingController {

    public static final int ALIGN_MIDDLE = 0;
    public static final int ALIGN_QUARTER_END = 1;
    public static final int ALIGN_END = 2;

    private static final int FAB_SCALE_IN_DURATION = 266;
    private static final int FAB_SCALE_IN_FADE_IN_DELAY = 100;
    private static final int FAB_ICON_FADE_OUT_DURATION = 66;

    private final int mAnimationDuration;
    private final int mFloatingActionButtonWidth;
    private final int mFloatingActionButtonMarginRight;
    private final LinearLayout floatingLayout;
    private final ImageView mCall;
    private final ImageView mBack;
    private final ImageView mContact;
    private final Interpolator mFabInterpolator;
    private int mScreenWidth;

    public ToroDialpadFloatingController(Activity activity, LinearLayout layout) {
        Resources resources = activity.getResources();
        mFabInterpolator =
                AnimationUtils.loadInterpolator(activity, android.R.interpolator.fast_out_slow_in);
        mFloatingActionButtonWidth =
                resources.getDimensionPixelSize(R.dimen.floating_action_button_width);
        mFloatingActionButtonMarginRight =
                resources.getDimensionPixelOffset(R.dimen.floating_action_button_margin_right);
        mAnimationDuration = resources.getInteger(R.integer.floating_action_button_animation_duration);
        floatingLayout = layout;
        mCall = layout.findViewById(R.id.toro_dialpad_floating_action_call);
        mContact = layout.findViewById(R.id.toro_dialpad_floating_action_contact);
        mBack = layout.findViewById(R.id.toro_dialpad_floating_action_back);
    }

    public void setOnclickListener(View.OnClickListener listener) {
        mCall.setOnClickListener(listener);
        mContact.setOnClickListener(listener);
        mBack.setOnClickListener(listener);
    }

    /**
     * Passes the screen width into the class. Necessary for translation calculations. Should be
     * called as soon as parent View width is available.
     *
     * @param screenWidth The width of the screen in pixels.
     */
    public void setScreenWidth(int screenWidth) {
        mScreenWidth = screenWidth;
    }

    public boolean isVisible() {
        return floatingLayout.getVisibility() == View.VISIBLE;
    }

    /**
     * Sets FAB as shown or hidden.
     *
     * @param visible Whether or not to make the container visible.
     */
    public void setVisible(boolean visible) {
        if (visible) {
            floatingLayout.setVisibility(View.VISIBLE);
        } else {
            floatingLayout.setVisibility(View.GONE);
        }
    }

//  public void changeIcon(Drawable icon, String description) {
//    if (mFab.getDrawable() != icon || !mFab.getContentDescription().equals(description)) {
//      mFab.setImageDrawable(icon);
//      mFab.setContentDescription(description);
//    }
//  }

    /**
     * Updates the FAB location (middle to right position) as the PageView scrolls.
     *
     * @param positionOffset A fraction used to calculate position of the FAB during page scroll.
     */
    public void onPageScrolled(float positionOffset) {
        // As the page is scrolling, if we're on the first tab, update the FAB position so it
        // moves along with it.
        floatingLayout.setTranslationX(positionOffset * getTranslationXForAlignment(ALIGN_END));
    }

    /**
     * Aligns the FAB to the described location
     *
     * @param align One of ALIGN_MIDDLE, ALIGN_QUARTER_RIGHT, or ALIGN_RIGHT.
     * @param animate Whether or not to animate the transition.
     */
    public void align(int align, boolean animate) {
//    align(align, 0 /*offsetX */, 0 /* offsetY */, animate);
    }

    /**
     * Aligns the FAB to the described location plus specified additional offsets.
     *
     * @param align One of ALIGN_MIDDLE, ALIGN_QUARTER_RIGHT, or ALIGN_RIGHT.
     * @param offsetX Additional offsetX to translate by.
     * @param offsetY Additional offsetY to translate by.
     * @param animate Whether or not to animate the transition.
     */
    private void align(int align, int offsetX, int offsetY, boolean animate) {
//    if (mScreenWidth == 0) {
//      return;
//    }
//
//    int translationX = getTranslationXForAlignment(align);
//
//    // Skip animation if container is not shown; animation causes container to show again.
//    if (animate && mFab.isShown()) {
//      mFab.animate()
//          .translationX(translationX + offsetX)
//          .translationY(offsetY)
//          .setInterpolator(mFabInterpolator)
//          .setDuration(mAnimationDuration)
//          .start();
//    } else {
//      mFab.setTranslationX(translationX + offsetX);
//      mFab.setTranslationY(offsetY);
//    }
    }

    /**
     * Scales the floating action button from no height and width to its actual dimensions. This is an
     * animation for showing the floating action button.
     *
     * @param delayMs The delay for the effect, in milliseconds.
     */
    public void scaleIn(int delayMs) {
        setVisible(true);
        AnimUtils.scaleIn(floatingLayout, FAB_SCALE_IN_DURATION, delayMs);
        AnimUtils.fadeIn(floatingLayout, FAB_SCALE_IN_DURATION, delayMs + FAB_SCALE_IN_FADE_IN_DELAY, null);
    }

    /**
     * Scales the floating action button from its actual dimensions to no height and width. This is an
     * animation for hiding the floating action button.
     */
    public void scaleOut() {
        AnimUtils.scaleOut(floatingLayout, mAnimationDuration);
        // Fade out the icon faster than the scale out animation, so that the icon scaling is less
        // obvious. We don't want it to scale, but the resizing the container is not as performant.
        AnimUtils.fadeOut(floatingLayout, FAB_ICON_FADE_OUT_DURATION, null);
    }

    /**
     * Calculates the X offset of the FAB to the given alignment, adjusted for whether or not the view
     * is in RTL mode.
     *
     * @param align One of ALIGN_MIDDLE, ALIGN_QUARTER_RIGHT, or ALIGN_RIGHT.
     * @return The translationX for the given alignment.
     */
    private int getTranslationXForAlignment(int align) {
        int result;
        switch (align) {
            case ALIGN_MIDDLE:
                // Moves the FAB to exactly center screen.
                return 0;
            case ALIGN_QUARTER_END:
                // Moves the FAB a quarter of the screen width.
                result = mScreenWidth / 4;
                break;
            case ALIGN_END:
                // Moves the FAB half the screen width. Same as aligning right with a marginRight.
                result =
                        mScreenWidth / 2 - mFloatingActionButtonWidth / 2 - mFloatingActionButtonMarginRight;
                break;
            default:
                throw Assert.createIllegalStateFailException("Invalid alignment value: " + align);
        }
        if (isLayoutRtl()) {
            result *= -1;
        }
        return result;
    }

    private boolean isLayoutRtl() {
        return floatingLayout.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

}
