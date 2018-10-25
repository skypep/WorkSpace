package com.toro.helper.fragment.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toro.helper.R;

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

    public void setUpPhotoView() {
        actionView.setVisibility(View.GONE);
        photoView.setImageResource(R.mipmap.default_photo_xxxxxxxxx);
    }

    public void setUpEditPhotoView(String imagePath, View.OnClickListener deleteListener) {
        actionView.setVisibility(View.VISIBLE);
        actionView.setImageResource(R.mipmap.delete_xxxxxxxx);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        photoView.setImageBitmap(bitmap);
//        photoView.setImageResource(R.mipmap.default_photo_xxxxxxxxx);
        actionView.setOnClickListener(deleteListener);
    }

    public void setUpEditPlusView(View.OnClickListener plusListener) {
        actionView.setVisibility(View.GONE);
        photoView.setImageResource(R.mipmap.plus_xxxxxxxxxxx);
        photoView.setOnClickListener(plusListener);
    }
}
