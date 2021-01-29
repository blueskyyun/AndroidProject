package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by mac on 2019/5/2.
 */

public class SimpleFrame extends BaseVisualElement {

    public SimpleFrame(float xLoc, float yLoc, float width, float height){
        super(xLoc, yLoc, width, height);
    }

    @Override
    public void draw(Canvas onCanvas) {
       // doLayout();
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        onCanvas.drawRect(getX(),getY(),getX()+getW(), getY()+getH(),p);
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0, getW(),getH());
        super.draw(onCanvas);

    }
}
