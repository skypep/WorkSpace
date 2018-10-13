package com.android.camera.module.ui;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.camera.AGlobalConfig;
import com.chus.camera.R;

/**
 * Created by THINK on 2017/6/15.
 */

public class SlideTab1 extends FrameLayout {
    final static String TAG = "SlideTab1";

    private static final int fixed_bottom_margin = 5;
    private static final int fixed_gap_between = 80; // frankie, 2018.01.08, 40;

    private Context mContext;
    private int mIndex = 0;
    TextView text01;
    TextView text02;
    TextView text03;
    TextView text04;
    private TextView[] mAllViews = new TextView[4];
    private Rect[] mAllRects = new Rect[4];
    private Point[] mAllSizes = new Point[4];
    private Rect ourOwnRect = new Rect();

    private int[] tab_name_str_ids = new int[] {R.string.my_text01, R.string.my_text02, R.string.my_text03, R.string.my_text04};
    private int[] tab_id_maps = new int[] {0, 1, 2, 3};
    private int[] tab_name_str_ids_1 = new int[] {R.string.my_text01, R.string.my_text04, R.string.my_text02, R.string.my_text03};
    private int[] tab_id_maps_1 = new int[] {0, 3, 1, 2};

    int getIndexBy_res_id(int res_id) {
        if(res_id == R.id.text01) { return tab_id_maps[0];}
        else if(res_id == R.id.text02) { return tab_id_maps[1];}
        else if(res_id == R.id.text03) { return tab_id_maps[2];}
        else if(res_id == R.id.text04) { return tab_id_maps[3];}
        else {return -1;}
    }
    private Listener mListener = null;
    public static interface Listener {
        public void onTextSelected(int index);
    }

    public SlideTab1(Context context) {
        super(context);
        initialize_(context);
    }

