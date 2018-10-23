package com.toro.helper.fragment.photo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.toro.helper.R;
import com.toro.helper.modle.photo.PhotoData;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PhotoData> photoDatas;
    private Context mContext;
    private RecyclerView.RecycledViewPool viewPool; // 优化嵌套recycleView 性能

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new PhotoViewHolder(inflater.inflate(R.layout.photo_data, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PhotoViewHolder photoHolder = (PhotoViewHolder) viewHolder;
        photoHolder.setViewPool(viewPool);
        photoHolder.init(photoDatas.get(i));
    }

    public PhotoAdapter(Context context, List<PhotoData> photoDatas) {
        this.mContext = context;
        this.photoDatas = photoDatas;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemCount() {
        return photoDatas.size();
    }
}
