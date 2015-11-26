
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

/**
 * Abstract displayer template for specific VisualElements
 */
public abstract class VisualElementDisplayer<T> implements IVisualElementDisplayer {

	private Class<T> type;

	/**
	 * Main constructor
	 * 
	 * @param type
	 */
	public VisualElementDisplayer(Class<T> type) {
		this.type = type;
	}

	@Override
	public void display(VisualElement visualElement) {

		if (type.isInstance(visualElement)) {
			T specificVisualElement = (T) visualElement;
			displayElement(specificVisualElement);
		}

	}

	@Override
	public void remove(VisualElement visualElement) {

		if (type.isInstance(visualElement)) {
			T mapsforgeElement = (T) visualElement;
			removeElement(mapsforgeElement);
		}

	}

	/**
	 * Displays an specific VisualElement type onto the screen
	 * 
	 * @param visualElement
	 */
	abstract protected void displayElement(T visualElement);

	/**
	 * Removes an specific VisualElement type from the screen
	 * 
	 * @param visualElement
	 */
	abstract protected void removeElement(T visualElement);
}
