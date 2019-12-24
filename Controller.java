package aMaze;

import java.util.ArrayList;

/**
 * Represents the collection of mazes created.
 * @author Neil Kale
 *
 */

public class Controller {

	/**
	 * A list containing the currently created mazes
	 */
	private ArrayList<Maze> list;

	/**
	 * @return the entire list
	 */
	
	public ArrayList<Maze> getList() {
		return list;
	}
	
	/**
	 * @param a location in the list (index)
	 * @return the maze located at the specified index
	 */
	public Maze getList(int a) {
		return list.get(a);
	}

	/**
	 * This function really never should be used, 
	 * unless a maze is being transferred from one controller to another
	 * @param list another list of Mazes
	 */
	public void setList(ArrayList<Maze> list) {
		this.list = list;
	}

	/**
	 * Constructor method for a Controller
	 */
	Controller() {
		list = new ArrayList<Maze>();
	}
	
	/**
	 * This method creates a maze and appends it to the list
	 * @param x width of the maze to be created
	 * @param y height of the maze to be created
	 */
	public void addMaze(int x, int y) {
		Maze m = new Maze(x,y);
		list.add(m);
	}
	
	public Maze solved(Maze m) {
		Solver s = new Solver(m, new int[]{1,1}, new int[]{m.getSizeX() - 2, m.getSizeY() - 2});
		return s.wallFollower();
	}
	
	public void playMaze(Maze m) {
		Solver s = new Solver(m, new int[]{1,1}, new int[]{m.getSizeX() - 2, m.getSizeY() - 2});
		Application a = new Application(m);
		while(!s.isSolved()) {
			s.nextStep();
			a.drawMaze();
			a.setM(s.getM());
		}
	}
}
