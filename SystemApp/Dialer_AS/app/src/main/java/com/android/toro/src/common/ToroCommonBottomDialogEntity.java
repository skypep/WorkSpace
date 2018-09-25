package com.android.toro.src.common;

import android.graphics.Color;
import android.support.annotation.DrawableRes;

public class ToroCommonBottomDialogEntity
{
    private String content;
    @DrawableRes
    private int drawableLeft;
    private int color = Color.BLACK;

    public ToroCommonBottomDialogEntity()
    {
    }

    public ToroCommonBottomDialogEntity(String content)
    {
        this.content = content;
    }

    public ToroCommonBottomDialogEntity(int color)
    {
        this.color = color;
    }

    public ToroCommonBottomDialogEntity(String content, int drawableLeft)
    {
        this.content = content;
        this.drawableLeft = drawableLeft;
    }

    public ToroCommonBottomDialogEntity(String content, int drawableLeft, int color)
    {
        this.content = content;
        this.drawableLeft = drawableLeft;
        this.color = color;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getDrawableLeft()
    {
        return drawableLeft;
    }

    public void setDrawableLeft(int drawableLeft)
    {
        this.drawableLeft = drawableLeft;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }
}
