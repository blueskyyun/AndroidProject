package com.example.mac.puzzlegame;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.puzzlegame.puzzleGame.model.PuzzleGameBoard;
import com.example.mac.puzzlegame.puzzleGame.model.PuzzleGameState;
import com.example.mac.puzzlegame.puzzleGame.model.PuzzleGameTile;
import com.example.mac.puzzlegame.puzzleGame.util.PuzzleImageUtil;
import com.example.mac.puzzlegame.puzzleGame.view.PuzzleGameTileView;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static java.lang.Thread.sleep;

public class PuzzleGameActivity extends AppCompatActivity {
private TextView tv_score;
    private int score = 0;
    private TextView tv_rfrsh;
    private Button mOriImgBtn;
   private  Dialog customizeDialog;

    //private static final int DEFAULT_PUZZLE_BOARD_SIZE = 2;
    private int[] tileImagesId = new int[9];
    private static int TILE_IMAGE_ID = R.drawable.a_koala;
    // Game State - what the game is currently doin
    private PuzzleGameState mGameState = PuzzleGameState.NONE;

    // The size of our puzzle board (mPuzzleBoardSize x mPuzzleBoardSize grid)
    private int mPuzzleBoardSize;

    // The puzzleboard model
    private PuzzleGameBoard mPuzzleGameBoard;

