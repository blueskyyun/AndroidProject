package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mac on 2019/5/2.
 */

public class SolidBackDrop extends BaseVisualElement {
    private int mSolidColor;
    public int getmSolidColor() {
        return mSolidColor;
    }

    public void setmSolidColor(int mSolidColor) {
        this.mSolidColor = mSolidColor;
    }


    public SolidBackDrop(float x, float y, float w, float h, int color){
        super(x,y,w,h);
        setmSolidColor(color);
    }

    @Override
    public void draw(Canvas onCanvas) {
        //doLayout();
        Paint p = new Paint();
        p.setColor(mSolidColor);
        p.setStyle(Paint.Style.FILL);
        onCanvas.drawRect(getX(),getY(),getX()+getW(), getY()+getH(),p);
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0, getW(),getH());
        super.draw(onCanvas);
    }
}
