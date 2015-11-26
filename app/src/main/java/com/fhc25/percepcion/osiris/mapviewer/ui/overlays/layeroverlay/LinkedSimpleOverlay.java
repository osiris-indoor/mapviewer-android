package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay;

import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple layer overlay, with no floor associated: always display its
 * {@link VisualElement}. The elements are saved in a LinkedList data structure
 */
public abstract class LinkedSimpleOverlay extends LayerOverlay {

    private List<VisualElement> visualElements = new LinkedList<VisualElement>();

    /**
     * Main constructor
     *
     * @param identificationName
     */
    public LinkedSimpleOverlay(String identificationName) {
        super(identificationName);
    }

    @Override
    public Collection<VisualElement> getVisualElements() {
        if (isVisible()) {
            return visualElements;
        } else {
            return new LinkedList<VisualElement>();
        }
    }

    /**
     * Adds a group of VisualElements to this layer. Elements are sorted respect
     * the compareTo function of the VisualElement. Notifies its Observers that
     * the elements have been added to the layer
     *
     * @param visualElements
     */
    public void addAll(Collection<VisualElement> visualElements) {
        this.visualElements.addAll(visualElements);
        Collections.sort(this.visualElements, VisualElement.ZDEPTH_COMPARATOR);

        observers.onVisualElementsAdded(this, visualElements);
    }

    /**
     * Removes a group of VisualElements from this layer. Notifies its Observers
     * about the elements that have been removed
     *
     * @param visualElements
     */
    public void removeAll(Collection<VisualElement> visualElements) {
        if (this.visualElements.removeAll(visualElements)) {
            observers.onVisualElementsRemoved(this, visualElements);
        }
    }

    /**
     * Removes all the VisualElements from this layer. Notifies its observers
     * about the elements that have been removed
     */
    public void clearVisualElements() {
        if (!visualElements.isEmpty()) {
            Collection<VisualElement> temp = new LinkedList<VisualElement>(
                    visualElements);
            visualElements.clear();

            observers.onVisualElementsRemoved(this, temp);
        }
    }

    @Override
    public void setLevel(Integer floor) {
        // Nothing here
    }

    @Override
    public void destroy() {

        for (VisualElement element : visualElements) {
            element.destroy();
        }
    }

    /**
     * Is a VisualElement contained in this layer?
     *
     * @param object
     * @return is visual element contained
     */
    public boolean contains(VisualElement object) {
        return visualElements.contains(object);
    }

}
