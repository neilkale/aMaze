import java.util.ArrayList;

import java.util.*;

public class aStar {

	int counter = 0;
	private Maze m;
	private Integer[] startPt;
	private Integer[] endPt;
	private Integer i;
	private Integer j;

	public Maze getM() {
		return this.m;
	}

	// A* Search Algorithm (From https://www.geeksforgeeks.org/a-search-algorithm/)
	// 1. Initialize the open list
	// 2. Initialize the closed list
	// put the starting node on the open
	// list (you can leave its f at zero)
	//
	// 3. while the open list is not empty
	// a) find the node with the least f on
	// the open list, call it "q"
	//
	// b) pop q off the open list
	//
	// c) generate q's 8 successors and set their
	// parents to q
	//
	// d) for each successor
	// i) if successor is the goal, stop search
	// successor.g = q.g + distance between
	// successor and q
	// successor.h = distance from goal to
	// successor (This can be done using many
	// ways, we will discuss three heuristics-
	// Manhattan, Diagonal and Euclidean
	// Heuristics)
	//
	// successor.f = successor.g + successor.h
	//
	// ii) if a node with the same position as
	// successor is in the OPEN list which has a
	// lower f than successor, skip this successor
	//
	// iii) if a node with the same position as
	// successor is in the CLOSED list which has
	// a lower f than successor, skip this successor
	// otherwise, add the node to the open list
	// end (for loop)
	//
	// e) push q on the closed list
	// end (while loop)

	aStar(Maze m, Integer[] startPt, Integer[] endPt) {
		this.m = m;
		this.startPt = startPt;
		this.endPt = endPt;
		i = startPt[0];
		j = startPt[1];
	}

	private ArrayList<Point> openListP;
	private ArrayList<Double> openListD;
	private PointInfo[][] ptInfo;
	private boolean[][] closedList;
	boolean foundDest;

