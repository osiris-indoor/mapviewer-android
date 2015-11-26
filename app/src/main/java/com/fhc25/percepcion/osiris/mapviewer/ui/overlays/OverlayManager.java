package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay.ILayerOverlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Basic abstract class for managing all the overlays from an application.
 * Inheriting classes should specify the map-specific components for managing
 * the overlays
 */
public abstract class OverlayManager {

    private static final String TAG = OverlayManager.class.getName();

    private Integer level = 0;
    private List<ILayerOverlay> overlays = new ArrayList<ILayerOverlay>();

    /**
     * Leaves the displayer definition to the inheriting classes
     *
     * @return displayer
     */
    abstract public IVisualElementDisplayer getDisplayer();

    private ILayerOverlay.Observer layerObserver = new ILayerOverlay.Observer() {

        @Override
        public void onVisualElementsAdded(ILayerOverlay overlay,
                                          java.util.Collection<VisualElement> visualElements) {

            if (visualElements.size() > 0) {
                deepUpdate();
            }
        }

        @Override
        public void onVisualElementsRemoved(ILayerOverlay overlay,
                                            java.util.Collection<VisualElement> visualElements) {

            if (visualElements.size() > 0) {
                deepUpdate();
            }
        }

        @Override
        public void onRedrawRequested() {
            Lgr.w(TAG, "onRedrawRequested");
            deepUpdate();
        }

    };

    /**
     * Sets the level that must be displayed now
     *
     * @param level
     */
    public synchronized void setLevel(Integer level) {
        this.level = level;

        for (ILayerOverlay layer : overlays) {
            layer.setLevel(level);
        }

        deepUpdate();
    }

    /**
     * Gets the current level
     *
     * @return
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Adds a new Overlay
     *
     * @param overlay
     */
    public synchronized void addOverlay(ILayerOverlay overlay) {
        overlays.add(overlay);
        Collections.sort(overlays);

        overlay.addObserver(layerObserver);
        overlay.setLevel(level);

        deepUpdate();
    }

    /**
     * Removes an Overlay
     *
     * @param overlay
     */
    public synchronized void removeOverlay(ILayerOverlay overlay) {
        overlays.remove(overlay);
        overlay.removeObserver(layerObserver);
        overlay.destroy();

        deepUpdate();
    }

    /**
     * Redraws the displayer but dont change its structure
     */
    public void shallowUpdate() {
        Lgr.w(TAG, "shallowUpdate");

        getDisplayer().update();
    }

    /**
     * Updates the displayer from scratch
     */
    public synchronized void deepUpdate() {

        getDisplayer().clear();

        for (ILayerOverlay overlay : overlays) {
            for (VisualElement visualElement : overlay.getVisualElements()) {
                getDisplayer().display(visualElement);
            }
        }

        getDisplayer().update();
    }

    /**
     * Destroys the OverlayManager, including the layers contained
     */
    public void destroy() {

        for (ILayerOverlay layer : overlays) {
            layer.destroy();
        }

        overlays.clear();
    }

    abstract public void saveIntoBundle(Bundle bundle);

    abstract public void loadFromBundle(Bundle bundle);


}
