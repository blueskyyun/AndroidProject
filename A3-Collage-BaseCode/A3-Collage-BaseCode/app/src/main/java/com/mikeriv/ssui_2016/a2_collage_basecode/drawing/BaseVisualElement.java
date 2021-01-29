package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by mac on 2019/5/2.
 */

public class BaseVisualElement extends PrebaseVisualElement {
    private float x;
    private float y;
    private float width;
    private float height;
    protected ArrayList<VisualElement> childList =  new ArrayList<VisualElement>();
    private VisualElement parent = null;

    @Override
    public void setPosition(PointF pos) {
        if (pos != null) {
            setPosition(pos.x, pos.y);
        }
    }
    @Override
    public void setPosition(float x, float y) {
       if(x < 0 && y >= 0){
            x = 0;
        }else if(x >= 0 && y < 0){
           y = 0;
       }else if(x < 0 && y < 0){
            x = 0;
           y = 0;
        }
        setX(x);
        setY(y);
    }

    @Override
    public void setX(float x){
        if(x < 0){
            x = 0;
        }
        this.x = x;
    }

    /* (non-Javadoc)
     * @see com.mikeriv.ssui_2016.a2_collage_basecode.drawing.VisualElement#setY(float)
     */
    @Override
    public void setY(float y){
        if(y < 0){
            y = 0;
        }
        this.y = y;
    }
    @Override
    public PointF getPosition() {
        return new PointF(getX(),getY());
    }

    @Override
    public float getX(){
        return x;
    }

    @Override
    public float getY(){
        return y;
    }

    @Override
    public boolean sizeIsIntrinsic() {
        // default value -- override in subclasses that need to...
        return false;
    }

    @Override
    public void setSize(PointF size) {
        if (size != null) {
            setSize(size.x,size.y);
        }
    }

    @Override
    public void setSize(float w, float h) {
        if(w < 0 && h >= 0){
           w = 0;
        }else if(w >= 0 && h < 0){
            h = 0;
        }else if(w < 0 && h < 0){
            w = 0;
            h = 0;
        }
        setW(w);
        setH(h);
    }

    @Override
    public void setW(float w){
        if(w < 0){
        w = 0;
    }
    width = w;
    }


    @Override
    public void setH(float h){
        if(h < 0){
            h  = 0;
        }
        height = h;
    }


    @Override
    public PointF getSize() {
        return new PointF(getW(),getH());
    }


    @Override
    public float getW(){
        return width;
    }

    @Override
   public float getH(){
        return  height;
    }

    @Override
    public VisualElement getParent(){
        return parent;
    }


    @Override
    public void setParent(VisualElement newParent){

            parent = newParent;
        //TODO -- put this visualElement into the newparent's child list,reset layout, redrawing
    }


    @Override
    public int getNumChildren(){
        return childList.size();
    }

    @Override
    public VisualElement getChildAt(int index){
        if(index >= childList.size() || index < 0){
            return null;
        }else{
            return  childList.get(index);
        }
    }

    @Override
    public int findChild(VisualElement child){
        if(childList.contains(child)){
            return childList.indexOf(child);
        }else {
            return  -1;
        }
    }


    @Override
    public void addChild(VisualElement child){
        if(child != null) {
            child.setParent(this);
            childList.add(child);
        }
    }


    @Override
    public void removeChildAt(int index){
        if(index < childList.size() && index >= 0)
        childList.remove(index);
    }


    @Override
   public void removeChild(VisualElement child){
        if(childList.contains(child)){
            childList.remove(child);
        }
    }

    @Override
    public void moveChildFirst(final VisualElement child){
        if(childList.contains(child)){
            int index_move  = childList.indexOf(child);
            for (int i = index_move; i > 0; i--){
                VisualElement tmpChild = childList.get(i-1);
                childList.set(i-1, child);
                childList.set(i, tmpChild);
            }
        }
    }


    @Override
    public void moveChildLast(VisualElement child){
        if(childList.contains(child)){
            int index_move  = childList.indexOf(child);
            for (int i = index_move; i < childList.size()-1; i++){
                VisualElement tmpChild = childList.get(i+1);
                childList.set(i+1, child);
                childList.set(i, tmpChild);
            }
        }
    }


    @Override
    public void moveChildEarlier(VisualElement child){
        if(childList.contains(child)){
            int index_move  = childList.indexOf(child);
                VisualElement tmpChild = childList.get(index_move-1);
                childList.set(index_move-1, child);
                childList.set(index_move, tmpChild);
        }
    }


    @Override
   public void moveChildLater(VisualElement child){
        if(childList.contains(child)){
            int index_move  = childList.indexOf(child);
                VisualElement tmpChild = childList.get(index_move+1);
                childList.set(index_move+1, child);
                childList.set(index_move, tmpChild);
        }
    }

    @Override
    public void doLayout(){
        if(getParent() != null){
            if(x >= getParent().getW() || getY() >= getParent().getH()){
                throw new IllegalArgumentException("position is outside the parent");
            }
            if( (getX()+getW()) >= getParent().getW() ){
                setW(getParent().getW()-getX());
            }else if((getY()+getH()) > getParent().getH()){
                setH(getParent().getH()-getY());
            }
        }
        if(childList.size() != 0)
        childList.forEach(VisualElement::doLayout);
    }
    @Override
    public void draw(Canvas onCanvas){
        //TODO--draw self
        if(childList.size() != 0) {
            for (VisualElement vchild : childList
                    ) {
                onCanvas.save();
                vchild.draw(onCanvas);
                onCanvas.restore();
            }
        }
    }

    /**
     * Default constructor.
     */
    public BaseVisualElement() {
        // do default initialization at an odd size so its easy to see when
        // the size is never set up.
        this(0,0);
    }

    /**
     * Constructor with position only.
     */
    public BaseVisualElement(float xLoc, float yLoc) {
        // do default initialization at an odd size so its easy to see when
        // the size is never set up.
        this(xLoc,yLoc,13.7f, 17.9f);
    }

    /**
     * Full constructor giving position and size.
     */
    public BaseVisualElement(float xLoc, float yLoc, float width, float height) {
//        setPosition(xLoc,yLoc);
//        setSize(width,height);
        super(xLoc, yLoc, width, height);
    }
}
