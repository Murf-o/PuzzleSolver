package com.SlidingBlock.PuzzleSolver.logic;

import java.util.ArrayList;

public class Snapshot {
	private int totalMoves;
	private ArrayList<Piece> pList;  //ArrayList of all pieces in list, used to check if moveable, then move -- same for all objects
	private int rowSize;
	private int colSize;
	private ArrayList<String> moveList;
	private ArrayList<ArrayList<Piece>> grid;
	
	public Snapshot(int totalMoves, ArrayList<Piece> pList, ArrayList<ArrayList<Piece>> grid, int r, int c){
	      this.totalMoves = totalMoves;
	      this.pList = new ArrayList<Piece>(pList);
	      this.rowSize = r;
	      this.colSize = c;
	      this.moveList = new ArrayList<String>();
	      this.grid = copyList(grid);
	 }

	    //constructor with moveList
	public Snapshot(int totalMoves, ArrayList<String> moveList, ArrayList<ArrayList<Piece>> grid, ArrayList<Piece> pList, int r, int c){
	      this.totalMoves = totalMoves;
	      this.pList = new ArrayList<Piece>(pList);
	      this.moveList = new ArrayList<String>(moveList);
	      this.grid = copyList(grid);
	      this.rowSize = r;
	      this.colSize = c;
	 }


	 public void setRowColSize(int r, int c){
	      rowSize = r;
	      colSize = c;
	 }

	    //sets Plist
	 public void setPlist(ArrayList<Piece> pList){
	      this.pList = pList;
	 }
	 
