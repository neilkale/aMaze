package aMaze;

public class Solver {	
	int counter = 0;
	private Maze m;
	private int x;
	private int y;
	private int[] endPt;
	private char dir;
	private char wallDir;

	Solver(Maze m, int[] startPt, int[] endPt) {
		this.m = m;
		this.x = startPt[0];
		this.y = startPt[1];
		this.endPt = endPt;
		dir = 'e';
		m.setPoint(x, y, 2);
	}
	
	public void nextStep() {
		if(isSolved() == false) {
		if (gapInWallExists() == true) {
			travel(wallDir);
		}
		else if (channelDirExists() == true) {
			travel(dir);
		}
		else rotate();
		}
	}

	public Maze wallFollower() {
		/*if(new int[]{x,y} == endPt) {
			return m;
		}
		getWallDir();
		if (gapInWallExists() == true) {
			travel(wallDir);
			breadcrumb();
		}
		else if (channelDirExists() == true) {
			travel(dir);
			breadcrumb();
		}
		else rotate();
		System.out.println(counter++);
		return wallFollower();*/
		while(isSolved() == false)	{
			getWallDir();
			if (gapInWallExists() == true) {
				travel(wallDir);
			}
			else if (channelDirExists() == true) {
				travel(dir);
			}
			else rotate();
			counter++;
//			m.printMaze(); to see each step
		}
		System.out.println("This operation took " + counter + " steps");
		return m;
	}

	public void rotate() {
		if(dir == 's') dir = 'e';
		else if(dir == 'n') dir = 'w';
		else if(dir == 'e') dir = 'n';
		else if(dir == 'w') dir = 's';
	}

	public void travel(char travelDir) {
		int oldX = x;
		int oldY = y;
		if(travelDir == 's') y++;
		if(travelDir == 'n') y--;
		if(travelDir == 'e') x++;
		if(travelDir == 'w') x--;
		if(m.getPoint(x, y) == 2) decrumb(oldX, oldY);
		breadcrumb();
		dir = travelDir;
	}

	public void getWallDir() {
		if(dir == 's') wallDir = 'w';
		if(dir == 'n') wallDir = 'e';
		if(dir == 'e') wallDir = 's';
		if(dir == 'w') wallDir = 'n';
	}

	public boolean gapInWallExists() {
		boolean exists = false;
		if (wallDir == 'w' && x - 1 >= 0 && m.getPoint(x-1, y) != 0) exists = true;
		if (wallDir == 'e' && x + 1 < m.getSizeX() && m.getPoint(x+1, y) != 0) exists = true;
		if (wallDir == 'n' && y - 1 >= 0 && m.getPoint(x, y-1) != 0) exists = true;
		if (wallDir == 's' && y + 1 < m.getSizeY() && m.getPoint(x, y+1) != 0) exists = true;
		return exists;
	}

	public boolean channelDirExists() {
		boolean exists = false;
		if (dir == 'w' && x - 1 >= 0 && m.getPoint(x-1, y) != 0) exists = true;
		if (dir == 'e' && x + 1 < m.getSizeX() && m.getPoint(x+1, y) != 0) exists = true;
		if (dir == 'n' && y - 1 >= 0 && m.getPoint(x, y-1) != 0) exists = true;
		if (dir == 's' && y + 1 < m.getSizeY() && m.getPoint(x, y+1) != 0) exists = true;
		return exists;
	}

	public void breadcrumb() {
		m.setPoint(x, y, 2);
	}

	public void decrumb(int x, int y) {
		m.setPoint(x, y, 1);
	}
	
	public boolean isSolved() {
		if(x != endPt[0] || y != endPt[1]) return false;
		else return true;
	}

	//Getters and Setters
	
	public Maze getM() {
		return m;
	}

	public void setM(Maze m) {
		this.m = m;
	}

}