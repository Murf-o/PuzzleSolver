package com.SlidingBlock.PuzzleSolver.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

public class Solver extends Grid{
	private Snapshot root; //initial grid
	private Set<String> arrangements; 
	private int totalMoves;
	private Snapshot solution;
	
	public Solver(int rowSize, int colSize){
		super(rowSize, colSize);
        root = null;
        solution = null;
        this.arrangements = new HashSet<String>();
      }
	
	//returns a list of moves. returns null if a solution doesn't exist
    public PuzzleAns solvePuzzle(){
      SolutionFound solutionFound = new SolutionFound();
      ArrayList<String> moveListAns;
      //check if already solved
      if(isSolution(this.getGrid())) {
    	  moveListAns = new ArrayList<String>();
    	  moveListAns.add("This puzzle is already solved, no need for any moves");
    	  PuzzleAns answers = new PuzzleAns(this.getGrid(), moveListAns);
    	  return answers;
      }
      
      root = new Snapshot(0, pList, grid, rowSize, colSize);
      Snapshot cur;
      Queue<Snapshot> q = new LinkedList<Snapshot>();
      q.add(root);

      while(!q.isEmpty()){
        cur = q.poll();
        ArrayList<Snapshot> snaps = cur.createAllPossibleMoves(solutionFound);
        if(solutionFound.getSolutionFound()) {//SOLUTION found 
          moveListAns = snaps.get(0).getMoveList(); 
          totalMoves = snaps.get(0).getTotalMoves(); 
          solution = snaps.get(0);
          cur = null;
          while(!q.isEmpty()) {q.poll();} //delete snaps from queue
          
          PuzzleAns answers = new PuzzleAns(solution.getGrid(), moveListAns);
          return answers;
        } /////////////
        int size = snaps.size();
        //System.out.println("snaps: " + snaps);
        for(int i = 0; i < size; i++) {   //push new snaps into queue
        	
        	//System.out.println("i: " + i);
          if(!arrangements.contains(snaps.get(i).toString())){  //only push snaps that haven't already been made before
              q.add(snaps.get(i));
              arrangements.add(snaps.get(i).toString());
          }
          //else{delete snaps[i];}  //delete the ones that have been made
        }
      }
      //NO SOLUTION
      return null;
    }
    
  //addblock to grid
    public int add(String pieceName, int r, int c, int w, int h, String movement){
      int handler = validPlacement(pieceName, r, c, w, h, movement);
      if(handler != 1){ return handler;}  //return errorHandler
      addBlock(pieceName, r, c, w, h, movement);
      return 1; //SUCCESS
    }
    
  //called in from 'add' method, used to check if block was placed correctly
    //  -1:  falls outside grid
    //  -2:  invalid movement
    //  -3: overlaps with other piece
    public int validPlacement(String pieceName, int r, int c, int w, int h, String movement){
      if(r >= rowSize || c >= colSize || r+h-1 >= rowSize || c+w-1 >= colSize || r < 0 || c < 0){return -1;}
      else if(!movement.equals("b") && !movement.equals("v") && !movement.equals("h") && !movement.equals("n")){return -2;}
      else{
        for(int i = 0; i < w; i++){  //width
            if(grid.get(r).get(c+i) != null) {return -3;}
        } 
        for(int i = 0; i < h; i++){  //height
            if(grid.get(r+i).get(c) != null) {return -3;}
        } 
      }
      return 1; //VALID
    }
    
    int getTotalMoves() {return this.totalMoves;}
    
  //returns true if grid is a solution to the puzzle, false otherwise
    public boolean isSolution(ArrayList<ArrayList<Piece>> g){
      for(int i = 0; i < rowSize; i++){
        if(g.get(i).get(colSize-1) != null && g.get(i).get(colSize-1).getName().equals("Z"))  {return true;}
      }
      return false;
    }
}

