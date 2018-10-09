package com.cs.camera.magicfilter;

import android.content.Context;

import com.cs.camera.magicfilter.filter.FilterType;

import com.cs.camera.magicfilter.SavePictureTask;
import com.cs.camera.magicfilter.widget.MagicCameraView;
import com.cs.camera.magicfilter.widget.MagicBaseView;

import java.io.File;

/**
 * Created by why8222 on 2016/2/25.
 */
public class MagicEngine {
    private static MagicEngine magicEngine;

    public static MagicEngine getInstance(){
        if(magicEngine == null)
            throw new NullPointerException("MagicEngine must be built first");
        else
            return magicEngine;
    }

    private MagicEngine(Builder builder){

    }

    public void setFilter(FilterType type){
        MagicContext.magicBaseView.setFilter(type);
    }

    public void savePicture(File file, SavePictureTask.OnPictureSaveListener listener){
        SavePictureTask savePictureTask = new SavePictureTask(file, listener);
        MagicContext.magicBaseView.savePicture(savePictureTask);
    }

    public void startRecord(){
        if(MagicContext.magicBaseView instanceof MagicCameraView)
            ((MagicCameraView)MagicContext.magicBaseView).changeRecordingState(true);
    }

    public void stopRecord(){
        if(MagicContext.magicBaseView instanceof MagicCameraView)
            ((MagicCameraView)MagicContext.magicBaseView).changeRecordingState(false);
    }

    public void setBeautyLevel(int level){
        if(MagicContext.magicBaseView instanceof MagicCameraView && MagicContext.beautyLevel != level) {
            MagicContext.beautyLevel = level;
            ((MagicCameraView) MagicContext.magicBaseView).onBeautyLevelChanged();
        }
    }

    public void switchCamera(){
        CameraEngine.switchCamera();
    }

    public static class Builder{

        public MagicEngine build(MagicBaseView magicBaseView) {
            MagicContext.context = magicBaseView.getContext();
            MagicContext.magicBaseView = magicBaseView;
            return new MagicEngine(this);
        }

        public Builder setVideoPath(String path){
            MagicContext.videoPath = path;
            return this;
        }

        public Builder setVideoName(String name){
            MagicContext.videoName = name;
            return this;
        }

    }
}
