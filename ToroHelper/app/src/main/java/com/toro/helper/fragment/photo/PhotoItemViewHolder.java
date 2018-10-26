package com.toro.helper.fragment.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.utils.ImageLoad;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class PhotoItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView photoView,actionView;
    private View rootView;

    public PhotoItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.rootView = itemView;
        this.photoView = rootView.findViewById(R.id.photo_view);
        this.actionView = rootView.findViewById(R.id.photo_action);
    }

    public void setUpPhotoView(PhotoItem photo) {
        actionView.setVisibility(View.GONE);
        ImageLoad.newInstance(photoView).load(photo,R.mipmap.image_loading);
    }

    public void setUpEditPhotoView(int index,String imagePath, View.OnClickListener deleteListener) {
        actionView.setVisibility(View.VISIBLE);
        actionView.setImageResource(R.mipmap.delete_icon);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        photoView.setImageBitmap(bitmap);
        actionView.setTag(index);
        actionView.setOnClickListener(deleteListener);
    }

    public void setUpEditPlusView(View.OnClickListener plusListener) {
        actionView.setVisibility(View.GONE);
        photoView.setImageResource(R.mipmap.plus_img);
        photoView.setOnClickListener(plusListener);
    }

    public void setOnclickListener(View.OnClickListener listener) {
        rootView.setOnClickListener(listener);
    }
}
