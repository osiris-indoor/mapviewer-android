
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

/**
 * Displayer interface for describing the functionality of displaying Visual
 * Elements. This interface should be specialized for each map library
 */
public interface IVisualElementDisplayer {

	/**
	 * Displays a visual element in the screen
	 * 
	 * @param visualElement
	 */
	void display(VisualElement visualElement);

	/**
	 * Removes a visual element from the screen
	 * 
	 * @param visualElement
	 */
	void remove(VisualElement visualElement);

	/**
	 * Redraws all the visual elements
	 */
	void update();

	/**
	 * Removes all the visual elements that are currently being displayed
	 */
	void clear();
	
}
