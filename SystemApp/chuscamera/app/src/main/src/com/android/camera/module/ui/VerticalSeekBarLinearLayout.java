package com.android.camera.module.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chus.camera.R;

/**
 * Created by THINK on 2017/9/7.
 */

public class VerticalSeekBarLinearLayout extends LinearLayout implements VerticalSeekBar3.OnSeekBarChangeListener {

    private VerticalSeekBar3 mSeekBar3 = null;
    private TextView mProgressText = null;
    private VerticalSeekBar3.OnSeekBarChangeListener mExtOnSeekBarChangeListener = null;

    public void setChangeListener__(VerticalSeekBar3.OnSeekBarChangeListener listener) {
        mExtOnSeekBarChangeListener = listener;
    }
    public VerticalSeekBarLinearLayout(Context context) {
        super(context);
    }
    public VerticalSeekBarLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public VerticalSeekBarLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public VerticalSeekBarLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        mSeekBar3 = (VerticalSeekBar3)this.findViewById(R.id.vertical_seek_bar_3);
        mSeekBar3.setOnSeekBarChangeListener(this);
        mProgressText = (TextView)this.findViewById(R.id.vertical_seek_bar_percent_text);

        updateProgressText();
    }
    public void setProgress(int progress) {
        mSeekBar3.setProgress(progress);
    }
    private void updateProgressText() {
        int max = mSeekBar3.getMax();
        int progress = mSeekBar3.getProgress();
        int percent = (progress*100)/max;
        mProgressText.setText("" + percent + "%");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateProgressText();
        if(mExtOnSeekBarChangeListener != null) {
            mExtOnSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(mExtOnSeekBarChangeListener != null) {
            mExtOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
        }
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(mExtOnSeekBarChangeListener != null) {
            mExtOnSeekBarChangeListener.onStopTrackingTouch(seekBar);
        }
    }


}
