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

package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay;

import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Interface that represents a layer of overlay objects in a map.
 * 
 */
public interface ILayerOverlay extends Comparable<ILayerOverlay> {

	/**
	 * Retrieves all the VisualElements in the layer
	 * 
	 * @return
	 */
	Collection<VisualElement> getVisualElements();

	/**
	 * Gets if the layer is visible now
	 * 
	 * @return visible
	 */
	boolean isVisible();

	/**
	 * Gets the Zdepth of the layer. The Zdepth determines the order of drawing
	 * when several layers are drawn over the same map. Lower numbers imply that
	 * the layer is drawn earlier, and then appears occluded by layers with
	 * higher Zdepth
	 * 
	 * @return Zdepth
	 */
	int getZDepth();

	/**
	 * Gets the identification name of the layer
	 * 
	 * @return name
	 */
	String getIdentificationName();

	/**
	 * Sets the level related to this layer. This level will only be drawn if
	 * this level is selected
	 * 
	 * @param level
	 */
	void setLevel(Integer level);

	/**
	 * Adds an observer for notification purposes
	 * 
	 * @param observer
	 */
	void addObserver(Observer observer);

	/**
	 * Removes an observer
	 * 
	 * @param observer
	 */
	void removeObserver(Observer observer);

	/**
	 * Retrieves the number of observers associated with the instance of this
	 * class
	 * 
	 * @return
	 */
	int getNumObservers();

    /**
     *
     */
	void destroy();
	
	/**
	 * Observer of ILayerOverlay elements
	 * 
	 * @author Alvaro.Arranz
	 *
	 */
	interface Observer {

		/**
		 * Notify when new visual elements where added to this layer
		 * 
		 * @param overlay
		 * @param visualElements
		 */
		void onVisualElementsAdded(ILayerOverlay overlay,
										  java.util.Collection<VisualElement> visualElements);

		/**
		 * Notify when visual elements were removed from this layer
		 * 
		 * @param overlay
		 * @param visualElements
		 */
		void onVisualElementsRemoved(ILayerOverlay overlay,
											java.util.Collection<VisualElement> visualElements);

		/**
		 * Redraw this layer
		 */
		void onRedrawRequested();

		/**
		 * Collection of ILayerOverlay.Observer for managing various observer's notifications at the same time
		 * 
		 * @author Alvaro.Arranz
		 *
		 */
		class Collection extends LinkedList<Observer> implements
				Observer {

			private static final long serialVersionUID = 1L;

			@Override
			public void onVisualElementsAdded(ILayerOverlay overlay,
					java.util.Collection<VisualElement> visualElements) {

				for (Observer observer : this) {
					observer.onVisualElementsAdded(overlay, visualElements);
				}
			}

			@Override
			public void onVisualElementsRemoved(ILayerOverlay overlay,
					java.util.Collection<VisualElement> visualElements) {

				for (Observer observer : this) {
					observer.onVisualElementsRemoved(overlay, visualElements);
				}
			}

			@Override
			public void onRedrawRequested() {

				for (Observer observer : this) {
					observer.onRedrawRequested();
				}
			}
		}

	}
}
