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
