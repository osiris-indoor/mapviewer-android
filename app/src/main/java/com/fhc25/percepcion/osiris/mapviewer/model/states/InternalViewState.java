package com.fhc25.percepcion.osiris.mapviewer.model.states;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;

public class InternalViewState implements IInternalViewState {

    private IInternalState internalState;

    public InternalViewState(IInternalState internalState) {
        this.internalState = internalState;
    }


    @Override
    public MetaData getMetadata() {
        return internalState.getMetadata();
    }

    @Override
    public void setMetadata(MetaData metaData) {
        internalState.setMetadata(metaData);
    }

    @Override
    public BuildingGroup getBuildingGroup() {
        return internalState.getBuildingGroup();
    }
}
