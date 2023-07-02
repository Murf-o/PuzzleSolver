package com.SlidingBlock.PuzzleSolver.logic;
public class Piece {
	private String name;
    private int width;
    private int height;
    private String direction;
	
    public Piece(String name, int width, int height, String direction) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.direction = direction;
	}
	
  //determines whether the piece can only move up and down
    public boolean moveVert(){
      if(direction.equals("v"))  {return true;}
      return false;
    }

    //determines whether the piece can only move horizontally
    public boolean moveHoriz(){
      if(direction.equals("h"))  {return true;}
      return false;
    }

    //determines whether piece can move horizontally and vertically
    public boolean moveBoth(){
      if(direction.equals("b"))  {return true;}
      return false;
    }

    public int getWidth()    {return width;}

    public int getHeight()    {return height;}

    public String getName()  {return name;}
    
    
}
