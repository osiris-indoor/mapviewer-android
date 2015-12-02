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

import android.util.SparseArray;

import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Leveled {@link LayerOverlay} implementation. Manages different layers for
 * each selected floor plus a default layer that is drawn when a level that was
 * not specifies was selected. It is implemented as a SparseArray data structure
 */
public abstract class SparseArrayLevelOverlay extends LayerOverlay {

    private SparseArray<LinkedSimpleOverlay> sparseVisualElements = new SparseArray<LinkedSimpleOverlay>();

    private LinkedSimpleOverlay defaultOverlay;

    private Integer floor = 0;

    /**
     * Main constructor
     *
     * @param identificationName
     */
    public SparseArrayLevelOverlay(String identificationName) {
        super(identificationName);

        defaultOverlay = new LinkedSimpleOverlay(getIdentificationName()
                + "- Default") {

            @Override
            public int getZDepth() {
                return SparseArrayLevelOverlay.this.getZDepth();
            }
        };
    }

    @Override
    public void addObserver(Observer observer) {
        defaultOverlay.addObserver(observer);

        for (int i = 0; i < sparseVisualElements.size(); i++) {

            LinkedSimpleOverlay overlay = sparseVisualElements
                    .get(sparseVisualElements.keyAt(i));

            overlay.addObserver(observer);
        }

        super.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        defaultOverlay.removeObserver(observer);

        for (int i = 0; i < sparseVisualElements.size(); i++) {

            LinkedSimpleOverlay overlay = sparseVisualElements
                    .get(sparseVisualElements.keyAt(i));

            overlay.removeObserver(observer);
        }

        super.removeObserver(observer);
    }

    @Override
    public Collection<VisualElement> getVisualElements() {
        if (isVisible()) {
            LinkedSimpleOverlay overlay = sparseVisualElements.get(floor);

            if (overlay != null) {
                return overlay.getVisualElements();
            } else {
                return defaultOverlay.getVisualElements();
            }
        } else {
            return new LinkedList<VisualElement>();
        }
    }

    /**
     * Adds a group of VisualElements to a certain level. Notifies its observers
     *
     * @param level
     * @param visualElements
     */
    public void addAll(Integer level, Collection<VisualElement> visualElements) {

        LinkedSimpleOverlay simpleLayer = sparseVisualElements.get(level);

        if (simpleLayer == null) {
            simpleLayer = new LinkedSimpleOverlay(getIdentificationName() + "-"
                    + level) {
                @Override
                public int getZDepth() {
                    return SparseArrayLevelOverlay.this.getZDepth();
                }
            };

            sparseVisualElements.put(level, simpleLayer);
        }

        simpleLayer.addAll(visualElements);
        observers.onVisualElementsAdded(this, visualElements);
    }

    /**
     * Removes a complete layer associated with a certain layer. Notifies its
     * observers
     *
     * @param level
     */
    public void removeLayer(Integer level) {

        LinkedSimpleOverlay overlay = sparseVisualElements.get(level);

        if (overlay != null) {
            observers.onVisualElementsRemoved(this,
                    overlay.getVisualElements());
        }

        sparseVisualElements.remove(level);

        defaultOverlay.clearVisualElements();
    }

    /**
     * Removes all the layers, including the default one. Notifies its observers
     */
    public void clearVisualElements() {

        for (int i = 0; i < sparseVisualElements.size(); i++) {

            LinkedSimpleOverlay overlay = sparseVisualElements
                    .get(sparseVisualElements.keyAt(i));
            observers.onVisualElementsRemoved(this,
                    overlay.getVisualElements());
        }

        sparseVisualElements.clear();
    }

    @Override
    public void setLevel(Integer floor) {
        this.floor = floor;
    }

    /**
     * Is a VisualElement contained in this layer?
     *
     * @param object
     * @return is visual element contained
     */
    public boolean contains(VisualElement object) {

        for (int i = 0; i < sparseVisualElements.size(); i++) {

            LinkedSimpleOverlay overlay = sparseVisualElements
                    .get(sparseVisualElements.keyAt(i));

            if (overlay.contains(object)) {
                return true;
            }
        }

        return defaultOverlay.contains(object);
    }

    /**
     * Removes a group of VisualElements from all the layers, including the
     * default one. Notifies its observers
     *
     * @param visualElements
     */
    public void removeAll(Collection<VisualElement> visualElements) {

        for (int i = 0; i < sparseVisualElements.size(); i++) {

            LinkedSimpleOverlay overlay = sparseVisualElements
                    .get(sparseVisualElements.keyAt(i));

            overlay.removeAll(visualElements);
        }

        defaultOverlay.removeAll(visualElements);
    }

    /**
     * Adds a group of VisualElements to the default layer
     *
     * @param visualElements
     */
    public void addDefaultAll(Collection<VisualElement> visualElements) {
        defaultOverlay.addAll(visualElements);
    }

    /**
     * Removes a group of VisualElements from the default layer
     *
     * @param visualElements
     */
    public void removeDefaultAll(Collection<VisualElement> visualElements) {
        defaultOverlay.removeAll(visualElements);
    }

    @Override
    public void destroy() {

        for (int i = 0; i < sparseVisualElements.size(); i++) {
            LinkedSimpleOverlay overlay = sparseVisualElements
                    .get(sparseVisualElements.keyAt(i));
            overlay.destroy();
        }

        defaultOverlay.destroy();
    }
}
