
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes;

/**
 * Theme of a certain indoor visual element
 */
public class IndoorElementTheme {

	protected float radius;
	protected int fillColor;
	protected int strokeColor;
	protected int strokeWidth;

	/**
	 * Main constructor
	 * 
	 * @param radius
	 * @param fillColor
	 * @param strokeColor
	 * @param strokeWidth
	 */
	public IndoorElementTheme(float radius, int fillColor, int strokeColor,
			int strokeWidth) {

		this.radius = radius;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.strokeWidth = strokeWidth;
	}

	/**
	 * Gets the radius
	 * 
	 * @return radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Gets the fill color
	 * 
	 * @return color
	 */
	public int getFillColor() {
		return fillColor;
	}

	/**
	 * Gets the stroke color
	 * 
	 * @return
	 */
	public int getStrokeColor() {
		return strokeColor;
	}

	/**
	 * Gets the stroke width
	 * 
	 * @return
	 */
	public int getStrokeWidth() {
		return strokeWidth;
	}

}
