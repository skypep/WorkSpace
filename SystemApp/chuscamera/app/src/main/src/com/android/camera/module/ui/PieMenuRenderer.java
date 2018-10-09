package com.android.camera.module.ui;

import android.graphics.Canvas;

import com.android.camera.ui.FocusIndicator;
import com.android.camera.ui.OverlayRenderer;

/**
 * Created by THINK on 2017/8/23.
 */

abstract  public class PieMenuRenderer extends OverlayRenderer
        implements FocusIndicator {

    /*
    @Override
    public void showStart() {
    }
    @Override
    public void showSuccess(boolean timeout) {
    }
    @Override
    public void showFail(boolean timeout) {
    }
    @Override
    public void clear() {
    }

    @Override
    public void onDraw(Canvas canvas) {
    }
    */

    public PieListener mListener;
    static public interface PieListener {
        public void onPieOpened(int centerX, int centerY);
        public void onPieClosed();
    }
    public void setPieListener(PieListener pl) {
        mListener = pl;
    }

    public PieMenuRenderer() { // empty
    }
    abstract public  boolean showsItems();
    abstract public boolean isOpen();
    abstract public void showInCenter();
    abstract public void hide();
    abstract public void setBlockFocus(boolean blocked);
    abstract public void setFocus(int x, int y);

}
