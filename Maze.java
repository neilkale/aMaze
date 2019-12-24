package aMaze;

import java.util.Random;

public class Maze {

	/**
	 * The dimensions of the maze
	 */
	private int sizeX;
	private int sizeY;
	
	/**
	 * The maze object itself.
	 * Note that it is non-static.
	 */
	private int[][] maze;

	/**
	 * Creates a new maze with the given dimensions.
	 * 
	 * Future extension: this maze constructor could be overloaded with another constructor 
	 * that can take in an entrance and exit coord. Either within the constructor, or a separate
	 * class, those two points could be drilled to make a classic maze - with entrance and exit.
	 * 
	 * @param length
	 * @param height
	 */
	Maze(int length, int height) 
	{
		sizeX = 2*length + 1;
		sizeY = 2*height + 1;
		maze = new int[sizeX][sizeY];
		maze = createMaze();
	}

	/**
	 * 
	 * @return a maze randomly generated by RBM 
	 */
	private int[][] createMaze() {
		return drill(1, 1);
	}
	
	/*************** Getters and Setters *****************/

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int[][] getMaze() {
		return maze;
	}
	
	public int getPoint(int x, int y) {
		return maze[x][y];
	}
	
	/**
	 * Sets a certain point in a maze to a certain value
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @param k new value of the point
	 */
	public void setPoint(int x, int y, int k) {
		this.maze[x][y] = k;
	}

	public void setMaze(int[][] maze) {
		this.maze = maze;
	}
	
	public void printMaze() {
		for (int i = 0; i < sizeY; i++) {
			for (int j = 0; j < sizeX; j++) {
				if (maze[j][i] == 1) System.out.print("  ");
				else if (maze[j][i] == 2) System.out.print("@ ");
				else System.out.print("+ ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printMazeRaw() {
		for (int i = 0; i < sizeY; i++) {
			for (int j = 0; j < sizeX; j++) {
				if (maze[j][i] == 1) System.out.print(1);
				else if (maze[j][i] == 2) System.out.print(2);
				else System.out.print(0);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/********** Maze Generation Algorithm: Recursive Backtracker *********/

	/*
	 * Make the initial cell the current cell, and mark it as visited
	 * While there are unvisited cells:
	 * 
	 * 	If the current cell has unvisited neighbors:
	 * 		1. Randomly choose one of the unvisited neighbors
	 * 		2. Push the current cell to the stack
	 * 		3. Remove the wall between the current cell and the chosen cell
	 * 		4. Make the chosen cell the current cell and mark it as visited
	 * 
	 * 	Else if stack isn't empty:
	 * 		1. Pop a cell from the stack
	 * 		2. Make it the current cell
	 */
	
	// recursive algorithm
	private int[][] drill(int xPos, int yPos) {
		int x = xPos;
		int y = yPos;

		while (anyDirExists(x,y) == true) {
			String priority = dirPriority();
			for(int i = 0; i < priority.length(); i++) {
				if (dirExists(x, y, 'n') == true && priority.charAt(i) == '1') {
					maze[x][y+1] = 1;
					maze[x][y+2] = 1;
					y += 2;
					drill(x, y);
				}
				if (dirExists(x, y, 's') == true && priority.charAt(i) == '2') {
					maze[x][y-1] = 1;
					maze[x][y-2] = 1;
					y += -2;
					drill(x, y);
				}
				if (dirExists(x, y, 'e') == true && priority.charAt(i) == '3') {
					maze[x+1][y] = 1;
					maze[x+2][y] = 1;
					x += 2;
					drill(x, y);
				}
				if (dirExists(x, y, 'w') == true && priority.charAt(i) == '4') {
					maze[x-1][y] = 1;
					maze[x-2][y] = 1;
					x += -2;
					drill(x, y);
				}
			}
		}
		return maze;
	}


	//checks whether the "drill" can go in a direction from its current location 
	public boolean dirExists(int x, int y, char dir) {
		boolean exists = false;
		if (x - 2 > 0 && maze[x-2][y] == 0 && dir == 'w') exists = true;
		if (x + 2 < sizeX && maze[x+2][y] == 0 && dir == 'e') exists = true;
		if (y - 2 > 0 && maze[x][y-2] == 0 && dir == 's') exists = true;
		if (y + 2 < sizeY && maze[x][y+2] == 0 && dir == 'n') exists = true;
		return exists;
	}

	//checks whether the drill can go in any direction at all from its current point
	public boolean anyDirExists(int x, int y) {
		boolean exists = false;
		if (x - 2 > 0 && maze[x-2][y] == 0) exists = true;
		if (x + 2 < sizeX && maze[x+2][y] == 0) exists = true;
		if (y - 2 > 0 && maze[x][y-2] == 0) exists = true;
		if (y + 2 < sizeY && maze[x][y+2] == 0) exists = true;
		return exists;
	}

	//sets a random travel priority (which direction will it try first/second/etc)
	public static String dirPriority() {
		String order = "";
		Random r = new Random();
		for (int i = 1; i <= 4; i++) {
			boolean dirPlace = r.nextBoolean();
			if (dirPlace == true) order = order + Integer.toString(i);
			else order = Integer.toString(i) + order;
		}
		return order;
	}

}
