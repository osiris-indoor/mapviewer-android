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

package com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor;

import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.ui.controllers.FloorSelectorViewController;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.OsirisOverlayManager;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level.IFloorSelectorView;

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
