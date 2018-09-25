package com.android.toro.src;

import android.animation.ValueAnimator;
import android.os.Bundle;

import com.android.dialer.animation.AnimUtils;
import com.android.dialer.app.widget.ActionBarController;
import com.android.dialer.app.widget.SearchEditTextLayout;
import com.android.dialer.common.LogUtil;

public class ToroActionBarController {

    private static final String KEY_IS_SLID_UP = "key_actionbar_is_slid_up";
    private static final String KEY_IS_FADED_OUT = "key_actionbar_is_faded_out";
    private static final String KEY_IS_EXPANDED = "key_actionbar_is_expanded";

    private ActionBarController.ActivityUi mActivityUi;
    private ToroActionBar mToroActionBar;

    private boolean mIsActionBarSlidUp;

    private final AnimUtils.AnimationCallback mFadeOutCallback =
            new AnimUtils.AnimationCallback() {
                @Override
                public void onAnimationEnd() {
                    slideActionBar(true /* slideUp */, false /* animate */);
                }

                @Override
                public void onAnimationCancel() {
                    slideActionBar(true /* slideUp */, false /* animate */);
                }
            };

    private final AnimUtils.AnimationCallback mFadeInCallback =
            new AnimUtils.AnimationCallback() {
                @Override
                public void onAnimationEnd() {
                    slideActionBar(false /* slideUp */, false /* animate */);
                }

                @Override
                public void onAnimationCancel() {
                    slideActionBar(false /* slideUp */, false /* animate */);
                }
            };
    private ValueAnimator mAnimator;

    public ToroActionBarController(ActionBarController.ActivityUi activityUi, ToroActionBar actionBar) {
        mActivityUi = activityUi;
        mToroActionBar = actionBar;
    }

    /** @return Whether or not the action bar is currently showing (both slid down and visible) */
    public boolean isActionBarShowing() {
        return !mIsActionBarSlidUp && !mToroActionBar.isFadedOut();
    }


    /** Called when search UI has been exited for some reason. */
    public void onSearchUiExited() {
        if (mToroActionBar.isFadedOut()) {
            mToroActionBar.fadeIn();
        }

        slideActionBar(false /* slideUp */, false /* animate */);
    }

    /**
     * Called to indicate that the user is trying to hide the dialpad. Should be called before any
     * state changes have actually occurred.
     */
    public void onDialpadDown() {
        if (mActivityUi.isInSearchUi()) {
            if (mActivityUi.hasSearchQuery()) {
                if (mToroActionBar.isFadedOut()) {
                    mToroActionBar.setVisible(true);
                }
                slideActionBar(false /* slideUp */, true /* animate */);
            } else {
                mToroActionBar.fadeIn(mFadeInCallback);
            }
        }
    }

    /**
     * Called to indicate that the user is trying to show the dialpad. Should be called before any
     * state changes have actually occurred.
     */
    public void onDialpadUp() {
        LogUtil.d("ActionBarController.onDialpadUp", "isInSearchUi " + mActivityUi.isInSearchUi());
        if (mActivityUi.isInSearchUi()) {
            slideActionBar(true /* slideUp */, true /* animate */);
        } else {
            // From the lists fragment
            mToroActionBar.fadeOut(mFadeOutCallback);
        }
    }

    public void slideActionBar(boolean slideUp, boolean animate) {
        LogUtil.d("ActionBarController.slidingActionBar", "up: %b, animate: %b", slideUp, animate);

        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
            mAnimator.removeAllUpdateListeners();
        }
        if (animate) {
            mAnimator = slideUp ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
            mAnimator.addUpdateListener(
                    animation -> {
                        final float value = (float) animation.getAnimatedValue();
                        setHideOffset((int) (mActivityUi.getActionBarHeight() * value));
                    });
            mAnimator.start();
        } else {
            setHideOffset(slideUp ? mActivityUi.getActionBarHeight() : 0);
        }
        mIsActionBarSlidUp = slideUp;
    }

    public void setAlpha(float alphaValue) {
        mToroActionBar.animate().alpha(alphaValue).start();
    }

    private void setHideOffset(int offset) {
        mActivityUi.setActionBarHideOffset(offset);
    }

    /** Saves the current state of the action bar into a provided {@link Bundle} */
    public void saveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_IS_SLID_UP, mIsActionBarSlidUp);
        outState.putBoolean(KEY_IS_FADED_OUT, mToroActionBar.isFadedOut());
    }

    /** Restores the action bar state from a provided {@link Bundle}. */
    public void restoreInstanceState(Bundle inState) {
        mIsActionBarSlidUp = inState.getBoolean(KEY_IS_SLID_UP);

        final boolean isSearchBoxFadedOut = inState.getBoolean(KEY_IS_FADED_OUT);
        if (isSearchBoxFadedOut) {
            if (!mToroActionBar.isFadedOut()) {
                mToroActionBar.setVisible(false);
            }
        } else if (mToroActionBar.isFadedOut()) {
            mToroActionBar.setVisible(true);
        }

        final boolean isSearchBoxExpanded = inState.getBoolean(KEY_IS_EXPANDED);
    }

    /**
     * This should be called after onCreateOptionsMenu has been called, when the actionbar has been
     * laid out and actually has a height.
     */
    public void restoreActionBarOffset() {
        slideActionBar(mIsActionBarSlidUp /* slideUp */, false /* animate */);
    }

    public interface ActivityUi {

        boolean isInSearchUi();

        boolean hasSearchQuery();

        int getActionBarHeight();

        void setActionBarHideOffset(int offset);
    }
}
