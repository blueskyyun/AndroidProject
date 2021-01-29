package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.RectF;

/**
 * Created by mac on 2019/5/2.
 */

public class NinePartImage extends BaseVisualElement {
private NinePatch mNinePatches;

    public NinePartImage(float x, float y, float w, float h, NinePatch patches){
        super(x,y,w,h);
        if(patches == null){
            throw new NullPointerException("NinePatch is null");
        }
        mNinePatches = patches;
    }

    @Override
    public void draw(Canvas onCanvas) {
        //doLayout();
        mNinePatches.draw(onCanvas,new RectF(getX(),getY(),getX()+getW(),getY()+getH()));
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0,getW(),getH());
        super.draw(onCanvas);
    }
}
