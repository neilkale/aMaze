package aMaze;

/**
 * Test other classes here as follows:
 * Create a new controller
 * Create a maze in that controller
 * Create a new solver
 * 
 * @author Neil Kale
 *
 */

public class Tester {

	public static void main(String[] args) {
		long start = System.nanoTime();
		Controller c = new Controller();
		
//		for(int i = 0; i < 5; i++) {
//			c.addMaze(5+i, 5+i);
//		}
		
		c.addMaze(10,10);
		
//		c.getList(0).printMazeRaw();
//		c.solved(c.getList(0)).printMaze();
		c.playMaze(c.getList(0));
		long end = System.nanoTime();
		
		System.out.println("This operation took " + (end - start) + " nanoseconds (" + (double) (end-start)/1000000000 + " sec)");
	}

}
