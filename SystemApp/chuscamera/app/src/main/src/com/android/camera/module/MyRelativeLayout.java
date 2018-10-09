package com.android.camera.module;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chus.camera.R;
import com.cs.camera.magicfilter.filter.FilterType;

/**
 * Created by THINK on 2017/8/18.
 */

public class MyRelativeLayout extends RelativeLayout {
    final static String TAG = "MyRelativeLayout";
    private float mAspectRatio = 16.0f/9.0f; // 4.0f/3.0f; // 16.0f/9.0f;
    private FilterType mFilterType = FilterType.NONE;
    private TextView mFilterName = null;
	private MyBackground mMyBackground = null;
    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        Log.v(TAG, "onFinishInflate");
        initilize_();
    }
    private void initilize_() {
        mFilterName = (TextView)this.findViewById(R.id.color_effect_name);
		mMyBackground = (MyBackground)this.findViewById(R.id.color_effect_selected);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        height = (int)(width * mAspectRatio);

        super.onMeasure(MeasureSpec.makeMeasureSpec(width, width_mode), MeasureSpec.makeMeasureSpec(height, height_mode));

//        Log.v(TAG, "onMeasure, width/heightMeasureSpec:" + width + " x " + height + " mAspectRatio:" + mAspectRatio
//                //+ " /"  + width + "x" + (int)(width * mAspectRatio)
//        );
        //setMeasuredDimension(width, (int)(width * mAspectRatio));
    }
	public void setSelected(boolean selected) {
		if(mMyBackground != null) {
			mMyBackground.setSelected(selected);
		}
	}
    public void setFilterType(FilterType type) {
        mFilterType = type;
        Log.v(TAG, "setFilterType: " + type.name() + " mFilterName" + (mFilterName!=null ? "!=null" : "==null"));
        if(mFilterName != null) {
            mFilterName.setText(type.getNameId());
        }
    }
    public void setmAspectRatio_ext(float ratio) {
        if(ratio != mAspectRatio) {
            mAspectRatio = ratio;
            this.requestLayout();
            this.invalidate();
        }
    }
}
