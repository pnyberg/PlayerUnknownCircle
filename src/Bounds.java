/**
 * Bounds creating an imaginary rectangle
 * This can return max and min values of the bounding box
 *  and also the total width/height of the bounding box
 *  
 * @author pernyberg
 */
public class Bounds {
	private int minX;
	private int minY;
	private int maxX; 
	private int maxY;
	
	/**
	 * Min-bounds must be smaller than max-bounds (for X and Y respectively)
	 */
	public Bounds(int minX, int minY, int maxX, int maxY) {
		if (minX > maxX) {
			throw new IllegalArgumentException("Minimum bounds for X must be lower than maximum bound!");
		}
		if (minY > maxY) {
			throw new IllegalArgumentException("Minimum bounds for Y must be lower than maximum bound!");
		}
		
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	
	/*
	 * Setters
	 */
	public void setMinX(int minX) {
		this.minX = minX;
	}
	
	public void setMinY(int minY) {
		this.minY = minY;
	}
	
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	/*
	 * Getters
	 */
	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}
	
	public int getWidth() {
		return maxX-minX;
	}
	
	public int getHeight() {
		return maxY-minY;
	}
}
