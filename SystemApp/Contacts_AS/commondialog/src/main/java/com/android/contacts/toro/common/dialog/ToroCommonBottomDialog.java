package com.android.contacts.toro.common.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imyyq on 2018/4/14.
 */

public class ToroCommonBottomDialog
        extends BottomSheetDialog
{
    private Context mContext;
    private View mView;
    private MultiItemTypeAdapter<ToroCommonBottomDialogEntity> mAdapter;
    private List<ToroCommonBottomDialogEntity> mDataList;

    private List<ToroCommonBottomDialogEntity> mRealDataList = new ArrayList<>();

    private int mGravity = Gravity.CENTER;

    public ToroCommonBottomDialog(
            @NonNull
                    Context context, int layoutId)
    {
        super(context, R.style.CustomBottomDialog);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null)
        {
            mView = inflater.inflate(layoutId, null);
        }
    }

    public ToroCommonBottomDialog(
            @NonNull
                    Context context, String... list)
    {
        this(context, R.style.CustomBottomDialog, list);
    }

    public ToroCommonBottomDialog(
            @NonNull
                    Context context, int theme, String... list)
    {
        super(context, theme);
        mRealDataList.clear();
        List<ToroCommonBottomDialogEntity> l = new ArrayList<>();
        for (String s : list)
        {
            ToroCommonBottomDialogEntity entity = new ToroCommonBottomDialogEntity();
            entity.setContent(s);
            mRealDataList.add(entity);

            l.add(entity);
            l.add(null);
        }
        ToroCommonBottomDialogEntity entity = new ToroCommonBottomDialogEntity(
                getContext().getString(R.string.toro_cancel));
        l.add(entity);
        init(context, l);
    }

    public ToroCommonBottomDialog(
            @NonNull
                    Context context, ToroCommonBottomDialogEntity... list)
    {
        super(context, R.style.CustomBottomDialog);
        mRealDataList.clear();
        List<ToroCommonBottomDialogEntity> l = new ArrayList<>();
        for (ToroCommonBottomDialogEntity s : list)
        {
            mRealDataList.add(s);

            l.add(s);
            l.add(null);
        }
        ToroCommonBottomDialogEntity entity = new ToroCommonBottomDialogEntity(
                getContext().getString(R.string.toro_cancel));
        l.add(entity);
        init(context, l);
    }

    public void setGravity(int gravity)
    {
        mGravity = gravity;
        mAdapter.notifyDataSetChanged();
    }

    private void init(Context context, List<ToroCommonBottomDialogEntity> list)
    {
        mContext = context;
        mDataList = list;

        RecyclerView recyclerView = new RecyclerView(context);
        mAdapter = new MultiItemTypeAdapter<>(context, mDataList);
        mAdapter.addItemViewDelegate(new ChildItemViewDelegate());
        mAdapter.addItemViewDelegate(new DividerItemViewDelegate());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        mView = recyclerView;
    }

    public void setOnItemClickListener(final OnItemClickListener listener)
    {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                ToroCommonBottomDialogEntity item = mDataList.get(position);
                if (item == null)
                {
                    return;
                }

                if (position == mAdapter.getItemCount() - 1)
                {
                    dismiss();
                }
                else if (listener != null)
                {
                    listener.onItemClick(item.getContent(), mRealDataList.size() + 1,
                            mRealDataList.indexOf(item));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(mView);

        /*
         * 窗体默认包含View，不设置width会压缩View
         */
        if (getWindow() == null)
        {
            return;
        }
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    public interface OnItemClickListener
    {
        void onItemClick(String text, int listSize, int position);
    }

    @NonNull
    public <T extends View> T findViewById(
            @IdRes
                    int viewId)
    {
        return mView.findViewById(viewId);
    }

    private class ChildItemViewDelegate
            implements ItemViewDelegate<ToroCommonBottomDialogEntity>
    {
        @Override
        public int getItemViewLayoutId()
        {
            return R.layout.toro_dlg_common_bottom;
        }

        @Override
        public boolean isForViewType(ToroCommonBottomDialogEntity item, int position)
        {
            return item != null;
        }

        @Override
        public void convert(ViewHolder holder, ToroCommonBottomDialogEntity item, int position)
        {
            TextView tv = holder.getView(R.id.tv);

            tv.setGravity(mGravity);
            tv.setCompoundDrawablesWithIntrinsicBounds(item.getDrawableLeft(), 0, 0, 0);

            if (mGravity == Gravity.CENTER)
            {
                tv.setCompoundDrawablePadding(0);
            }
            else if (mGravity == Gravity.START || mGravity == Gravity.LEFT)
            {
                tv.setCompoundDrawablePadding(
                        (int) mContext.getResources().getDimension(R.dimen.toro_common_panding));
                int leftMargin = (int) mContext.getResources().getDimension(R.dimen.toro_common_panding);
                tv.setPadding(leftMargin, tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());
            }

            tv.setTextColor(item.getColor());

            tv.setText(item.getContent());

            if (item.getContent().equals(getContext().getString(R.string.toro_cancel)))
            {
                tv.setBackgroundResource(R.drawable.toro_common_dlg_item_bottom_bg);
                tv.setPadding(0, tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());
                tv.setCompoundDrawablePadding(0);
                tv.setGravity(Gravity.CENTER);
            }
            else if (position == 0)
            {
                tv.setBackgroundResource(R.drawable.toro_common_dlg_item_top_bg);
            }
            else
            {
                tv.setBackgroundColor(Color.WHITE);
            }
        }
    }

    private static class DividerItemViewDelegate
            implements ItemViewDelegate<ToroCommonBottomDialogEntity>
    {
        @Override
        public int getItemViewLayoutId()
        {
            return R.layout.toro_common_horizontal_line;
        }

        @Override
        public boolean isForViewType(ToroCommonBottomDialogEntity item, int position)
        {
            return item == null;
        }

        @Override
        public void convert(ViewHolder holder, ToroCommonBottomDialogEntity o, int position)
        {
        }
    }
}
