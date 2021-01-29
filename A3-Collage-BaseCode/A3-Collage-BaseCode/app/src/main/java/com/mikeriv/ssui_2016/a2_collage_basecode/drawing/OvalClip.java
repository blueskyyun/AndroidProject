package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by mac on 2019/5/2.
 */

public class OvalClip extends BaseVisualElement {
    public OvalClip(float x, float y, float w, float h){
        super(x,y,w,h);
    }



    @Override
    public void draw(Canvas onCanvas) {
        Path path = new Path();
        path.addOval(new RectF(getX(),getY(),getW(),getH()),Path.Direction.CW);
        onCanvas.clipPath(path);
        super.draw(onCanvas);
    }
}
