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

/**
 * Base abstract class for creating Layers. Implements some basic functionality
 * of the {@link ILayerOverlay} interface
 */
public abstract class LayerOverlay implements ILayerOverlay {

    protected Observer observers;
    private boolean isVisible = true;
    private String identificationName = "";
    private Observer.Collection observersCollection = new Observer.Collection();

    /**
     * Main constructor
     *
     * @param identificationName
     */
    public LayerOverlay(String identificationName) {
        this.identificationName = identificationName;
        observers = observersCollection;
    }

    @Override
    public void addObserver(Observer observer) {
        observersCollection.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observersCollection.remove(observer);
    }

    @Override
    public int getNumObservers() {
        return observersCollection.size();
    }

    /**
     * Determines if the layer is visible or not
     *
     * @param isVisible
     */
    public void setIsVisible(boolean isVisible) {

        if (this.isVisible != isVisible) {

            // If before was visible, notify that elements will be removed
            if (this.isVisible) {
                observers.onVisualElementsRemoved(this, getVisualElements());
                this.isVisible = isVisible;
                observers.onRedrawRequested();
            } else {
                this.isVisible = isVisible;
                observers.onVisualElementsAdded(this, getVisualElements());
            }
        }
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public String getIdentificationName() {
        return identificationName;
    }

    @Override
    public int compareTo(ILayerOverlay rhs) {

        if (getZDepth() < rhs.getZDepth()) {
            return -1;
        } else if (getZDepth() == rhs.getZDepth()) {
            return 0;
        } else {
            return 1;
        }
    }

}