    public SlideTab1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize_(context);
    }

    public SlideTab1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize_(context);
    }

    public SlideTab1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize_(context);
    }
    private void initialize_(Context context) {
        mContext = context;
		if(AGlobalConfig.config_slidetab_reorder_en) {
	        tab_name_str_ids = tab_name_str_ids_1;
	        tab_id_maps = tab_id_maps_1;
		}
    }

    private void init() {
        text01 = (TextView)this.findViewById(R.id.text01);
        text01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "text01", Toast.LENGTH_SHORT).show();
                if(mListener!=null) {mListener.onTextSelected(getIndexBy_res_id(R.id.text01));}
            }
        });
        text02 = (TextView)this.findViewById(R.id.text02);
        text02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "text02", Toast.LENGTH_SHORT).show();
                if(mListener!=null) {mListener.onTextSelected(getIndexBy_res_id(R.id.text02));}
            }
        });
        text03 = (TextView)this.findViewById(R.id.text03);
        text03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "text03", Toast.LENGTH_SHORT).show();
                if(mListener!=null) {mListener.onTextSelected(getIndexBy_res_id(R.id.text03));}
            }
        });
        text04 = (TextView)this.findViewById(R.id.text04);
        text04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "text04", Toast.LENGTH_SHORT).show();
                if(mListener!=null) {mListener.onTextSelected(getIndexBy_res_id(R.id.text04));}
            }
        });
        mAllViews[0] = text01;
        mAllRects[0] = new Rect();
        mAllSizes[0] = new Point();
        mAllViews[1] = text02;
        mAllRects[1] = new Rect();
        mAllSizes[1] = new Point();
        mAllViews[2] = text03;
        mAllRects[2] = new Rect();
        mAllSizes[2] = new Point();
        mAllViews[3] = text04;
        mAllRects[3] = new Rect();
        mAllSizes[3] = new Point();

        for(int i=0;i<tab_name_str_ids.length;i++) {
            mAllViews[i].setText(tab_name_str_ids[i]);
        }
        ourOwnRect.left = -1;
    }
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();

        if(AGlobalConfig.config_disable_scan_module_en) {
            mAllViews[0].setVisibility(View.INVISIBLE);
        } else {
            mAllViews[0].setVisibility(View.VISIBLE);
        }
        mAllViews[1].setVisibility(View.VISIBLE);
        mAllViews[2].setVisibility(View.VISIBLE);
        mAllViews[3].setVisibility(View.VISIBLE);
    }
    public void setListener(Listener listener) {
        mListener = listener;
    }

	private int[] module_index_2_index_map = new int[]{
			1, // PHOTO_MODULE_INDEX
			2, // VIDEO_MODULE_INDEX
			3, // WIDE_ANGLE_PANO_MODULE_INDEX
			-1, // LIGHTCYCLE_MODULE_INDEX
			-1, // GCAM_MODULE_INDEX
			-1, // CAPTURE_MODULE_INDEX
			-1, // 
			-1, // 
			-1, // 
			-1, // 
			0, // SCAN_MODULE_INDEX
	};
	private int[] module_index_2_index_map_1 = new int[]{
			2, // PHOTO_MODULE_INDEX
			3, // VIDEO_MODULE_INDEX
			1, // WIDE_ANGLE_PANO_MODULE_INDEX
			-1, // LIGHTCYCLE_MODULE_INDEX
			-1, // GCAM_MODULE_INDEX
			-1, // CAPTURE_MODULE_INDEX
			-1, // 
			-1, // 
			-1, // 
			-1, // 
			0, // SCAN_MODULE_INDEX // liujia fixed
	};
    public void setIndex(int index) {
		if(AGlobalConfig.config_slidetab_reorder_en) {
			module_index_2_index_map = module_index_2_index_map_1;
		}
        int i = module_index_2_index_map[index];
		if(i >= 0) {
	        mIndex = i;
	        //ourOwnRect.left = -1; // normally , this size will not changed !!!
	        this.invalidate();
	        this.requestLayout();
		}
    }


    private void setViewSize(int id, int width, int height) {
        if(id == R.id.text01) {
            mAllSizes[0].x = width; mAllSizes[0].y = height;
        } else if(id == R.id.text02) {
            mAllSizes[1].x = width; mAllSizes[1].y = height;
        } else if(id == R.id.text03) {
            mAllSizes[2].x = width; mAllSizes[2].y = height;
        } else if(id == R.id.text03) {
            mAllSizes[4].x = width; mAllSizes[3].y = height;
        }
    }
    private void center_(int index, int l, int t, int r, int b, boolean updateFlag) {
        Log.v(TAG, "center_ :" + index);
        View v = mAllViews[index];

        if(v instanceof TextView) {
            TextView textView = (TextView)v;
            textView.setTextColor(0xd0ffcc00);//0xd0f0f0f0
        }
        Rect result = new Rect();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        int tw = lp.width;
        int th = lp.height;
        tw = v.getMeasuredWidth();
        th = v.getMeasuredHeight();
        Log.v(TAG, "center_ tw:" + tw + ",th:" + th);
        result.left = (r + l) / 2 - tw / 2;
        result.right = (r + l) / 2 + tw / 2;
        result.bottom = b - fixed_bottom_margin;
        result.top = b - th - fixed_bottom_margin;
        if(updateFlag) {
            v.layout(result.left, result.top, result.right, result.bottom);
        }
        mAllRects[index] = result;
    }
    // curIndex to left of lastIndex
    private void toLeftOf(int curIndex, int lastIndex, boolean updateFlag) {
        Log.v(TAG, "toLeftOf :" + curIndex + " lastIndex:" + lastIndex);
        View v = mAllViews[curIndex];
        if(v instanceof TextView) {
            TextView textView = (TextView)v;
            textView.setTextColor(0xffffffff);//0xd0a0a0a0
        }
        Rect result = new Rect();
        int tw = v.getMeasuredWidth();
        int th = v.getMeasuredHeight();
        Log.v(TAG, "toLeftOf tw:" + tw + ",th:" + th);
        result.right = mAllRects[lastIndex].left - fixed_gap_between;
        result.left = result.right - tw;
        result.bottom = mAllRects[lastIndex].bottom;
        result.top = mAllRects[lastIndex].top;
        if(updateFlag) {
            v.layout(result.left, result.top, result.right, result.bottom);
        }
        mAllRects[curIndex] = result;
    }
    // curIndex to right of lastIndex
    private void toRightOf(int curIndex, int lastIndex, boolean updateFlag) {
        Log.v(TAG, "toRightOf :" + curIndex + " lastIndex:" + lastIndex);
        View v = mAllViews[curIndex];
        if(v instanceof TextView) {
            TextView textView = (TextView)v;
            textView.setTextColor(0xffffffff);
        }
        Rect result = new Rect();
        int tw = v.getMeasuredWidth();
        int th = v.getMeasuredHeight();
        Log.v(TAG, "toRightOf tw:" + tw + ",th:" + th);
        result.left = mAllRects[lastIndex].right + fixed_gap_between;
        result.right = result.left + tw;
        result.bottom = mAllRects[lastIndex].bottom;
        result.top = mAllRects[lastIndex].top;
        if(updateFlag) {
            v.layout(result.left, result.top, result.right, result.bottom);
        }
        mAllRects[curIndex] = result;
    }
    private void position_(boolean updateFlag) {
        Log.v(TAG, "position_ : " + mIndex);
        if(mIndex >= 0 && mIndex <= 3) {

            center_(mIndex, ourOwnRect.left, ourOwnRect.top, ourOwnRect.right, ourOwnRect.bottom, updateFlag);
            // to left
            int curIndex = mIndex-1;
            int lastIndex = mIndex;
            while(curIndex >= 0) {
                toLeftOf(curIndex, lastIndex, updateFlag);
                lastIndex = curIndex;
                curIndex--;
            }
            // to right
            curIndex = mIndex + 1;
            lastIndex = mIndex;
            while(curIndex <= 3) {
                toRightOf(curIndex, lastIndex, updateFlag);
                lastIndex = curIndex;
                curIndex++;
            }
        }
    }
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v(TAG, "onLayout,mIndex:" + mIndex + " l:" + l + " t:" + t + " r:" + r + " b:" + b);
        //super.onLayout(changed, l, t, r, b);

        r = r - l;
        b = b - t;
        l = 0;
        t = 0;
        int childCounts = this.getChildCount();
        for (int i = 0; i < childCounts; i++) { // pre-calculate
            View v = getChildAt(i);
            v.layout(l, t, r, b);
        }
        for(int i=0;i<childCounts;i++) {
            View v = getChildAt(i);
            setViewSize(v.getId(), v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        //if(ourOwnRect.left < 0)
        {
            ourOwnRect.left = l;
            ourOwnRect.right = r;
            ourOwnRect.bottom = b;
            ourOwnRect.top = t;
        }
        position_(true);
        //print_();
    }

}