    private PuzzleGameTileView[][] mPuzzleTileViews;/*= new PuzzleGameTileView[mPuzzleBoardSize][mPuzzleBoardSize];*/
    private int mPuzzleViewWidth;
    private int mTileSize;
    private Bitmap mFullPuzzleBitmap;
    private PuzzleImageUtil puzzleImageUtil = new PuzzleImageUtil();
    private RelativeLayout rlImageView;
    int[][] arrSubstituteTile;
    int emptyTileR;
    int emptyTileC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_game);
        Intent intent = getIntent();
        mPuzzleBoardSize = Integer.parseInt(intent.getStringExtra("level").substring(0,1));
        if(mPuzzleBoardSize<2 || mPuzzleBoardSize>5){
            mPuzzleBoardSize = 2;
        }
        mPuzzleTileViews = new PuzzleGameTileView[mPuzzleBoardSize][mPuzzleBoardSize];
        tv_rfrsh = (TextView)findViewById(R.id.tv_refresh);
        tv_score = (TextView)findViewById(R.id.tv_2);
        rlImageView = (RelativeLayout) findViewById(R.id.rl_2);
        mOriImgBtn = (Button)findViewById(R.id.btn_orimage);
        setListener();
        mPuzzleViewWidth = getPuzzleViewWidth();
        initGame();
        setTileImagesId(tileImagesId);


    }

    /**
     * refresh icon Listener that starts a new game - this must be attached to the new game button
     */
    private final View.OnClickListener mRefreshTVOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO start a new game if a new game button is clicked
            initGame();

        }
    };

    /**
     * Click Listener that Handles Tile Swapping when we click on a tile that is
     * neighboring the empty tile - this must be attached to every tileView in the grid
     */
    private final View.OnClickListener mGameTileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // TODO handle swapping tiles and updating the tileViews if there is a valid swap
            // with an empty tile
            // If any changes happen, be sure to update the state of the game to check for a win
            // condition
        }
    };



    /**
     * Creates the puzzleboard and the PuzzleGameTiles that serve as the model for the game. It
     * does image slicing to get the appropriate bitmap subdivisions of the TILE_IMAGE_ID. It
     * then creates a set for PuzzleGameTileViews that are used to display the information in models
     */
    private void initGame() {
        mPuzzleGameBoard = new PuzzleGameBoard(mPuzzleBoardSize,mPuzzleBoardSize);
        // Now scale the bitmap so it fits out screen dimensions and change aspect ratio (scale) to
        // fit a square
        mFullPuzzleBitmap = createFullPuzzleBitmap(mFullPuzzleBitmap);
        emptyTileR = mPuzzleBoardSize-1;
        emptyTileC = mPuzzleBoardSize-1;
        // TODO calculate the appropriate size for each puzzle tile

        // TODO create the PuzzleGameTiles for the PuzzleGameBoard using sections of the bitmap.
        int tilePx = dip2px(getBaseContext(),mTileSize);
        for(int i = 0; i < mPuzzleBoardSize; i++){
            for(int j = 0; j < mPuzzleBoardSize; j++){
                if(i*mPuzzleBoardSize+j != mPuzzleBoardSize*mPuzzleBoardSize-1) {
                    PuzzleGameTile puzzleGameTile = new PuzzleGameTile(i * mPuzzleBoardSize + j, new BitmapDrawable(puzzleImageUtil.getSubdivisionOfBitmap(mFullPuzzleBitmap, tilePx, tilePx, i, j)));
                    mPuzzleGameBoard.seTile(puzzleGameTile, i, j);
                }else{
                    PuzzleGameTile puzzleGameTile = new PuzzleGameTile(i * mPuzzleBoardSize + j, new BitmapDrawable(puzzleImageUtil.getSubdivisionOfBitmap(mFullPuzzleBitmap, tilePx, tilePx, i, j)),true);
                    mPuzzleGameBoard.seTile(puzzleGameTile, i, j);
                }
            }
        }
        // You may find PuzzleImageUtil helpful for getting sections of the bitmap
        // Also ensure the last tile (the bottom right tile) is set to be an "empty" tile
        // (i.e. not filled with an section of the original image)
        // TODO createPuzzleTileViews with the appropriate width, height
        // createPuzzleTileViews(0, 0);
        createPuzzleTileViews(mTileSize,mTileSize);
        shufflePuzzleTiles();
        for(int i = 0; i < mPuzzleBoardSize; i++){
            for(int  j = 0; j < mPuzzleBoardSize; j++){
               if(i == mPuzzleBoardSize - 1 && j == mPuzzleBoardSize-1)continue;
                //mPuzzleTileViews[i][j].updateWithTile(mPuzzleGameBoard.getTile(i,j));
                mPuzzleTileViews[i][j].updateWithTile(mPuzzleGameBoard.getTile(arrSubstituteTile[i][j]/mPuzzleBoardSize,arrSubstituteTile[i][j]%mPuzzleBoardSize));
                mPuzzleTileViews[i][j].setTileId(arrSubstituteTile[i][j]);
            }

        }
        mPuzzleTileViews[mPuzzleBoardSize-1][mPuzzleBoardSize-1].setImageDrawable(null);
        mGameState = PuzzleGameState.PLAYING;

    }

    /**
     * Creates a set of tile views based on the tileWidth and height
     * @param minTileViewWidth the minium width of the tile
     * @param minTileViewHeight the minimum height of the tile
     */

    private void createPuzzleTileViews(int minTileViewWidth, int minTileViewHeight) {
        final int rowsCount = mPuzzleGameBoard.getRowsCount();
        final int colsCount = mPuzzleGameBoard.getColumnsCount();

        // TODO Set up TileViews (that will be what the user interacts with)
        // Make sure each tileView gets a click listener for interaction
        // Be sure to set the appropriate LayoutParams so that your tileViews
        // So that they fit your gameboard properly
        for(int i = 0; i < rowsCount; i++){
            for(int j = 0; j < colsCount; j++){
                mPuzzleTileViews[i][j] = new PuzzleGameTileView(getApplicationContext(),-1,minTileViewWidth,minTileViewHeight);
                mPuzzleTileViews[i][j].setId(i*mPuzzleBoardSize+j+100);
               RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(minTileViewWidth,minTileViewHeight);
                //mPuzzleTileViews[i][j].setBackgroundColor(Color.parseColor("#ff0000"));
                mPuzzleTileViews[i][j].setBackground(getResources().getDrawable(R.drawable.imageview_shape));
                if(i == 0 && j != 0){
                    lp.addRule(RelativeLayout.RIGHT_OF, mPuzzleTileViews[i][j-1].getId());
                }
               if(i != 0 && j == 0 ){
                   lp.addRule(RelativeLayout.BELOW, mPuzzleTileViews[i-1][j].getId());
               }
                if(i != 0 && j != 0) {
                    lp.addRule(RelativeLayout.BELOW, mPuzzleTileViews[i - 1][j].getId());
                    lp.addRule(RelativeLayout.RIGHT_OF, mPuzzleTileViews[i][j-1].getId());
                }
                rlImageView.addView(mPuzzleTileViews[i][j], lp);

                mPuzzleTileViews[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //PuzzleGameTileView puzzleGameTileView = (PuzzleGameTileView)v;
                        int r = (v.getId()-100)/mPuzzleBoardSize;
                        int c = (v.getId()-100)%mPuzzleBoardSize;

                        if(mPuzzleGameBoard.areTilesNeighbor(r,c,emptyTileR,emptyTileC)){
                            int swapTileID = mPuzzleTileViews[r][c].getTileId();
                            int tileRInBoard = mPuzzleTileViews[r][c].getTileId()/mPuzzleBoardSize;
                            int tileCInBoard = mPuzzleTileViews[r][c].getTileId()%mPuzzleBoardSize;
                            mPuzzleTileViews[r][c].setImageDrawable(null);
                            mPuzzleTileViews[r][c].setTileId(mPuzzleTileViews[emptyTileR][emptyTileC].getTileId());
                            mPuzzleTileViews[emptyTileR][emptyTileC].updateWithTile(mPuzzleGameBoard.getTile(tileRInBoard,tileCInBoard));
                            mPuzzleTileViews[emptyTileR][emptyTileC].setTileId(swapTileID);
                            emptyTileR = r;
                            emptyTileC = c;
                           updateGameState();
                            if(mGameState == PuzzleGameState.WON){
                                mPuzzleTileViews[emptyTileR][emptyTileC].updateWithTile(mPuzzleGameBoard.getTile(mPuzzleBoardSize-1,mPuzzleBoardSize-1));
                                mGameState = PuzzleGameState.NONE;
                                score++;
                                Toast.makeText(getApplicationContext(),"You Win!!!",Toast.LENGTH_LONG).show();
                                Resources res = getResources();
                                tv_score.setText(String.format(res.getString(R.string.score),score));
                            }
                        }

                    }
                });

            }
        }
        mPuzzleTileViews[mPuzzleBoardSize-1][mPuzzleBoardSize-1].setTileId(mPuzzleBoardSize*mPuzzleBoardSize-1);
    }

    /**
     * Shuffles the puzzle tiles randomly such that tiles may only swap if they are swapping with
     * an empty tile to maintain solvability
     */
    private void shufflePuzzleTiles() {
        // TODO randomly shuffle the tiles such that tiles may only move spots if it is randomly
        // swapped with a neighboring empty tile
        //for()
        int emptyTileR = mPuzzleBoardSize-1;
        int emptyTileC = mPuzzleBoardSize-1;
        int swapTileR;
        int swapTileC;
        int swapIndex;
        int dirR = 0,dirC = 0;
        int emptyIndex = mPuzzleBoardSize*mPuzzleBoardSize - 1;
        int[][] dir = { {-1,0},{1,0},{0,1},{0,-1}};
        arrSubstituteTile = new int[mPuzzleBoardSize][mPuzzleBoardSize];
      for(int i = 0; i < mPuzzleBoardSize; i++){
          for(int j = 0; j < mPuzzleBoardSize; j++){
               arrSubstituteTile[i][j] = i*mPuzzleBoardSize + j;
          }
      }
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int changeTileDirection;
        for(int i = 0; i < 32; i++){
            changeTileDirection = threadLocalRandom.nextInt(4);
            if(mPuzzleGameBoard.isWithinBounds(emptyTileR+dir[changeTileDirection][0],emptyTileC+dir[changeTileDirection][1])){
                dirR = dir[changeTileDirection][0];
                dirC = dir[changeTileDirection][1];
            }else{
               for(int k = 0; k < 3; k++){
                   if(mPuzzleGameBoard.isWithinBounds(emptyTileR+dir[(changeTileDirection+ k+1)%4][0],emptyTileC+dir[(changeTileDirection+k+1)%4][1])){
                       dirR = dir[(changeTileDirection+k+1)%4][0];
                       dirC = dir[(changeTileDirection+k+1)%4][1];
                       break;
                   }
               }
            }
            swapTileR = emptyTileR;
            swapTileC = emptyTileC;
            emptyTileR += dirR;
            emptyTileC += dirC;
            swapIndex = arrSubstituteTile[emptyTileR][emptyTileC];
            arrSubstituteTile[emptyTileR][emptyTileC] = emptyIndex ;
            arrSubstituteTile[swapTileR][swapTileC] = swapIndex;
            }

           if(emptyTileR != mPuzzleBoardSize-1 ||emptyTileC != mPuzzleBoardSize-1){
                for(int a = emptyTileR; a < mPuzzleBoardSize-1; a++){
                    swapIndex = arrSubstituteTile[a+1][emptyTileC];
                    arrSubstituteTile[a+1][emptyTileC] = emptyIndex;
                    arrSubstituteTile[a][emptyTileC] = swapIndex;
                }
                this.emptyTileR = mPuzzleBoardSize-1;
                for(int a = emptyTileC; a < mPuzzleBoardSize-1; a++){
                    swapIndex = arrSubstituteTile[this.emptyTileR][a+1];
                    arrSubstituteTile[this.emptyTileR][a+1] = emptyIndex;
                    arrSubstituteTile[this.emptyTileR][a] = swapIndex;
                }
               this.emptyTileC = mPuzzleBoardSize-1;
            }
            /*for(int i = 0; i < mPuzzleBoardSize; i++){
                for(int j = 0; j < mPuzzleBoardSize; j++){
                    Log.d("mmm","("+i+","+j+")"+","+arrSubstituteTile[i][j]);
                }
            }*/
        }




    /**
     * Places the empty tile in the lower right corner of the grid
     */
    private void resetEmptyTileLocation() {
        // TODO
    }

    /**
     * Updates the game state by checking if the user has won. Also triggers the tileViews to update
     * their visuals based on the gameboard
     */
    private void updateGameState() {
        boolean isWon = true;
        for(int i = 0; i < mPuzzleBoardSize; i++){
            for(int j = 0; j < mPuzzleBoardSize; j++){
                if(mPuzzleTileViews[i][j].getTileId()/mPuzzleBoardSize != i || mPuzzleTileViews[i][j].getTileId() % mPuzzleBoardSize != j ){
                    isWon = false;
                    break;
                }
            }
            if(!isWon)break;

        }
        if(isWon){
            mGameState = PuzzleGameState.WON;
        }
        // TODO refresh tiles and handle winning the game and updating score
    }
