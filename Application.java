package aMaze;

import processing.core.*;

public class Application extends PApplet {
	
	private Maze m;
	
	public Maze getM() {
		return m;
	}

	public void setM(Maze m) {
		this.m = m;
	}

	Application(Maze m) {
		this.m = m;
	}
	
	static public void main(String[] args) {
		System.out.println(Application.class.getName());
		  PApplet.main(Application.class.getName());
		}
	
	public void settings() {
		  size(800, 600);
		}
	
	public void setup() {
		background(0);
	}
	
	public void drawMaze() {
//		int[][] arr = m.getMaze();
//		for(int i = 0; i < m.getSizeY(); i++) {
//			for(int j = 0; j < m.getSizeX(); j++) {
//				
//			}
//		}
		m.printMaze();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
}