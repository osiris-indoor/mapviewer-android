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