private void resetGame(){
    emptyTileC = mPuzzleBoardSize-1;
    emptyTileR = mPuzzleBoardSize-1;
    mFullPuzzleBitmap = null;
    mPuzzleGameBoard.reset();
    for(int i = 0; i < mPuzzleBoardSize; i++){
        for(int j = 0; j < mPuzzleBoardSize; j++){
            mPuzzleTileViews[i][j].setImageDrawable(null);
        }
    }
}
    private void refreshGameBoardView() {
        // TODO update the PuzzleTileViews with the data stored in the PuzzleGameBoard
    }


    /**
     * Checks the game board to see if the tile indices are in proper increasing order
     * @return true if the tiles are in correct order and the game is won
     */
    private boolean hasWonGame() {
        // TODO check if the user has won the game
        return false;

    }

    /**
     * Updates the score displayed in the text view
     */
    private void updateScore() {
        // TODO update a score to be displayed to the user

       }

    /**
     * Begins a new game by shuffling the puzzle tiles, changing the game state to playing
     * and showing a start message
     */
    private void startNewGame() {
        // TODO - handle starting a new game by shuffling the tiles and showing a start message,
        // and updating the game state
    }

    public int[] getTileImagesId() {
        return tileImagesId;
    }

    public void setTileImagesId(int[] fullImageId) {
        tileImagesId[0]=R.drawable.a_europe;
        tileImagesId[1]=R.drawable.a_koala;
        tileImagesId[2]=R.drawable.a_norway;
        tileImagesId[3]=R.drawable.kitty;
        tileImagesId[4]=R.drawable.a_cat;
        tileImagesId[5]=R.drawable.a_forest_stream;
        tileImagesId[6]=R.drawable.a_ice_free_river;
        tileImagesId[7]= R.drawable.a_tibet;
        tileImagesId[8]=R.drawable.a_roadway;
        //TODO - this.tileImagesId = fullImagesId;
    }
    private int getPuzzleViewWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int screenWidth = metrics.widthPixels - 92;
        mTileSize = screenWidth/mPuzzleBoardSize;
        return  screenWidth;
    }

    private int getScreenViewHeight(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        return  screenHeight;
    }

    private Bitmap createFullPuzzleBitmap(Bitmap bitmap){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeResource(getResources(),TILE_IMAGE_ID, options);
        //不获取图片，不加载到内存中，只返回图片属性
        //图片的宽高
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        ThumbnailUtils utils = new ThumbnailUtils();
        int i = computeSampleSize(options, -1, 1000 * 1000);
        //设置采样率，不能小于1 假如是2 则宽为之前的1/2，高为之前的1/2，一共缩小1/4 一次类推
        options.inSampleSize = i;
       // Log.d("mmm", "采样率为=" + i);
        //图片格式压缩
        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(),TILE_IMAGE_ID, options);
        float bitmapsize = getBitmapSize(bitmap);
        //Log.d("mmm","压缩后：图片占内存大小" + bitmapsize + "MB / 宽度=" + bitmap.getWidth() + "高度=" + bitmap.getHeight());

        // Now scale the bitmap so it fits out screen dimensions and change aspect ratio (scale) to
        // fit a square
        int fullImageWidth = bitmap.getWidth();
        int fullImageHeight = bitmap.getHeight();
        int rectX = fullImageWidth >= fullImageHeight ? (fullImageWidth-fullImageHeight)/2 : 0;
        int rectY = fullImageWidth >= fullImageHeight ? 0: (fullImageHeight-fullImageWidth)/2;
        int squareImageSize = (fullImageWidth>fullImageHeight) ? fullImageWidth : fullImageHeight;
        //fullImageBitmap = Bitmap.createScaledBitmap(fullImageBitmap, squareImageSize,squareImageSize,false);
        bitmap = Bitmap.createBitmap(bitmap,rectX,rectY,squareImageSize,squareImageSize, null,false);
        if(mPuzzleViewWidth <= 0){
            mPuzzleViewWidth = getPuzzleViewWidth();
        }
        int imagePx = dip2px(getBaseContext(),mPuzzleViewWidth);
        bitmap = Bitmap.createScaledBitmap(bitmap,imagePx , imagePx, false);
        return bitmap;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int computeSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,maxNumOfPixels);
         int roundedSize;
         if (initialSize <= 8) {
             roundedSize = 1;
             while (roundedSize < initialSize) {
                 roundedSize <<= 1;
                 }
             } else {
             roundedSize = (initialSize + 7) / 8 * 8;
             }
        return roundedSize;
        }
    private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
        //原始图片的宽
        double w = options.outWidth;
        //原始图片的高
        double h = options.outHeight;
        System.out.println("========== w=" + w + ",h=" + h);
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }
    private void showCustomizeDialog(){

        customizeDialog = new Dialog(PuzzleGameActivity.this,R.style.edit_AlertDialog_style);
        customizeDialog.setContentView(R.layout.activity_start_dialog);
        ImageView imageView = (ImageView)customizeDialog.findViewById(R.id.ori_img);
        imageView.setBackgroundResource(TILE_IMAGE_ID);
        customizeDialog.setCanceledOnTouchOutside(true);
        /*Window window = customizeDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 40;
        customizeDialog.onWindowAttributesChanged(lp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customizeDialog.dismiss();
            }
        });*/
        customizeDialog.show();

    }
    private void nextGame(){
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int id = threadLocalRandom.nextInt(9);
        TILE_IMAGE_ID = tileImagesId[id];
      resetGame();
        mFullPuzzleBitmap = createFullPuzzleBitmap(mFullPuzzleBitmap);
        emptyTileR = mPuzzleBoardSize-1;
        emptyTileC = mPuzzleBoardSize-1;
        int tilePx = dip2px(getBaseContext(),mTileSize);
        for(int i = 0; i < mPuzzleBoardSize; i++){
            for(int j = 0; j < mPuzzleBoardSize; j++){
                if(i*mPuzzleBoardSize+j != mPuzzleBoardSize*mPuzzleBoardSize-1) {
                    PuzzleGameTile puzzleGameTile = new PuzzleGameTile(i * mPuzzleBoardSize + j, new BitmapDrawable(puzzleImageUtil.getSubdivisionOfBitmap(mFullPuzzleBitmap, tilePx, tilePx, i, j)));
                    mPuzzleGameBoard.seTile(puzzleGameTile, i, j);
                }else{
                    PuzzleGameTile puzzleGameTile = new PuzzleGameTile(i * mPuzzleBoardSize + j, new BitmapDrawable(puzzleImageUtil.getSubdivisionOfBitmap(mFullPuzzleBitmap, tilePx, tilePx, i, j)),true);
                    mPuzzleGameBoard.seTile(puzzleGameTile, i, j);
                }
            }
        }
        shufflePuzzleTiles();
        for(int i = 0; i < mPuzzleBoardSize; i++){
            for(int  j = 0; j < mPuzzleBoardSize; j++){
                if(i == mPuzzleBoardSize - 1 && j == mPuzzleBoardSize-1)continue;
                //mPuzzleTileViews[i][j].updateWithTile(mPuzzleGameBoard.getTile(i,j));
                mPuzzleTileViews[i][j].updateWithTile(mPuzzleGameBoard.getTile(arrSubstituteTile[i][j]/mPuzzleBoardSize,arrSubstituteTile[i][j]%mPuzzleBoardSize));
                mPuzzleTileViews[i][j].setTileId(arrSubstituteTile[i][j]);
            }

        }
        mPuzzleTileViews[mPuzzleBoardSize-1][mPuzzleBoardSize-1].setImageDrawable(null);
        mGameState = PuzzleGameState.PLAYING;

    }
    private void setListener(){
        OnClick onClick = new OnClick();
        mOriImgBtn.setOnClickListener(onClick);
        tv_rfrsh.setOnClickListener(onClick);

    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_orimage:
                    showCustomizeDialog();
                    break;
                case R.id.tv_refresh:
                    nextGame();
                    break;
            }
        }
    }

}
