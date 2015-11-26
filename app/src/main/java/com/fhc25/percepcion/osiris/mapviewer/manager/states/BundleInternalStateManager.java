package com.fhc25.percepcion.osiris.mapviewer.manager.states;

import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;

public class BundleInternalStateManager {

    public BundleInternalStateManager() {}

    public void saveInternalState(Bundle bundle, IInternalState internalState) {

        bundle.putSerializable("internalState.BuildingGroup",
                internalState.getBuildingGroup());
        bundle.putSerializable("internalState.Metadata",
                internalState.getMetadata());
    }

    public void loadInternalState(Bundle bundle, IInternalState internalState) {

        if (bundle.containsKey("internalState.BuildingGroup")) {
            internalState.setBuildingGroup((BuildingGroup) bundle
                    .getSerializable("internalState.BuildingGroup"));
        } else {
            Lgr.e(this.getClass().getName(), "BuildingGroup element not found in bundle");
        }

        if (bundle.containsKey("internalState.Metadata")) {
            internalState.setMetadata((MetaData) bundle.getSerializable("internalState.Metadata"));
        } else {
            Lgr.e(this.getClass().getName(), "Metadata element not found in bundle");
        }

    }
}
