package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

/**
 * Created by mac on 2019/5/6.
 */

public class RowLayout extends BaseVisualElement {

    public RowLayout(float x, float y, float w, float h){
        super(x,y,w,h);
    }

    @Override
    public void doLayout() {
        if(getParent() != null){
            if(getX() >= getParent().getW() ||getY() >= getParent().getH()){
                throw new IllegalArgumentException("position is outside the parent");
            }
            if( (getX()+getW()) >= getParent().getW() ){
                setW(getParent().getW()-getX()-1);
            }else if((getY()+getH()) > getParent().getH()){
                setH(getParent().getH()-getY()-1);
            }
        }

        if(childList.size() != 0) {
           for(int i = 0; i < childList.size(); i++){
               if(i == 0){
                   getChildAt(i).setX(0);
                   getChildAt(i).setY(getH()/2);
                   continue;
               }
               getChildAt(i).setX(getChildAt(i-1).getX()+getChildAt(i-1).getW()+1);
               getChildAt(i).setY(getH()/2);
           }
            childList.forEach(VisualElement::doLayout);
        }
    }

    @Override
    public void draw(Canvas onCanvas) {
       // doLayout();
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0,getW(),getH());
        super.draw(onCanvas);
    }
}
