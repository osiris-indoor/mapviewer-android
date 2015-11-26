package com.fhc25.percepcion.osiris.mapviewer.model.states.api;


import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

public interface IInternalViewState {

    MetaData getMetadata();

    void setMetadata(MetaData metaData);

    BuildingGroup getBuildingGroup();
}
