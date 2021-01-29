package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * Created by mac on 2019/5/2.
 */

public class IconImage extends BaseVisualElement {

    private Bitmap mSourceImage;
    @Override
    public boolean sizeIsIntrinsic() {
        return true;
    }
    public IconImage(float x, float y, Bitmap image){
        super(x,y);
        mSourceImage = image;
    }

    @Override
    public void doLayout() {
        if (mSourceImage != null) {
            setW(mSourceImage.getWidth());
            setH(mSourceImage.getHeight());
        }
        if (getParent() != null) {
            if (getX() >= getParent().getW() || getY() >= getParent().getH()) {
                throw new IllegalArgumentException("position is outside the parent");
            }
            if ((getX() + getW()) >= getParent().getW()) {
                setW(getParent().getW() - getX() - 1);
            } else if ((getY() + getH()) > getParent().getH()) {
                setH(getParent().getH() - getY() - 1);
            }
        }
        if (childList.size() != 0)
            childList.forEach(VisualElement::doLayout);
    }

    @Override
    public void draw(Canvas onCanvas) {
        //doLayout();
        Paint p = new Paint();
        onCanvas.drawBitmap(mSourceImage,getX(),getY(),p);
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0,getW(), getH());
        super.draw(onCanvas);
    }
}