	public Maze solver() {

		closedList = new boolean[m.getSizeX()][m.getSizeY()];
		ptInfo = new PointInfo[m.getSizeX()][m.getSizeY()];

		for (int i = 0; i < ptInfo.length; i++) {
			for (int j = 0; j < ptInfo[0].length; j++) {
				ptInfo[i][j] = new PointInfo();
				ptInfo[i][j].f = Double.MAX_VALUE;
				ptInfo[i][j].g = Double.MAX_VALUE;
				ptInfo[i][j].h = Double.MAX_VALUE;
				ptInfo[i][j].parent_x = -1;
				ptInfo[i][j].parent_y = -1;
			}
		}

		ptInfo[i][j].f = 0.0;
		ptInfo[i][j].g = 0.0;
		ptInfo[i][j].h = 0.0;
		ptInfo[i][j].parent_x = i;
		ptInfo[i][j].parent_y = j;

		openListP = new ArrayList<Point>();
		openListD = new ArrayList<Double>();

		openListP.add(new Point(i, j));
		openListD.add(ptInfo[i][j].f);

		foundDest = false;

		while (!openListP.isEmpty()) {

			Point p = openListP.get(0);
			openListP.remove(0);
			Double pF = openListD.get(0);
			openListD.remove(0);

			i = p.x;
			j = p.y;

			closedList[i][j] = true;
			counter++;

			// Generating successors

			double gNew, hNew, fNew;

			// North (N)
			int norther = j - 1;

			// is the cell in the maze
			if (isValid(i, norther)) {

				// is this the destination
				if (isDestination(i, norther)) {
					// set the parent of destination
					ptInfo[i][norther].parent_x = i;
					ptInfo[i][norther].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
					return updateMaze(closedList);
				}
				// else if (closedList[i][norther]) {
				// closedList[i][norther] = false;
				// }
				else if (closedList[i][norther] == false && isUnblocked(i, norther) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(i, norther);
					fNew = gNew + hNew;
					if (ptInfo[i][norther].f == Double.MAX_VALUE || ptInfo[i][norther].f > fNew) {
						openListP.add(new Point(i, norther));
						openListD.add(fNew);
						ptInfo[i][norther].f = fNew;
						ptInfo[i][norther].g = gNew;
						ptInfo[i][norther].h = hNew;
						ptInfo[i][norther].parent_x = i;
						ptInfo[i][norther].parent_y = j;
					}
				}

			}

			// South (S)
			int souther = j + 1;

			// is the cell in the maze
			if (isValid(i, souther)) {

				// is this the destination
				if (isDestination(i, souther)) {
					// set the parent of destination
					ptInfo[i][souther].parent_x = i;
					ptInfo[i][souther].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
					return updateMaze(closedList);
				}
				// else if (closedList[i][souther]) {
				// closedList[i][souther] = false;
				// }
				else if (closedList[i][souther] == false && isUnblocked(i, souther) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(i, souther);
					fNew = gNew + hNew;
					if (ptInfo[i][souther].f == Double.MAX_VALUE || ptInfo[i][souther].f > fNew) {
						openListP.add(new Point(i, souther));
						openListD.add(fNew);
						ptInfo[i][souther].f = fNew;
						ptInfo[i][souther].g = gNew;
						ptInfo[i][souther].h = hNew;
						ptInfo[i][souther].parent_x = i;
						ptInfo[i][souther].parent_y = j;
					}
				}

			}

			// East (E)
			int easter = i + 1;

			// is the cell in the maze
			if (isValid(easter, j)) {

				// is this the destination
				if (isDestination(easter, j)) {
					// set the parent of destination
					ptInfo[easter][j].parent_x = i;
					ptInfo[easter][j].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
					return updateMaze(closedList);
				}
				// else if (closedList[easter][j]) {
				// closedList[easter][j] = false;
				// }
				else if (closedList[easter][j] == false && isUnblocked(easter, j) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(easter, j);
					fNew = gNew + hNew;
					if (ptInfo[easter][j].f == Double.MAX_VALUE || ptInfo[easter][j].f > fNew) {
						openListP.add(new Point(easter, j));
						openListD.add(fNew);
						ptInfo[easter][j].f = fNew;
						ptInfo[easter][j].g = gNew;
						ptInfo[easter][j].h = hNew;
						ptInfo[easter][j].parent_x = i;
						ptInfo[easter][j].parent_y = j;
					}
				}

			}

			// West (W)
			int wester = i - 1;

			// is the cell in the maze
			if (isValid(wester, j)) {

				// is this the destination
				if (isDestination(wester, j)) {
					// set the parent of destination
					ptInfo[wester][j].parent_x = i;
					ptInfo[wester][j].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
					return updateMaze(closedList);
				}
				// else if (closedList[wester][j]) {
				// closedList[wester][j] = false;
				// }
				else if (closedList[wester][j] == false && isUnblocked(wester, j) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(wester, j);
					fNew = gNew + hNew;
					if (ptInfo[wester][j].f == Double.MAX_VALUE || ptInfo[wester][j].f > fNew) {
						openListP.add(new Point(wester, j));
						openListD.add(fNew);
						ptInfo[wester][j].f = fNew;
						ptInfo[wester][j].g = gNew;
						ptInfo[wester][j].h = hNew;
						ptInfo[wester][j].parent_x = i;
						ptInfo[wester][j].parent_y = j;
					}
				}

			}

		}

		if (foundDest == false) {
			System.out.println("Destination is unreachable.");
		}
		return updateMaze(closedList);
	}

	public void stepByStepInit() {
		closedList = new boolean[m.getSizeX()][m.getSizeY()];
		ptInfo = new PointInfo[m.getSizeX()][m.getSizeY()];

		for (int i = 0; i < ptInfo.length; i++) {
			for (int j = 0; j < ptInfo[0].length; j++) {
				ptInfo[i][j] = new PointInfo();
				ptInfo[i][j].f = Double.MAX_VALUE;
				ptInfo[i][j].g = Double.MAX_VALUE;
				ptInfo[i][j].h = Double.MAX_VALUE;
				ptInfo[i][j].parent_x = -1;
				ptInfo[i][j].parent_y = -1;
			}
		}

		ptInfo[i][j].f = 0.0;
		ptInfo[i][j].g = 0.0;
		ptInfo[i][j].h = 0.0;
		ptInfo[i][j].parent_x = i;
		ptInfo[i][j].parent_y = j;

		openListP = new ArrayList<Point>();
		openListD = new ArrayList<Double>();

		openListP.add(new Point(i, j));
		openListD.add(ptInfo[i][j].f);

		foundDest = false;
	}

