/**
 * The JPanel that displays the circles (basically just a canvas)
 * 
 * @author pernyberg
 */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PlayerUnknownCircleBoard extends JPanel {
	/******************************************/
	public static final int SPACING = 5;
	private final long UPDATE_DELAY = 100;
	/******************************************/
	
	private int width;
	private int height;
	private PlayerUnknownCircle circle;
	
	public PlayerUnknownCircleBoard(int width, int height, PlayerUnknownCircle circle) {
		this.width = width;
		this.height = height;
		this.circle = circle;
	}
	
	/**
	 * Start a thread that updates the screen (at a given interval)
	 */
	public void startUpdating() {
		(new Thread() {
			public void run() {
				// repaint and then sleep for a certain amount of ms
				while(true) {
					repaint();

					try {
						Thread.sleep(UPDATE_DELAY);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	/**
	 * Paints the active circle as well as the inner circle
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(SPACING, SPACING, width, height);
		
		// Paint "inner" circle
		g.setColor(Color.black);
		int innerX = circle.getInnerCircleX();
		int innerY = circle.getInnerCircleY();
		int innerDiameter = circle.getInnerCircleRadius() * 2;
		g.drawOval(innerX + SPACING, innerY + SPACING, innerDiameter, innerDiameter);
		
		// Paint active circle
		g.setColor(Color.blue);
		int activeX = circle.getActiveCircleX();
		int activeY = circle.getActiveCircleY();
		int activeDiameter = circle.getActiveCircleRadius() * 2;
		g.drawOval(activeX + SPACING, activeY + SPACING, activeDiameter, activeDiameter);
	}
}
