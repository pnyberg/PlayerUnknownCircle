/**
 * This is the "circle" (which is both the active circle as 
 *  well as housing the outer and inner circle). The circle 
 *  starts outside the bounds and works towards the inner 
 *  circle (which will start within the bounds). Then for 
 *  each time the active circle reaches the inner circle a 
 *  new smaller circle will be created inside the former one.
 *  The active circle will then approach this inner circle 
 *  once more and do these steps repeatedly until the inner 
 *  circle disappears (and then the active will disappear with
 *  it). The handling for the circle shrinkage is done by a
 *  separate thread created from one of the methods 
 *  ("startCircle").
 * 
 * @author pernyberg
 */

import java.awt.Point;

public class PlayerUnknownCircle {
	private Bounds bounds;
	private Point innerCirclePoint;
	private Point outerCirclePoint;
	private int activeCircleX;
	private int activeCircleY;
	private int activeCircleRadius;
	private int innerCircleRadius;
	private int outerCircleRadius;
	private int activeCircleSpeed;
	private long activeCircleDelay;

	private final long circleDecreaseDelay = 200;

	/**
	 * 
	 * @param bounds
	 *            the bounds in which the first circle may be
	 * @param startRadius
	 *            size of circle-radius
	 * @param circleSpeed
	 *            ????
	 * @param circleDelay
	 *            milliseconds
	 */
	public PlayerUnknownCircle(Bounds bounds, int circleSpeed, long circleDelay) {
		this.bounds = bounds;
		this.activeCircleSpeed = circleSpeed;
		this.activeCircleDelay = circleDelay;

		init();
	}
	
	public void init() {
		// create the active circle (the starting outer circle)
		activeCircleRadius = (int)Math.sqrt((double)(
				Math.pow((double)bounds.getWidth(), 2) + 
				Math.pow((double)bounds.getHeight(), 2)
			)) / 2 + 10;
		activeCircleX = bounds.getWidth() / 2 - activeCircleRadius;
		activeCircleY = bounds.getHeight() / 2 - activeCircleRadius;
		
		// create the outer circle (based on active circle)
		outerCirclePoint = new Point(activeCircleX, activeCircleY);
		outerCircleRadius = activeCircleRadius;

		// create the inner circle (half the bounds in size)
		innerCircleRadius = Math.min(bounds.getWidth(), bounds.getHeight()) / 4;
		int innerPointX = bounds.getMinX()
				+ ((int) (Math.random() * (bounds.getWidth() - 2 * innerCircleRadius)));
		int innerPointY = bounds.getMinY()
				+ ((int) (Math.random() * (bounds.getHeight() - 2 * innerCircleRadius)));
		innerCirclePoint = new Point(innerPointX, innerPointY);
	}

	/**
	 * Wait for the duration of the delay, then half the circle placing the new
	 * circle completely inside the old circle. Repeat until circle is no more
	 * (radius = 0).
	 */
	public void startCircle() {
		(new Thread() {
			public void run() {
				// First iteration, is done without halving the radius
				try {
					Thread.sleep(activeCircleDelay);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Doing it until it's "the same" as the first inner circle
				decreaseCircle();

				// All the other iterations
				while (innerCircleRadius > 0) {
					// Pause
					try {
						Thread.sleep(activeCircleDelay);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Making the former inner circle the new outer circle
					outerCirclePoint = innerCirclePoint;
					outerCircleRadius = innerCircleRadius;
					
					// Creating a new inner circle
					innerCircleRadius /= 2;
					if (innerCircleRadius < 5) {
						innerCircleRadius = 0;
					}
					innerCirclePoint = createInnerCircle();

					// Pause
					try {
						Thread.sleep(activeCircleDelay);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Doing it until it's "the same" as the first inner circle
					decreaseCircle();
				}
			}
		}).start();
	}

	/**
	 * The decreasing of the circle is done one step at 
	 *  a time with some given delay between "decreases"
	 */
	private void decreaseCircle() {
		while (activeCircleRadius > innerCircleRadius) {
			decreaseCircleIteration();
			
			try {
				Thread.sleep(circleDecreaseDelay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Decrease the active circles radius and recalculate 
	 *  its position
	 */
	private void decreaseCircleIteration() {
		activeCircleRadius -= activeCircleSpeed;
		
		// recalculate the active circles point
		int circleDiffX = innerCirclePoint.x - outerCirclePoint.x;
		int circleDiffY = innerCirclePoint.y - outerCirclePoint.y;

		double percentTowardsInnerCircle = 1 - (double)(activeCircleRadius - innerCircleRadius) / (outerCircleRadius - innerCircleRadius);

		activeCircleX = outerCirclePoint.x + (int)(circleDiffX * percentTowardsInnerCircle);
		activeCircleY = outerCirclePoint.y + (int)(circleDiffY * percentTowardsInnerCircle);
	}

	/**
	 * Creates a inner circle which is inside the current outer circle
	 */
	private Point createInnerCircle() {
		int newX = outerCirclePoint.x + innerCircleRadius/2 + 
				((int) (Math.random() * innerCircleRadius));
		int newY = outerCirclePoint.y + innerCircleRadius/2 + 
				((int) (Math.random() * innerCircleRadius));
		return new Point(newX, newY);
	}

	/*
	 * Getters
	 */
	public int getActiveCircleX() {
		return activeCircleX;
	}

	public int getActiveCircleY() {
		return activeCircleY;
	}

	public int getActiveCircleRadius() {
		return activeCircleRadius;
	}
	
	public int getInnerCircleX() {
		return innerCirclePoint.x;
	}

	public int getInnerCircleY() {
		return innerCirclePoint.y;
	}

	public int getInnerCircleRadius() {
		return innerCircleRadius;
	}
}