	public Maze getMaze() {
		return updateMaze(closedList);
	}

	public void nextStep() {
		if (!openListP.isEmpty()) {

			Point p = openListP.get(0);
			openListP.remove(0);
			Double pF = openListD.get(0);
			openListD.remove(0);

			i = p.x;
			j = p.y;

			closedList[i][j] = true;

			// Generating successors

			double gNew, hNew, fNew;

			// North (N)
			int norther = j - 1;

			// is the cell in the maze
			if (isValid(i, norther)) {

				// is this the destination
				if (isDestination(i, norther)) {
					// set the parent of destination
					ptInfo[i][norther].parent_x = i;
					ptInfo[i][norther].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
				}
				// else if (closedList[i][norther]) {
				// closedList[i][norther] = false;
				// }
				else if (closedList[i][norther] == false && isUnblocked(i, norther) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(i, norther);
					fNew = gNew + hNew;
					if (ptInfo[i][norther].f == Double.MAX_VALUE || ptInfo[i][norther].f > fNew) {
						openListP.add(new Point(i, norther));
						openListD.add(fNew);
						ptInfo[i][norther].f = fNew;
						ptInfo[i][norther].g = gNew;
						ptInfo[i][norther].h = hNew;
						ptInfo[i][norther].parent_x = i;
						ptInfo[i][norther].parent_y = j;
					}
				}

			}

			// South (S)
			int souther = j + 1;

			// is the cell in the maze
			if (isValid(i, souther)) {

				// is this the destination
				if (isDestination(i, souther)) {
					// set the parent of destination
					ptInfo[i][souther].parent_x = i;
					ptInfo[i][souther].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
				}
				// else if (closedList[i][souther]) {
				// closedList[i][souther] = false;
				// }
				else if (closedList[i][souther] == false && isUnblocked(i, souther) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(i, souther);
					fNew = gNew + hNew;
					if (ptInfo[i][souther].f == Double.MAX_VALUE || ptInfo[i][souther].f > fNew) {
						openListP.add(new Point(i, souther));
						openListD.add(fNew);
						ptInfo[i][souther].f = fNew;
						ptInfo[i][souther].g = gNew;
						ptInfo[i][souther].h = hNew;
						ptInfo[i][souther].parent_x = i;
						ptInfo[i][souther].parent_y = j;
					}
				}

			}

			// East (E)
			int easter = i + 1;

			// is the cell in the maze
			if (isValid(easter, j)) {

				// is this the destination
				if (isDestination(easter, j)) {
					// set the parent of destination
					ptInfo[easter][j].parent_x = i;
					ptInfo[easter][j].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
				}
				// else if (closedList[easter][j]) {
				// closedList[easter][j] = false;
				// }
				else if (closedList[easter][j] == false && isUnblocked(easter, j) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(easter, j);
					fNew = gNew + hNew;
					if (ptInfo[easter][j].f == Double.MAX_VALUE || ptInfo[easter][j].f > fNew) {
						openListP.add(new Point(easter, j));
						openListD.add(fNew);
						ptInfo[easter][j].f = fNew;
						ptInfo[easter][j].g = gNew;
						ptInfo[easter][j].h = hNew;
						ptInfo[easter][j].parent_x = i;
						ptInfo[easter][j].parent_y = j;
					}
				}

			}

			// West (W)
			int wester = i - 1;

			// is the cell in the maze
			if (isValid(wester, j)) {

				// is this the destination
				if (isDestination(wester, j)) {
					// set the parent of destination
					ptInfo[wester][j].parent_x = i;
					ptInfo[wester][j].parent_y = j;
					System.out.println("destination found");
					foundDest = true;
				}
				// else if (closedList[wester][j]) {
				// closedList[wester][j] = false;
				// }
				else if (closedList[wester][j] == false && isUnblocked(wester, j) == true) {
					gNew = ptInfo[i][j].g + 1.0;
					hNew = calculateH(wester, j);
					fNew = gNew + hNew;
					if (ptInfo[wester][j].f == Double.MAX_VALUE || ptInfo[wester][j].f > fNew) {
						openListP.add(new Point(wester, j));
						openListD.add(fNew);
						ptInfo[wester][j].f = fNew;
						ptInfo[wester][j].g = gNew;
						ptInfo[wester][j].h = hNew;
						ptInfo[wester][j].parent_x = i;
						ptInfo[wester][j].parent_y = j;
					}
				}

			}

		}
	}

