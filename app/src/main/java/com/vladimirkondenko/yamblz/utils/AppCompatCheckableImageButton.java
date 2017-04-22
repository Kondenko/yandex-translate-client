package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;

/**
 * ImageButton with on and off states.
 *
 * @author hendrawd on 6/23/16
 */
public class AppCompatCheckableImageButton extends android.support.v7.widget.AppCompatImageButton implements Checkable {

    private boolean mChecked = false;

    private CheckedChangeListener checkedChangeListener;

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    public AppCompatCheckableImageButton(Context context) {
        super(context);
    }

    public AppCompatCheckableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
        checkedChangeListener.onCheckedChanged(mChecked);
    }

    public void setOnCheckedChangeListener(CheckedChangeListener listener) {
        checkedChangeListener = listener;
        setOnClickListener((view) -> toggle());
    }

    public interface CheckedChangeListener {
        void onCheckedChanged(boolean checked);
    }

}
