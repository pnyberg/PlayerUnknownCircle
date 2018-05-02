/**
 * The frame which is the main engine for the whole simulation
 *  By pressing a button the simulation starts/restarts, then
 *  it goes on until the circles disappear, then the threads
 *  running the simulation stops. 
 * 
 * @author pernyberg
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerUnknownCircleSimulator extends JFrame implements ActionListener {
	private PlayerUnknownCircleBoard board; 
	private JPanel buttonPanel;
	private JButton startButton;
	
	private PlayerUnknownCircle circle;
	
	public PlayerUnknownCircleSimulator(int width, int height) {
		Bounds bounds = new Bounds(0, 0, width, height);
		int circleSpeed = 1;
		long circleDelay = 100;
		circle = new PlayerUnknownCircle(bounds, circleSpeed, circleDelay);

		// Creating components
		board = new PlayerUnknownCircleBoard(width, height, circle);		
		buttonPanel = new JPanel();
		startButton = new JButton("Start");
		
		// Adding listeners to components
		startButton.addActionListener(this);
		
		// Adding components to other components 
		buttonPanel.add(startButton);
		
		add(board);
		add(buttonPanel, BorderLayout.SOUTH);
		
		// Setting frame-properties
		setSize(width + PlayerUnknownCircleBoard.SPACING * 2, height + 50 + PlayerUnknownCircleBoard.SPACING * 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Inits the circle and starts the "process"
	 */
	private void init() {
		circle.init();

		circle.startCircle();
		board.startUpdating();
	}
	
	/**
	 * Handle when the button is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			init();
		}
	}

	public static void main(String[] args) {
		new PlayerUnknownCircleSimulator(500, 500);
	}
}