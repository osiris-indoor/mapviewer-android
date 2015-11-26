package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layers;

import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay.LinkedSimpleOverlay;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay.SparseArrayLevelOverlay;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Indoor visual layer
 */
public class IndoorLayer extends SparseArrayLevelOverlay {

    private LinkedSimpleOverlay tinyZoomOverlay = new LinkedSimpleOverlay(
            "Indoor TinyZoomOverlay") {
        @Override
        public int getZDepth() {
            return 50;
        }
    };

    private boolean isTiny = false;

    /**
     * Gets if the Indoor layer is represented with tiny characteristics (view
     * from far away in the map)
     *
     * @return isTiny
     */
    public boolean getIsTiny() {
        return isTiny;
    }

    /**
     * Sets if the Indoor layer is represented as tiny (view far away in the
     * map)
     *
     * @param tiny
     */
    public void setIsTiny(boolean tiny) {
        isTiny = tiny;
    }

    /**
     * Main constructor
     *
     * @param identificationName
     */
    public IndoorLayer(String identificationName) {
        super(identificationName);
    }

    @Override
    public int getZDepth() {
        return 1;
    }

    /**
     * Adds a VisualElement to the "tiny" representation characteristics
     *
     * @param visualElement
     */
    public void addToTinyZoom(VisualElement visualElement) {
        Collection<VisualElement> visualElements = new ArrayList<VisualElement>();
        visualElements.add(visualElement);
        addToTinyZoom(visualElements);
    }

    /**
     * Adds a group of VisualElements to the "tiny" representation characteristics
     *
     * @param visualElements
     */
    public void addToTinyZoom(Collection<VisualElement> visualElements) {
        tinyZoomOverlay.addAll(visualElements);
    }

    /**
     * Removes a group of VisualElements from the "tiny" representation
     *
     * @param visualElements
     */
    public void removeFromTinyZoom(Collection<VisualElement> visualElements) {
        tinyZoomOverlay.removeAll(visualElements);
    }

    @Override
    public Collection<VisualElement> getVisualElements() {

        if (isTiny) {
            return tinyZoomOverlay.getVisualElements();
        } else {
            return super.getVisualElements();
        }
    }

}
