package com.toro.helper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.modle.photo.PhotoUserInfo;
import com.toro.helper.modle.photo.PubTime;
import com.toro.helper.utils.okhttp.OkHttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create By liujia
 * on 2018/10/26.
 **/
public class ImageLoad {

    private String url;

    public static ImageLoad newInstance(ImageView imageView) {
        ImageLoad instance = new ImageLoad(imageView);

        return instance;
    }

    private ImageView imageView;
    private Context mContext;

    private ImageLoad(ImageView imageView) {
        this.imageView = imageView;
        mContext = imageView.getContext();
    }

    public void load(PhotoItem photo, int defultResId) {
        if(photo == null) {
            imageView.setImageResource(defultResId);
        } else {
            url = ConnectManager.getInstance().getPhotoUrl(photo);
            Bitmap bitmap = ImageCache.getInstance().readBitmapFromDiskLruCache(url);
            if(bitmap == null) {
                imageView.setImageResource(defultResId);
                OkHttp.downloadImage(ConnectManager.DOWNLOAD_IMAGE,url, ToroUserManager.getInstance(mContext).getToken(),photo.getPath(),listener);
            } else {
                imageView.setImageBitmap(bitmap);
            }
        }


    }

    private OnHttpDataUpdateListener listener = new OnHttpDataUpdateListener() {
        @Override
        public boolean bindData(int tag, Object object) {
            if(tag == ConnectManager.DOWNLOAD_IMAGE) {
                try{
                    final Bitmap bitmap = (Bitmap) object;
                    if(bitmap != null) {
                        imageView.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
//                        ImageCache.getInstance().writeToDiskLruCache(url,bitmap);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    };
}
