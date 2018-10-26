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
import com.toro.helper.activity.PhotoPreviewActivity;
import com.toro.helper.app.AppConfig;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.utils.ImageLoad;
import com.toro.helper.view.RecyclerItemDecoration;
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
    private boolean isFirst = false;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        recyclerView = itemView.findViewById(R.id.recycler_view);
        headImg = itemView.findViewById(R.id.head_img);
        nameText = itemView.findViewById(R.id.name);
        timeText = itemView.findViewById(R.id.time);
        inAction = itemView.findViewById(R.id.action_in);
        isFirst = true;
    }

    public void setViewPool(RecyclerView.RecycledViewPool viewPool) {
        recyclerView.setRecycledViewPool(viewPool);
    }

    public void init(PhotoData data) {
        nameText.setText(data.getUserinfo().getName());
        timeText.setText(data.getGmtUpdated());
        recyclerView.setAdapter(new PhotoItemAdapter(data.getPhotos()));
        if(isFirst) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, AppConfig.PhotoSpanCount));
            recyclerView.addItemDecoration(new RecyclerItemDecoration(mContext.getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
            isFirst = false;
        }
        ImageLoad.newInstance(headImg).load(data.getUserinfo().getHeadPhoto(),R.mipmap.default_head);
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
            return new PhotoItemViewHolder(inflater.inflate(R.layout.photo_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            PhotoItemViewHolder itemView = (PhotoItemViewHolder) viewHolder;
            itemView.setUpPhotoView(photos.get(i));
            itemView.setOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(PhotoPreviewActivity.createIntent(mContext,photos,i));
                }
            });
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }
    }
    }
