package com.your.project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class ViewAnimation {

    static public boolean animationInProgress = false;
    static private int defaultOffsetHeight = 4;
    static private int defaultOffsetWidth = 2;
    static private int defaultDuration = 220;
    static private int animateOffsetHeight = defaultOffsetHeight; // what fraction of view to offset for animation
    static private int animateOffsetWidth = defaultOffsetWidth; // what fraction of view to offset for animation
    static private int animateDuration = defaultDuration; // how long to animate

    /**
     * Check if animation is in progress.
     * @return <b>true</b> or <b>false</b>
     */
    public static boolean isAnimationInProgress() {
        return animationInProgress;
    }

    private static void setAnimationInProgress(boolean animationInProgress) {
        ViewAnimation.animationInProgress = animationInProgress;
    }

    /**
     * Set animation <b>height</b> offset.  Default value is <b>{@value #defaultOffsetHeight}</b>.
     * @param animateOffsetHeight what fraction of view to offset for vertical animations
     */
    public static void setAnimateOffsetHeight(int animateOffsetHeight) {
        ViewAnimation.animateOffsetHeight = animateOffsetHeight;
    }

    /**
     * Set animation <b>width</b> offset.  Default value is <b>{@value #defaultOffsetWidth}</b>.
     * @param animateOffsetWidth what fraction of view to offset for horizontal animations
     */
    public static void setAnimateOffsetWidth(int animateOffsetWidth) {
        ViewAnimation.animateOffsetWidth = animateOffsetWidth;
    }

    /**
     * Set animation <b>duration</b>.  Default value is <b>{@value #defaultDuration}</b> milliseconds.
     * @param animateDuration how long to animate in milliseconds
     */
    public static void setAnimateDuration(int animateDuration) {
        ViewAnimation.animateDuration = animateDuration;
    }

    /**
     * Reset animation height offset to the default value of <b>{@value #defaultOffsetHeight}</b>.
     */
    public static void resetAnimateOffsetHeight() {
        ViewAnimation.animateOffsetHeight = defaultOffsetHeight;
    }

    /**
     * Reset animation width offset to the default value of <b>{@value #defaultOffsetWidth}</b>.
     */
    public static void resetAnimateOffsetWidth() {
        ViewAnimation.animateOffsetWidth = defaultOffsetWidth;
    }

    /**
     * Reset animation duration to the default value of <b>{@value #defaultDuration}</b> milliseconds.
     */
    public static void resetAnimateDuration() {
        ViewAnimation.animateDuration = defaultDuration;
    }

    /**
     * Set view location to <b>center</b> and hide to prepare for {@link #slideToCenter(View, SimpleCallback) slideToCenter} animation.
     * @param view view to animate
     */
    static public void setCenter(View view){
        view.setX(0);
        view.setY(0);
        view.setAlpha(0.0f);
        view.setVisibility(View.GONE);
    }

    /**
     * Offset view location to <b>top</b> and hide to prepare for {@link #slideToCenter(View, SimpleCallback) slideToCenter} animation.
     * @param view view to animate
     */
    static public void setOffsetTop(View view){
        view.setY(-(view.getHeight()/animateOffsetHeight));
        view.setAlpha(0.0f);
        view.setVisibility(View.GONE);
    }

    /**
     * Offset view location to <b>bottom</b> and hide to prepare for {@link #slideToCenter(View, SimpleCallback) slideToCenter} animation.
     * @param view view to animate
     */
    static public void setOffsetBot(View view){
        view.setY(view.getHeight()/animateOffsetHeight);
        view.setAlpha(0.0f);
        view.setVisibility(View.GONE);
    }

    /**
     * Offset view location to <b>left</b> and hide to prepare for {@link #slideToCenter(View, SimpleCallback) slideToCenter} animation.
     * @param view view to animate
     */
    static public void setOffsetLeft(View view){
        view.setX(-(view.getWidth()/animateOffsetWidth));
        view.setAlpha(0.0f);
        view.setVisibility(View.GONE);
    }

    /**
     * Offset view location to <b>right</b> and hide to prepare for {@link #slideToCenter(View, SimpleCallback) slideToCenter} animation.
     * @param view view to animate
     */
    static public void setOffsetRight(View view){
        view.setX(view.getWidth()/animateOffsetWidth);
        view.setAlpha(0.0f);
        view.setVisibility(View.GONE);
    }

    /**
     * Slide view towards the <b>center</b> and fade in.
     * @param view view to animate
     * @param cb callback for animation complete
     */
    static public void slideToCenter(final View view, final SimpleCallback cb){
        view.animate()
                .translationX(0)
                .translationY(0)
                .alpha(1.0f)
                .setDuration(animateDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        view.setVisibility(View.VISIBLE);
                        setAnimationInProgress(true);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                        setAnimationInProgress(true);
                        cb.done();
                    }
                });
    }

    /**
     * * Slide view towards the <b>top</b> and fade out.
     * @param view view to animate
     * @param cb callback for animation complete
     */
    static public void slideOutTop(final View view, final SimpleCallback cb){
        view.animate()
                .translationX(0)
                .translationY(-(view.getHeight()/animateOffsetHeight))
                .alpha(0.0f)
                .setDuration(animateDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setAnimationInProgress(true);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                        setAnimationInProgress(true);
                        cb.done();
                    }
                });
    }

    /**
     * * Slide view towards the <b>bottom</b> and fade out.
     * @param view view to animate
     * @param cb callback for animation complete
     */
    static public void slideOutBot(final View view, final SimpleCallback cb){
        view.animate()
                .translationX(0)
                .translationY(view.getHeight()/animateOffsetHeight)
                .alpha(0.0f)
                .setDuration(animateDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setAnimationInProgress(true);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                        setAnimationInProgress(true);
                        cb.done();
                    }
                });
    }

    /**
     * * Slide view towards the <b>left</b> and fade out.
     * @param view view to animate
     * @param cb callback for animation complete
     */
    static public void slideOutLeft(final View view, final SimpleCallback cb){
        view.animate()
                .translationX(-(view.getWidth()/animateOffsetWidth))
                .translationY(0)
                .alpha(0.0f)
                .setDuration(animateDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setAnimationInProgress(true);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                        setAnimationInProgress(true);
                        cb.done();
                    }
                });
    }

    /**
     * * Slide view towards the <b>right</b> and fade out.
     * @param view view to animate
     * @param cb callback for animation complete
     */
    static public void slideOutRight(final View view, final SimpleCallback cb){
        view.animate()
                .translationX(view.getWidth()/animateOffsetWidth)
                .translationY(0)
                .alpha(0.0f)
                .setDuration(animateDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setAnimationInProgress(true);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                        setAnimationInProgress(false);
                        cb.done();
                    }
                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                        setAnimationInProgress(true);
                        cb.done();
                    }
                });
    }

}
