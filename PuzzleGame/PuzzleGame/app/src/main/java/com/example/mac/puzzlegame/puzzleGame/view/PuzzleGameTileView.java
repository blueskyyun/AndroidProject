package com.example.mac.puzzlegame.puzzleGame.view;

import android.content.Context;
import android.widget.ImageView;

import com.example.mac.puzzlegame.puzzleGame.model.PuzzleGameTile;

/**
 * Created by mac on 2019/4/22.
 */

public class PuzzleGameTileView extends android.support.v7.widget.AppCompatImageView {
    // A Unique Index that identifies this view in its grid
   private int mTileId = -1;
    public PuzzleGameTileView(Context context){
        super(context);
    }
    public PuzzleGameTileView(Context context, int tileId, int minWidth, int minHeight){
        super(context);
        mTileId = tileId;
        init(minWidth,minHeight);
    }

    private void init(int minWidth, int minHeight) {
        setMinimumWidth(minWidth);
        setMinimumHeight(minHeight);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);

    }
    public int getTileId(){
        return mTileId;
    }

    public void setTileId(int id){
        mTileId = id;
    }
    public void updateWithTile(PuzzleGameTile tile){
        setImageDrawable(tile.getDrawable());
    }
}