	//creates all new possible snaps from current snap. If it returns ArrayList of size 1, it's possible that that snapshot is a solution
	 public ArrayList<Snapshot> createAllPossibleMoves(SolutionFound solution){
	      ArrayList<Snapshot> newSnaps = new ArrayList<Snapshot>();
	      Snapshot sTmp;
	      ArrayList<ArrayList<Piece>> gridCpy;
	      Piece p;
	      int size = pList.size();
	      ArrayList<String> movesTmp;
	      for(int i = 0; i < size; i++){
	        p = pList.get(i);
	        int r, c;
	        int[] arr = findPiece(p);   //find piece location -- r and c are passed by reference
	        r = arr[0]; c = arr[1];
	        gridCpy = this.grid;
	        //if can move up block up, add to 'newSnaps', if can move down add... (do for all pieces)
	        movesTmp = new ArrayList<String>(this.moveList);
	        int movesTowardsDirection = 0;  //used to see how many moves a block moved in one direction
	        //if its a block that moves both ways
	        //System.out.println("original grid");
	        //printGrid(this.grid);
	        if(p.moveBoth()){
	         //System.out.println("move both: " + p.getName());
	          while(possibleMoveUp(r-movesTowardsDirection, c, p.getWidth(), p.getHeight(), p)){
	            gridCpy = Movement.moveUp(copyList(gridCpy), r-movesTowardsDirection, c, p.getWidth(), p.getHeight(), p);
	            //System.out.println("Movement up");
	            //printGrid(gridCpy);
	            String str = "Piece " + p.getName() + " moves up " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            ++movesTowardsDirection;
	            movesTmp = new ArrayList<String>(this.moveList);
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	          while(possibleMoveDown(r+movesTowardsDirection, c, p.getWidth(), p.getHeight(), p)){
	            gridCpy = Movement.moveDown(copyList(gridCpy), r+movesTowardsDirection, c, p.getWidth(), p.getHeight(), p);
	            //System.out.println("Movement down");
	            //printGrid(gridCpy);
	            String str = "Piece " + p.getName() + " moves down " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            ++movesTowardsDirection;
	            movesTmp = new ArrayList<String>(this.moveList);
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	          while(possibleMoveLeft(r, c-movesTowardsDirection, p.getWidth(), p.getHeight(), p)){         
	        	  
	           gridCpy = Movement.moveLeft(copyList(gridCpy), r, c-movesTowardsDirection, p.getWidth(), p.getHeight(), p);
        	   //System.out.println("Movement left");
	           //printGrid(gridCpy);
        	   String str = "Piece " + p.getName() + " moves left " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            ++movesTowardsDirection;
	            movesTmp = new ArrayList<String>(this.moveList);
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	          
	          while(possibleMoveRight(r, c+movesTowardsDirection, p.getWidth(), p.getHeight(), p)){
	        	  
	            gridCpy = Movement.moveRight(copyList(gridCpy), r, c+movesTowardsDirection, p.getWidth(), p.getHeight(), p);
	            //System.out.println("Movement right");
	            //printGrid(gridCpy);
	            String str = "Piece " + p.getName() + " moves right " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);           
	            //check if first block was moved to a solution spot, if true return that single snap
	            if(isSolution(gridCpy)){
	              solution.solutionFound();;
	              sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	              //delete all snaps from ArrayList 'newSnaps'
	             // newSnaps = null;
	              ArrayList<Snapshot> ans = new ArrayList<Snapshot>();
	              ans.add(sTmp);
	              return ans;
	            }
	            movesTmp = new ArrayList<String>(this.moveList);
	             ++movesTowardsDirection;
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	        }
	        /////////////////////////////////////////////////////
	        //else, if it's a block that only moves vertically
	        else if(p.moveVert()){
	          while(possibleMoveUp(r-movesTowardsDirection, c, p.getWidth(), p.getHeight(), p)){
	            gridCpy = Movement.moveUp(copyList(gridCpy), r-movesTowardsDirection, c, p.getWidth(), p.getHeight(), p);
	            String str = "Piece " + p.getName() + " moves up " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            ++movesTowardsDirection;
	            movesTmp = new ArrayList<String>(this.moveList);
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	          while(possibleMoveDown(r+movesTowardsDirection, c, p.getWidth(), p.getHeight(), p)){
	            gridCpy = Movement.moveDown(copyList(gridCpy), r+movesTowardsDirection, c, p.getWidth(), p.getHeight(), p);
	            String str = "Piece " + p.getName() + " moves down " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            ++movesTowardsDirection;
	            movesTmp = new ArrayList<String>(this.moveList);
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	        }
	        /////////////////////////////////////////////
	        //else, if it's a block that only moves horizontally
	        else if(p.moveHoriz()){
	          while(possibleMoveLeft(r, c-movesTowardsDirection, p.getWidth(), p.getHeight(), p)){
	            gridCpy = Movement.moveLeft(copyList(gridCpy), r, c-movesTowardsDirection, p.getWidth(), p.getHeight(), p);
	            String str = "Piece " + p.getName() + " moves left " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            ++movesTowardsDirection;
	            movesTmp = new ArrayList<String>(this.moveList);
	          }
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	          gridCpy = this.grid;
	          while(possibleMoveRight(r, c+movesTowardsDirection, p.getWidth(), p.getHeight(), p)){
	            gridCpy = Movement.moveRight(copyList(gridCpy), r, c+movesTowardsDirection, p.getWidth(), p.getHeight(), p);
	            String str = "Piece " + p.getName() + " moves right " + (movesTowardsDirection+1) + " space(s)";
	            movesTmp.add(str);
	            sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	            newSnaps.add(sTmp);
	            //check if first block was moved to a solution spot, if true return that single snap
	             if(isSolution(gridCpy)){
	              solution.solutionFound();;
	              sTmp = new Snapshot(this.totalMoves+1, movesTmp, gridCpy, this.pList, this.rowSize, this.colSize);
	              //delete all snaps from ArrayList 'newSnaps'
	              //newSnaps = null;
	              ArrayList<Snapshot> ans = new ArrayList<Snapshot>();
	              ans.add(sTmp);
	              return ans;
	            }
	             movesTmp = new ArrayList<String>(this.moveList);
	            ++movesTowardsDirection;
	          }
	          gridCpy = this.grid;
	          movesTowardsDirection = 0;
	          movesTmp = new ArrayList<String>(this.moveList);
	        }
	        /////////////////////////////////////////////////////////
	        else{}  //cannot move piece, no new snapshots with it
	        movesTowardsDirection = 0;
	        movesTmp = this.moveList;
	      }
	      
	      return newSnaps;
	    }
	 
	//locates where a piece is and puts that value into 'r' and 'c'
	 public int[] findPiece(Piece p){
	      for(int i = 0; i < rowSize; i++){
	            for(int j = 0; j < colSize; j++){
	              if(grid.get(i).get(j) != null && grid.get(i).get(j).getName().equals(p.getName())){int[] arr = {i,j};return arr;}
	            }
	      }
	      //if somehow not found
	      System.out.println("piece: " + p.getName() + " not found.");
	      return null;
	  }
	 
	//returns whether is possible for a block to move up from its current spot
	public boolean possibleMoveUp(int r, int c, int w, int h, Piece p){
	      if(r-1 < 0)  {return false;}
	      for(int i = 0; i < w; i++){
	        if(grid.get(r-1).get(c+i) != null) {return false;} 
	      }
	      return true;
	 }

	    //returns whether is possible for a block to move down from its current spot
	  public boolean possibleMoveDown(int r, int c, int w, int h, Piece p){
	      if(r+h >= rowSize)  {return false;}
	      for(int i = 0; i < w; i++){
	        if(grid.get(r+h).get(c+i) != null) {return false;} 
	      }
	      return true;
	    }

	    //returns whether is possible for a block to move left from its current spot
	  public boolean possibleMoveLeft(int r, int c, int w, int h, Piece p){
	      if(c-1 < 0)  {return false;}
	      for(int i = 0; i < h; i++){
	        if(grid.get(r+i).get(c-1) != null) {return false;} 
	      }
	      return true;
	    }

	     //returns whether is possible for a block to move Right from its current spot
	  public boolean possibleMoveRight(int r, int c, int w, int h, Piece p){
	      if(c+w >= colSize)  {return false;}
	      for(int i = 0; i < h; i++){
	        if(grid.get(r+i).get(c+w) != null) {return false;} 
	      }
	      return true;
	    }	 
	//returns true if grid is a solution to the puzzle, false otherwise
      public boolean isSolution(ArrayList<ArrayList<Piece>> g){
        for(int i = 0; i < rowSize; i++){
          if(g.get(i).get(colSize-1) != null && g.get(i).get(colSize-1).getName().equals("Z"))  {return true;}
        }
        return false;
      }
      
      public ArrayList<String> getMoveList()  {return this.moveList;}

      public ArrayList<ArrayList<Piece>> getGrid()  {return this.grid;}

      public int getTotalMoves() {return this.totalMoves;}
      
    //converts grid to it's string equivalent
      public String toString(){
         String ans = "";
         String c;
         for(int i = 0; i < rowSize; i++){
           for(int j = 0; j < colSize; j++){
             if(grid.get(i).get(j) == null){c = " ";}
             else  {c = grid.get(i).get(j).getName();}
             ans += c;
           }
         }
         return ans;
       }
      
      // used to help debug, prints grid out
      void printGrid(ArrayList<ArrayList<Piece>> g)
      {

        for (int i = 0; i < rowSize; i++)
        {
          for (int j = 0; j < colSize; j++)
          {
            if (g.get(i).get(j) == null)
            {
            	System.out.print(".");
            }
            else
            {
            	System.out.print(g.get(i).get(j).getName());
            }
          }
          System.out.println();
        }
      }
      
      public ArrayList<ArrayList<Piece>> copyList(ArrayList<ArrayList<Piece>> grid){
    	  int size = grid.size();
    	  ArrayList<ArrayList<Piece>> ret = new ArrayList<>(size);
    	  for(int i = 0; i < size; ++i) {
    		  ret.add(new ArrayList<Piece>(grid.get(i)));
    	  }
    	  return ret;
      }
}
