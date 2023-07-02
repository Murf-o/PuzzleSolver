package com.SlidingBlock.PuzzleSolver.logic;

import java.util.ArrayList;

public class Grid {

    protected int rowSize;
    protected int colSize;
    protected ArrayList<ArrayList<Piece>> grid;
    protected ArrayList<Piece> pList;


    //constructor
    public Grid(int row, int col){
      this.grid = new ArrayList<>(row);
      this.pList = new ArrayList<Piece>();
      
      for(int i = 0; i < row; ++i) {
    	  this.grid.add(new ArrayList<Piece>(col));
      }
      
      this.colSize = col;
      this.rowSize = row;
      for(int i = 0; i < row; i++){
        for(int j = 0; j < col; j++){
          this.grid.get(i).add(null);
        }
      }
    }


    //adds block to the grid
    public void addBlock(String pieceName, int r, int c, int w, int h, String movement){
        Piece p = new Piece(pieceName, w, h, movement);

        //add Piece* p to everywhere its locates to grid -- depends on w and h
        for(int i = 0; i < h; i++){
          for(int j = 0; j < w; j++){
            grid.get(r+i).set(c+j, p);
          }
        }
        pList.add(p);
    }

    public Piece get(int r, int c){
      return grid.get(r).get(c);
    }

    //returns grid
    public ArrayList<ArrayList<Piece>> getGrid(){ 
      return grid;
    }
}
