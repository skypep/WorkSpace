package com.toro.helper.fragment.photo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.view.RoundnessImageView;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;
    private RoundnessImageView headImg;
    private TextView nameText,timeText;
    private ImageView inAction;
    private Context mContext;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_view);
        headImg = itemView.findViewById(R.id.head_img);
        nameText = itemView.findViewById(R.id.name);
        timeText = itemView.findViewById(R.id.time);
        inAction = itemView.findViewById(R.id.action_in);
        mContext = itemView.getContext();
    }

    public void setViewPool(RecyclerView.RecycledViewPool viewPool) {
        recyclerView.setRecycledViewPool(viewPool);
    }

    public void init(PhotoData data) {
        headImg.setImageResource(R.mipmap.default_head_xxxxxxxx);
        nameText.setText(data.getUserinfo().getName());
        timeText.setText(data.getGmtUpdated());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(new PhotoItemAdapter(data.getPhotos()));
    }

    class PhotoItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<PhotoItem> photos;

        PhotoItemAdapter(List<PhotoItem> photos) {
            this.photos = photos;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            return new PhotoViewHolder(inflater.inflate(R.layout.photo_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return photos.size();
        }
    }
    }
