package com.vladimirkondenko.yamblz.utils.ui;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public final class AnimUtils {

    /**
     * Animates a view in and out to show a "swapping"-styled change.
     *
     * 1. Move the view up or down with fade in/out animation
     * 2. Do something, like change the text while the view's invisible
     * 2. Return the view to its original place
     *
     * @param view the view to be animated
     * @param moveDown whether move the view up (and then down) or otherwise
     * @param distance the distance to move the view for
     * @param duration the duration of a half of the animation
     * @param startAction something to do before the animation starts
     * @param endAction something to do after the animation ends
     */
    public static void slideInAndOut(View view, boolean moveDown, int distance, int duration, Runnable startAction, Runnable endAction) {
        slideInAndOut(view, false, moveDown, distance, duration, startAction, endAction);
    }

    private static void slideInAndOut(View view, boolean in, boolean moveDown, int distance, int duration, Runnable startAction, Runnable endAction) {
        view.animate()
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withStartAction(startAction)
                .alpha(in ? 1f : 0f)
                // Move the view to the top or from the bottom from its original place to animate it later
                .y(moveDown ? view.getTop() - distance : view.getBottom() + distance)
                .translationYBy(moveDown ? distance : -distance)
                .withEndAction(() -> {
                    endAction.run();
                    // The out animation have been played and now the in animation should be executed
                    if (!in) slideInAndOut(view, true, !moveDown, distance, duration, () -> {}, () -> {});
                });
    }

}
