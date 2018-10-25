package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.app.AppConfig;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.fragment.photo.PhotoEditAdapter;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.HttpUtils;
import com.toro.helper.utils.okhttp.mutifile.listener.impl.UIProgressListener;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.RecyclerItemDecoration;
import com.toro.helper.view.ToroProgressView;

import java.util.ArrayList;

/**
 * Create By liujia
 * on 2018/10/24.
 **/
public class UploadPhotoActivity extends ToroActivity implements View.OnClickListener {

    private static final String EXTRA_IMAGES = "extra_images";

    private MainActionBar actionBar;
    private Button submitBt;
    private RecyclerView recyclerView;
    private ArrayList<String> images;
    private PhotoEditAdapter adapter;

    private ToroProgressView progressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo_activity);
        actionBar = findViewById(R.id.main_action_view);
        actionBar.updateView(getString(R.string.upload_photo),R.mipmap.action_back_icon, 0,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        progressView = findViewById(R.id.toro_progress);
        submitBt = findViewById(R.id.submit_button);
        submitBt.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        images = new ArrayList<>();
        updateImages(getIntent().getStringArrayListExtra(EXTRA_IMAGES));
        if(images.size() > AppConfig.PhotoMaxCoun) {
            images = (ArrayList<String>) images.subList(0,AppConfig.PhotoMaxCoun - 1);
        }
        adapter = new PhotoEditAdapter(this,images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, AppConfig.PhotoSpanCount));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(this.getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
    }

    private void updateImages(ArrayList<String> images) {
        if(images == null || images.size() < 1) {
            submitBt.setEnabled(false);
        } else {
            this.images = images;
            submitBt.setEnabled(true);
        }
    }

    public static Intent newIntent(Context context, ArrayList<String> images) {
        Intent intent = new Intent();
        intent.setClass(context,UploadPhotoActivity.class);
        intent.putStringArrayListExtra(EXTRA_IMAGES,images);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submit();
                break;
        }
    }

    private void submit() {
        showProgress(0);
        ConnectManager.getInstance().uploadPhotos(this, images, ToroUserManager.getInstance(this).getToken(), new UIProgressListener() {
            @Override
            public void onUIProgress(long currentBytes, long contentLength, boolean done) {
                showProgress((int) (currentBytes * 100 / contentLength));
            }
        });
    }

    private void showProgress(int progress) {
        progressView.show(this);
        String formatString = getResources().getString(R.string.upload_progress_text_format);
        String text = String.format(formatString, progress + "%");
        progressView.setProgressText(text);
    }

    @Override
    public boolean bindData(int tag, final Object object) {
        if(HttpUtils.useOkHttp) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressView.hide(UploadPhotoActivity.this);
                    String result = (String) object;
                    BaseResponeData data = DataModleParser.parserBaseResponeData(result);
                    if(data.isStatus()) {
                        Toast.makeText(UploadPhotoActivity.this,getString(R.string.submit_sucsses),Toast.LENGTH_LONG).show();
                        finish();
                        // 上传成功
                    } else {
                        Toast.makeText(UploadPhotoActivity.this,getString(R.string.submit_failed),Toast.LENGTH_LONG).show();
                    }
                }
            });
            return true;
        } else {
            progressView.hide(this);
            return super.bindData(tag, object);
        }

    }
}
