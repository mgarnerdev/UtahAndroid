package com.utahandroid.april.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.utahandroid.april.UtahAndroidApplication;

import java.lang.ref.WeakReference;

/**
 * Created by mgarner on 4/12/2016.
 * Generic helper/utility methods considered 'app-wide'
 */
@SuppressWarnings("unused")
public class Utils {

    /**
     * Translates dp values into pixel values based on the phone's pixel density.
     *
     * @param dp Yes, dps.
     * @return int of pixels.
     */
    public static int dpToPx(int dp) {
        return Math.round((float) dp * UtahAndroidApplication.mPixelDensity);
    }

    /**
     * Sets up an alpha animation to fade in or out a view.
     *
     * @param duration     How long should this animation last?
     * @param start        The starting alpha value. 0.0f to fade in. 1.0f to fade out.
     * @param end          The ending alpha value. 1.0f to fade in. 0.0f to fade out.
     * @param animatedView The view to animate.
     * @return AlphaAnimation that pleases the eye.
     */
    public static AlphaAnimation setupFadeAnimation(int duration, float start, float end, final View animatedView) {
        final AlphaAnimation fade = new AlphaAnimation(start, end);
        fade.setDuration(duration);
        if (start < end) {
            fade.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (animatedView != null) {
                        animatedView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            fade.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (animatedView != null) {
                        animatedView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return fade;
    }

    /**
     * Fades in a view using an alpha animation.
     *
     * @param viewWeakReference The view as a weak reference to fade.
     */
    public static void fadeInView(final WeakReference<View> viewWeakReference) {
        final AlphaAnimation fade = new AlphaAnimation(0.0f, 1.0f);
        fade.setDuration(500);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (viewWeakReference != null) {
                    viewWeakReference.get().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (viewWeakReference != null) {
            viewWeakReference.get().startAnimation(fade);
        }
    }

    /**
     * Fades out a view using an alpha animation.
     *
     * @param viewWeakReference The view as a weak reference to fade.
     */
    public static void fadeOutView(final WeakReference<View> viewWeakReference) {
        final AlphaAnimation fade = new AlphaAnimation(1.0f, 0.0f);
        fade.setDuration(300);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (viewWeakReference != null) {
                    viewWeakReference.get().setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (viewWeakReference != null) {
            viewWeakReference.get().startAnimation(fade);
        }
    }
}
