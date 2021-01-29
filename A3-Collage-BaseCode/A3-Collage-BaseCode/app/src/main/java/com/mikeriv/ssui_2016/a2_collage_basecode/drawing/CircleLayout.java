package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

/**
 * Created by mac on 2019/5/6.
 */

public class CircleLayout extends BaseVisualElement {
    private float mCircleCenterX;
    private float mCircleCenterY;
    private float mRadius;

    public CircleLayout(float x, float y, float w, float h, float layoutCenterX, float layoutCenterY, float layoutRadius) {
        super(x, y, w, h);
        if(layoutCenterX>w){
            layoutCenterX = w/2;
        }
        if(layoutCenterY>h){
            layoutCenterY = h/2;
        }
        mCircleCenterX = layoutCenterX;
        mCircleCenterY = layoutCenterY;
        mRadius = layoutRadius;
    }

    @Override
    public void draw(Canvas onCanvas) {
        //doLayout();
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0,getW(),getH());
        float rotateAngle = (float) (360.0/getNumChildren());
        float x;
        float y;
        if(getNumChildren() != 0){
            for(int i = 0; i < getNumChildren(); i++){
                x = mCircleCenterX - getChildAt(i).getW()/2;
                y = mCircleCenterY - mRadius*2/3- getChildAt(i).getH()/2;
                getChildAt(i).setX(x);
                getChildAt(i).setY(y);
                getChildAt(i).draw(onCanvas);
                onCanvas.rotate(rotateAngle,mCircleCenterX,mCircleCenterY);
            }
        }



    }
}