	public boolean isSolved() {
		if (i != endPt[0] || j != endPt[1])
			return false;
		else
			return true;
	}

	// is pt in the maze?
	public boolean isValid(int[] pt) {
		if (pt[0] < m.getSizeX() && pt[0] >= 0) {
			if (pt[1] < m.getSizeY() && pt[0] >= 0) {
				return true;
			}
		}
		return false;
	}

	public boolean isValid(int x, int y) {
		int[] pt = new int[] { x, y };
		if (pt[0] < m.getSizeX() && pt[0] >= 0) {
			if (pt[1] < m.getSizeY() && pt[0] >= 0) {
				return true;
			}
		}
		return false;
	}

	// is pt not a wall?
	public boolean isUnblocked(Integer[] pt) {
		if (m.getPoint(pt) == 0)
			return false;
		else
			return true;
	}

	public boolean isUnblocked(int x, int y) {
		int[] pt = new int[] { x, y };
		if (m.getPoint(pt) == 0)
			return false;
		else
			return true;
	}

	// is pt the end point?
	public boolean isDestination(Integer[] pt) {
		if (Arrays.equals(pt, endPt))
			return true;
		else
			return false;
	}

	public boolean isOrigin(Integer[] pt) {
		if (Arrays.equals(pt, startPt))
			return true;
		else
			return false;
	}

	public boolean isDestination(int x, int y) {
		Integer[] pt = new Integer[] { x, y };
		if (Arrays.equals(pt, endPt))
			return true;
		else
			return false;
	}

	// distance to end point using Manhattan heuristic
	public Double calculateH(Integer[] pt) {
		int a = pt[0];
		int b = pt[1];
		// return Math.sqrt(Math.pow(m.getSizeX()-a, 2) * Math.pow(m.getSizeY()-b, 2));
		// euclidean heuristic
		return (double) (Math.abs(m.getSizeX() - a) + Math.abs(m.getSizeY() - b));
	}

	public Double calculateH(int a, int b) {
		// return Math.sqrt(Math.pow(m.getSizeX()-a, 2) * Math.pow(m.getSizeY()-b, 2));
		// euclidean heuristic
		return (double) (Math.abs(m.getSizeX() - a) + Math.abs(m.getSizeY() - b));
	}

	public Maze updateMaze(boolean[][] closedList) {
		// for (int i = 0; i < closedList.length; i++) {
		// for (int j = 0; j < closedList[0].length; j++) {
		// if (closedList[i][j] == true)
		// m.setPoint(i, j, 2);
		// }
		// m.setPoint(endPt[0], endPt[1], 2);
		// }
		boolean startReached = false;
		int x = endPt[0];
		int y = endPt[1];
		while (startReached == false) {
			m.setPoint(x, y, 2);
			x = ptInfo[x][y].parent_x;
			y = ptInfo[x][y].parent_y;
			if (isOrigin(new Integer[] { x, y }))
				startReached = true;
		}
		return m;
	}

	// check whether the corners are in the maze

	public Point point(int x, int y) {
		Point z = new Point(x, y);
		return z;
	}

	public class Point {

		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Point() {

		}

	}

	public class PointInfo {

		double f;
		double g;
		double h;
		int parent_x;
		int parent_y;

		PointInfo(double f, double g, double h, int p_x, int p_y) {
			this.f = f;
			this.g = g;
			this.h = h;
			this.parent_x = p_x;
			this.parent_y = p_y;
		}

		PointInfo() {

		}

	}
}
