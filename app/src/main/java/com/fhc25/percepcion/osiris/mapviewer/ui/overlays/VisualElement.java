
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

import java.util.Comparator;

/**
 * Describes an element that can be displayed through a map library
 */
public abstract class VisualElement {

	/**
	 * Gets the Zdepth of the visual element. The Zdepth determines the order of
	 * drawing when several visual elements are drawn over the same map. Lower
	 * numbers imply that the visual element is drawn earlier, and then appears
	 * occluded by visual elements with higher Zdepth
	 * 
	 * @return
	 */
	abstract public int getZDepth();

    /**
     * Called for destroying the visual element. Not every implementation shall use this feature,
     * for instance if the destruction of the visual element does not need to be called explicitly
     */
    abstract public void destroy();

	/**
	 * Main comparator based on the Zdepth value of the visual elements
	 */
	static public final Comparator<VisualElement> ZDEPTH_COMPARATOR = new Comparator<VisualElement>() {

		@Override
		public int compare(VisualElement lhs, VisualElement rhs) {
			return lhs.getZDepth() - rhs.getZDepth();
		}

	};

}
