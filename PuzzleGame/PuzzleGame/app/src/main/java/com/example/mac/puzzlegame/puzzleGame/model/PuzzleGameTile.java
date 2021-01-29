package com.example.mac.puzzlegame.puzzleGame.model;

import android.graphics.drawable.Drawable;

/**
 * Created by mac on 2019/4/22.
 */

public class PuzzleGameTile {
    private static final int INVALID_TILE_INDEX = -1;
    private int mOrderIndex = INVALID_TILE_INDEX;
    private Boolean mIsEmpty = false;

    public Boolean isEmpty() {
        return mIsEmpty;
    }

    public void setIsEmpty(Boolean mIsEmpty) {
        this.mIsEmpty = mIsEmpty;
    }

    public int getOrderIndex() {
        return mOrderIndex;
    }

    public void setOrderIndex(int mOrderIndex) {
        this.mOrderIndex = mOrderIndex;
    }

    public Drawable getDrawable() {

        return mDrawable;
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    private Drawable mDrawable = null;
    public PuzzleGameTile(){}
    public PuzzleGameTile(int tileOrderIndex, Drawable drawable){
        this(tileOrderIndex,drawable,false);
    }

    public PuzzleGameTile(int tileOrderIndex, Drawable drawable, boolean isEmpty) {
        mOrderIndex = tileOrderIndex;
        mDrawable = drawable;
        mIsEmpty = isEmpty;

    }
}
