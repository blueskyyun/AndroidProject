package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;

/**
 * Created by mac on 2019/5/2.
 */

public class TextVisualElement extends BaseVisualElement {
    private String mText;
    private Typeface mFace;
    private float mTextSize;
    @Override
    public boolean sizeIsIntrinsic() {
        return true;
    }
    public TextVisualElement(float x, float y, String text, Typeface face, float textSize){
        super(x,y);
        mText = text;
        mFace = face;
        mTextSize = textSize;
    }

    @Override
    public void doLayout() {
        TextPaint tp = new TextPaint();
        tp.setTextSize(mTextSize);
        setW(tp.measureText(mText,0,mText.length()));
        Rect rect = new Rect();
        tp.getTextBounds(mText,0 , mText.length(),rect);
        setH(rect.height());
        setY(getY()-tp.getFontMetrics().top);
        super.doLayout();
    }

    @Override
    public void draw(Canvas onCanvas) {
       // doLayout();
        Paint p = new Paint();
        p.setTextSize(mTextSize);
        p.setTypeface(mFace);
        onCanvas.drawText(mText,getX(),getY(),p);
        onCanvas.translate(getX(),getY());
        onCanvas.clipRect(0,0,getW(),getH());
        super.draw(onCanvas);
    }
}
