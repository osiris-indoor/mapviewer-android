/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

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
