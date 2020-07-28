import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Application extends JFrame implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JFrame f;
	float hueCounter = 0;
	int fX = 750;
	int fY = 750;
	private Maze m;
	MazeDrawing mD;
	int cellSize;

	public Maze getM() {
		return m;
	}

	public void setM(Maze m) {
		this.m = m;
	}

	public static void main(String[] args) {
		Controller c = new Controller();
		c.addMaze(20, 20);
		new Application(c.getList(0));
	}

	JTextField tf;
	JLabel l;
	JButton b;

	Application(Maze m) {
		setM(m);
		calcCellSize();
		initUI();
	}
	
	private void calcCellSize() {
		cellSize = fY/(m.getSizeX()+2);
	}
	
	private void initUI() {
		mD = new MazeDrawing();
		// tf = new JTextField();
		// tf.setBounds(50, 50, 150, 20);
		// l = new JLabel();
		// l.setBounds(50, 100, 250, 20);
		// b = new JButton("Find IP");
		// b.setBounds(50, 150, 95, 30);
		// b.addActionListener(this);
		// add(b);
		// add(tf);
		// add(l);
		add(mD);
		mD.setSize(fX + 50, fY + 50);
		setTitle("aMaze");
		setSize(fX + 50, fY + 50);
		setLayout(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			String host = tf.getText();
			String ip = java.net.InetAddress.getByName(host).getHostAddress();
			l.setText("IP of " + host + " is: " + ip);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public class MazeDrawing extends JPanel {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			hueCounter = 0;
			super.paintComponent(g);
			for (int i = 0; i < m.getSizeY(); i++) {
				for (int j = 0; j < m.getSizeX(); j++) {
					if (m.getPoint(j, i) == 1) {
						g.setColor(Color.white);
						g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
					} else if (m.getPoint(j, i) == 2) {
						g.setColor(Color.getHSBColor(hueCounter/360, 1, 1));
//						g.setColor(Color.red);
						g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
						hueCounter+=1;
					} else {
						g.setColor(Color.black);
						g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
					}
				}
			}
		}
	}

}
