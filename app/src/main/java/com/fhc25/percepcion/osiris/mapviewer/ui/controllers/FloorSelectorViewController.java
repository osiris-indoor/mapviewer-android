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

package com.fhc25.percepcion.osiris.mapviewer.ui.controllers;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.OsirisOverlayManager;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.MapsforgeMapView;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level.IFloorSelectorObserver;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level.IFloorSelectorView;

public class FloorSelectorViewController implements IFloorSelectorObserver, MapsforgeMapView.Observer{

    private static final String TAG = FloorSelectorViewController.class.getName();
    private static final byte TINY_ZOOM_LIMIT = 16;

    private OsirisOverlayManager osirisOverlayManage;
    private IFloorSelectorView floorSelectorView;

    public FloorSelectorViewController(IFloorSelectorView floorSelectorView, OsirisOverlayManager osirisOverlayManager) {
        this.osirisOverlayManage = osirisOverlayManager;
        this.floorSelectorView = floorSelectorView;
    }

    @Override
    public void onFloorSelectedChanged(String floor) {

        if (osirisOverlayManage != null) {

            Integer ifloor = 0;
            try {
                ifloor = Integer.parseInt(floor);
            } catch (NumberFormatException e) {
                Lgr.e(TAG, "Unable to parse button text " + floor);
                Lgr.e(TAG, e.getMessage());
            }

            osirisOverlayManage.setLevel(ifloor);
        }
    }

    @Override
    public void onPositionChange(double latitude, double longitude, final byte currentZoomLevel) {

        if (currentZoomLevel <= TINY_ZOOM_LIMIT && floorSelectorView.isVisible()) {
            floorSelectorView.setVisible(false);
            floorSelectorView.setFloor("0");
        } else if (currentZoomLevel > TINY_ZOOM_LIMIT && !floorSelectorView.isVisible()) {
            floorSelectorView.setVisible(true);
        }
    }
}
