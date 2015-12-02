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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Leveled {@link LayerOverlay} for displaying indoor VisualElements and a
 * {@link LinkedSimpleOverlay} for displaying outdoor VisualElements, managed
 * everything by the same layer.
 */
public abstract class MixedOverlay extends LayerOverlay {

    private LinkedSimpleOverlay simpleOverlay;
    private SparseArrayLevelOverlay levelOverlay;

    /**
     * Main constructor
     *
     * @param identificationName
     */
    public MixedOverlay(String identificationName) {
        super(identificationName);

        simpleOverlay = new LinkedSimpleOverlay(identificationName
                + "SimpleOverlay") {

            @Override
            public int getZDepth() {
                return MixedOverlay.this.getZDepth();
            }
        };

        levelOverlay = new SparseArrayLevelOverlay(identificationName
                + "LevelOverlay") {

            @Override
            public int getZDepth() {
                return MixedOverlay.this.getZDepth();
            }
        };
    }

    @Override
    public Collection<VisualElement> getVisualElements() {

        Collection<VisualElement> elements = new ArrayList<VisualElement>();
        elements.addAll(simpleOverlay.getVisualElements());
        elements.addAll(levelOverlay.getVisualElements());

        return elements;
    }

    @Override
    public void addObserver(Observer observer) {
        simpleOverlay.addObserver(observer);
        levelOverlay.addObserver(observer);
        super.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        simpleOverlay.removeObserver(observer);
        levelOverlay.removeObserver(observer);
        super.removeObserver(observer);
    }

    @Override
    public void setLevel(Integer floor) {
        levelOverlay.setLevel(floor);
    }

    /**
     * Adds a group of VisualElements to the simple overlay (outdoor element).
     * Notifies its observers
     *
     * @param visualElements
     */
    public void addAllToSimpleOverlay(Collection<VisualElement> visualElements) {
        simpleOverlay.addAll(visualElements);
    }

    /**
     * Removes a group of VisualElements from the simple overlay (outdoor
     * elements). Notifies its observers
     *
     * @param visualElements
     */
    public void removeAllFromSimpleOverlay(
            Collection<VisualElement> visualElements) {
        simpleOverlay.removeAll(visualElements);
    }

    /**
     * Adds a group of VisualElements to the leveled layer (indoor elements) .
     * Notifies its observers
     *
     * @param visualElement
     * @param level
     */
    public void addAllToLevelOverlay(Collection<VisualElement> visualElement,
                                     Integer level) {
        levelOverlay.addAll(level, visualElement);
    }

    /**
     * Removes a level layer from the leveled layer (indoor elements). Notifies
     * its observers
     *
     * @param level
     */
    public void removeLayerFromLevelOverlay(Integer level) {
        levelOverlay.removeLayer(level);
    }

    /**
     * Removes all the VisualElements from all layers. Notifies its observers
     */
    public void clearVisualElements() {
        simpleOverlay.clearVisualElements();
        levelOverlay.clearVisualElements();
    }

    /**
     * Adds a group of VisualElements to the default layer of the indoor layer.
     * Notifies its observers
     *
     * @param visualElements
     */
    public void addDefaultAll(Collection<VisualElement> visualElements) {
        levelOverlay.addDefaultAll(visualElements);
    }

    /**
     * Removes a group of VisualElements from the default layer of the indoor
     * layer. Notifies its observers
     *
     * @param visualElements
     */
    public void removeDefaultAll(Collection<VisualElement> visualElements) {
        levelOverlay.removeDefaultAll(visualElements);
    }

    /**
     * Removes all the VisualElements from the leveled layer
     */
    public void cleanLevelOverlay() {
        levelOverlay.clearVisualElements();
    }

    /**
     * Removes a group of VisualElements from all the layers. Notifies its
     * observers
     *
     * @param visualElements
     */
    public void removeAll(Collection<VisualElement> visualElements) {
        simpleOverlay.removeAll(visualElements);
        levelOverlay.removeAll(visualElements);
    }

    /**
     * Removes one specific VisualElement from all the layers Notifies its
     * observers
     *
     * @param visualElement
     */
    public void remove(VisualElement visualElement) {
        Collection<VisualElement> list = new ArrayList<VisualElement>();
        list.add(visualElement);
        removeAll(list);
    }

    /**
     * Is a VisualElement contained in this layer?
     *
     * @param object
     * @return
     */
    public boolean contains(VisualElement object) {

        return simpleOverlay.contains(object) || levelOverlay.contains(object);
    }

    @Override
    public void destroy() {
        simpleOverlay.destroy();
        levelOverlay.destroy();
    }

}
