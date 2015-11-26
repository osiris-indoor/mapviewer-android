package com.fhc25.percepcion.osiris.mapviewer.model.states.api;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

public interface IInternalState {

    MetaData getMetadata();
    void setMetadata(MetaData metadata);

    BuildingGroup getBuildingGroup();
    void setBuildingGroup(BuildingGroup buildingGroup);
}
