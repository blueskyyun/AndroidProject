package com.example.mac.puzzlegame.puzzleGame.model;

/**
 * Created by mac on 2019/4/22.
 */

public class PuzzleGameBoard {
    private PuzzleGameTile[][] mTileGrid;
    private int mRows;
    private int mColumns;
    public PuzzleGameBoard(int size){
        this(size,size);
    }

    public PuzzleGameBoard(int rows, int columns) {
        if(rows <= 0 || columns <= 0){
            throw new IllegalArgumentException("GameBoard must have width/height dimensions greater than 0");
        }
        mRows = rows;
        mColumns = columns;
        mTileGrid = new PuzzleGameTile[mRows][mColumns];

    }
    public int getRowsCount(){
        return mRows;
    }
    public int getColumnsCount(){
        return mColumns;
    }
    public int getTotalTileCount(){
        return mRows*mColumns;
    }

    /**
     *  Sets the tile at the row,col position in the game board
     * @param tile  the tile to set
     * @param row the row to set the tile in
     * @param col the column to set the tile in
     */
    public void seTile(PuzzleGameTile tile, int row, int col){
        throwOutOfBoundsExceptionIfNecessary(row, col);
        mTileGrid[row][col] = tile;
    }

    /**
     * get the tile at the row,col position in the game board
     * @param row the row to get the tile
     * @param col  the column to get the tile
     * @return the tile at the row,col position in the game board
     */
    public PuzzleGameTile getTile(int row, int col){
        throwOutOfBoundsExceptionIfNecessary(row, col);
        return mTileGrid[row][col];
    }
    /**
     * Checks if the tile at row, col is empty
     * @param row the row of the tile to check
     * @param col the col of the tile to checks
     * @return true if the tile exists and is empty; false if the tile is not empty, or null
     */
    public Boolean isEmptyTile(int row, int col){
        throwOutOfBoundsExceptionIfNecessary(row, col);
        if(mTileGrid[row][col] == null){
            return false;
        }
        return mTileGrid[row][col].isEmpty();

    }
    /**
     * Clears all the tiles on the game board
     */
    public void reset(){
        for(int i = 0; i < mRows; i++){
            for(int j = 0; j < mColumns; j++){
                mTileGrid[i][j] = null;
            }
        }
    }

    /**
     * Checks if two tiles neighbor eachother on the gameboard along the vertical and horizontal
     * axes
     * @param fTileR the first tile's row index
     * @param fTileC the first tile's column index
     * @param sTileR the second tile's row index
     * @param sTileC the second tile's row index
     * @return true if the tiles are neighbors
     */
    public boolean areTilesNeighbor(int fTileR, int fTileC, int sTileR, int sTileC){
        throwOutOfBoundsExceptionIfNecessary(fTileR, fTileC);
        throwOutOfBoundsExceptionIfNecessary(sTileR,sTileC);
        if(fTileR==sTileR && Math.abs(fTileC-sTileC) == 1){
            return true;
        }else if(fTileC==sTileC && Math.abs(fTileR-sTileR) == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * checks if the given row, col position is within the bounds of the game board
     * @param row the given row
     * @param col the given column
     * @return true if the row, col is within the game board
     */
    public boolean isWithinBounds(int row, int col){
        if((row < 0 || row >= mRows) || (col < 0 || col >= mColumns)){
            return false;
        }
        return true;
    }
    public final void swapTiles(int fTileR, int fTileC, int sTileR, int sTileC){
        throwOutOfBoundsExceptionIfNecessary(fTileR, fTileC);
        throwOutOfBoundsExceptionIfNecessary(sTileR, sTileC);
        PuzzleGameTile tmpTile;
       tmpTile = mTileGrid[fTileR][fTileC];
        mTileGrid[fTileR][fTileC] = mTileGrid[sTileR][sTileC];
        mTileGrid[sTileR][sTileC] = tmpTile;
        //TODO-利用位置进行交换

    }
    private final void throwOutOfBoundsExceptionIfNecessary(int row, int col) {

         if(!isWithinBounds(row,col)){
             throw new IndexOutOfBoundsException("row,col combination is out of board's dimensions");

        }
    }
}
