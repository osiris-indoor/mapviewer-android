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

import android.content.res.Resources;
import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.IIsIndoorProvider;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Building;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layerbuilder.ILayerBuilder;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layerbuilder.LayerBuilder;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layers.IndoorLayer;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.android.view.MapView;

/**
 * Specific SmartCampus overlay manager
 */
public abstract class OsirisOverlayManager extends OverlayManager
        implements org.mapsforge.map.model.common.Observer {

    static private final byte TINY_ZOOM_LIMIT = 16;

    protected MapView mapView;
    protected VisualTheme theme;
    private ILayerBuilder layerBuilder;
    private Resources resources;

    private IndoorLayer indoorLayer = null;

    private IIsIndoorProvider isIndoorProvider;

    /**
     * Main constructor
     *
     * @param resources
     * @param mapView
     * @param theme
     */
    public OsirisOverlayManager(Resources resources, MapView mapView,
                                VisualTheme theme) {

        this.mapView = mapView;
        this.theme = theme;
        this.resources = resources;

        layerBuilder = new LayerBuilder(getVisualsBuilder());
        mapView.getModel().mapViewPosition.addObserver(this);
    }

    public IndoorLayer getIndoorLayer() {
        return indoorLayer;
    }

    /**
     * Leaves the visuals builder definition to the inheriting classes
     *
     * @return visuals builder
     */
    abstract public IVisualsBuilder getVisualsBuilder();

    /**
     * Builds the smart campus layers based on the main
     */
    public void buildFromViewState(IInternalViewState viewState) {

        if (indoorLayer != null) {
            indoorLayer.destroy();
            indoorLayer = null;
        }

        createIndoorLayer(viewState);

        deepUpdate();
    }

    private void createIndoorLayer(IInternalViewState viewState) {

        BuildingGroup buildingGroup = viewState.getBuildingGroup();
        isIndoorProvider = buildingGroup;

        for (Building building : buildingGroup.getAllBuildings()) {
            indoorLayer = layerBuilder.buildIndoor(building);
            addOverlay(indoorLayer);
        }

        this.onChange();
    }

    @Override
    public void deepUpdate() {

        OsirisOverlayManager.super.deepUpdate();
    }

    @Override
    public void onChange() {

        if (indoorLayer != null) {
            byte currentZoomLevel = mapView.getModel().mapViewPosition
                    .getZoomLevel();

            if (currentZoomLevel <= TINY_ZOOM_LIMIT && !indoorLayer.getIsTiny()) {
                indoorLayer.setIsTiny(true);
                deepUpdate();
            } else if (currentZoomLevel > TINY_ZOOM_LIMIT && indoorLayer.getIsTiny()) {
                indoorLayer.setIsTiny(false);
                deepUpdate();
            }
        }

    }

    @Override
    public void saveIntoBundle(Bundle bundle) {
    }

    @Override
    public void loadFromBundle(Bundle bundle) {
    }
}
