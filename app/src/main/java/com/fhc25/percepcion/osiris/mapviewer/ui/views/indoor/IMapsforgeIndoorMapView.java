package com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor;

import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.ui.controllers.FloorSelectorViewController;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.OsirisOverlayManager;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level.IFloorSelectorView;

/**
 * Created by alvaroarranz on 10/09/15.
 */
public interface IMapsforgeIndoorMapView {

    MapsforgeMapView getMapsforgeMapView();

    IFloorSelectorView getFloorSelectorView();

    OsirisOverlayManager getOsirisOverlayManager();

    FloorSelectorViewController getFloorSelectorViewController();

    void initFromViewState(IInternalViewState internalViewState);

    void setMap(String mapFileName);

    void updateIndoorOverlay();

    void destroy();

    void onSaveInstanceState(Bundle savedInstanceState);

    void loadFromBundle(Bundle savedInstanceState);

    void pauseMapView();

    void showProgressDialog(final int titleResource);

    void setProgressDialogTitle(final int titleResource);

    void dismissProgressDialog();
}
