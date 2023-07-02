package com.SlidingBlock.PuzzleSolver.logic;

import java.util.ArrayList;

public class Movement {
	
	//returns grid, grid with piece moved up.
    //int r, c are the location of the piece. int w, h are the width and height
     public static ArrayList<ArrayList<Piece>> moveUp(ArrayList<ArrayList<Piece>> grid, int r, int c, int w, int h, Piece p){
        int i = r-1;
        for(int j = c; j < c+w; j++){
          grid.get(i).set(j, p);
          grid.get(i+h).set(j, null);
        }
        return grid;
    }

    //returns grid, grid with piece moved down. 
    //int r, c are the location of the piece. int w, h are the width and height
     public static ArrayList<ArrayList<Piece>> moveDown(ArrayList<ArrayList<Piece>> grid, int r, int c, int w, int h, Piece p){
    	 int i = r+h;
        for(int j = c; j < c+w; j++){
	    	grid.get(i).set(j, p);
	        grid.get(i-h).set(j, null);
        }
        
        return grid;
    }

    //returns grid, grid with piece moved right. 
    //int r, c are the location of the piece. int w, h are the width and height
     public static ArrayList<ArrayList<Piece>> moveRight(ArrayList<ArrayList<Piece>> grid, int r, int c, int w, int h, Piece p){
        int i = c+w;
        for(int j = r; j < r+h; j++){
        	grid.get(j).set(i, p);
        	grid.get(j).set(i-w, null);
        }
        
        return grid;
    }

    //returns grid, grid with piece moved left. 
    //int r, c are the location of the piece. int w, h are the width and height
     public static ArrayList<ArrayList<Piece>> moveLeft(ArrayList<ArrayList<Piece>> grid, int r, int c, int w, int h, Piece p){
        int i = c-1;
        for(int j = r; j < r+h; j++){
        	grid.get(j).set(i, p);
        	grid.get(j).set(i+w, null);
        }
        return grid;
    }
}
