
import java.awt.Graphics;
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

	public Maze solved(Maze M, String algo) {
		Maze m = new Maze(M);
		WallFollower wf = new WallFollower(m, new int[]{1,1}, new int[]{m.getSizeX() - 2, m.getSizeY() - 2});
		aStar astar = new aStar(m, new Integer[]{1,1}, new Integer[]{m.getSizeX() - 2, m.getSizeY() - 2});
		if(algo.equals("wf")) {
			double start = System.nanoTime();
			Maze ret = wf.solver();
			double end = System.nanoTime();
			System.out.println("Wall-Follower Algorithm took: " + (end-start)/1000000000 + " seconds");
			return ret;
		} 
		else if(algo.equals("a*")) {
			double start = System.nanoTime();
			Maze ret = astar.solver();
			double end = System.nanoTime();
			System.out.println("A-Star Algorithm took: " + (astar.counter) + " steps");
			System.out.println("A-Star Algorithm took: " + (end-start)/1000000000 + " seconds");
			return ret;
		} 
		else {
		System.out.println("Please enter a valid algorithm encoding!");
		System.out.println("A-Star: a*");
		System.out.println("Wall-Follower: wf");
		return m;
		}
	}
	
	public void printSolved(Maze m, String algo) {
		Application a = new Application(m);
		if(algo.equals("wf")) {
			WallFollower wf = new WallFollower(m, new int[]{1,1}, new int[]{m.getSizeX() - 2, m.getSizeY() - 2});
			double start = System.nanoTime();
			Maze ret = wf.solver();
			double end = System.nanoTime();
			System.out.println("Wall-Follower Algorithm took: " + (end-start)/1000000000 + " seconds");
			a.setM(wf.getM());
			a.repaint();
		} 
		else if(algo.equals("a*")) {
			aStar astar = new aStar(m, new Integer[]{1,1}, new Integer[]{m.getSizeX() - 2, m.getSizeY() - 2});
			double start = System.nanoTime();
			Maze ret = astar.solver();
			double end = System.nanoTime();
			System.out.println("A-Star Algorithm took: " + (astar.counter) + " steps");
			System.out.println("A-Star Algorithm took: " + (end-start)/1000000000 + " seconds");
			a.setM(astar.getM());
			a.repaint();
		} 
		else {
		System.out.println("Please enter a valid algorithm encoding!");
		System.out.println("A-Star: a*");
		System.out.println("Wall-Follower: wf");
		}
	}

	public void playMaze(Maze m, String algo, int delayMs) {
		Application a = new Application(m);
		if(algo == "wf") {
			WallFollower wf = new WallFollower(m, new int[]{1,1}, new int[]{m.getSizeX() - 2, m.getSizeY() - 2});
			while(!wf.isSolved()) {
				try {
					Thread.sleep(delayMs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				wf.nextStep();
				a.repaint();
				a.setM(wf.getM());
			}
			a.repaint();
		}
		else if(algo == "a*") {
			aStar astar = new aStar(m, new Integer[]{1,1}, new Integer[]{m.getSizeX() - 2, m.getSizeY() - 2});
			astar.stepByStepInit();
			while(astar.foundDest == false) {
				try {
					Thread.sleep(delayMs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				astar.nextStep();
			}
			a.setM(astar.getM());
			a.repaint();
		}
	}
	
}
