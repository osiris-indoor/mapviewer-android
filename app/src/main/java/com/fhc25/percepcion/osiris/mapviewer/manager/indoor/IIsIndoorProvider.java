package com.fhc25.percepcion.osiris.mapviewer.manager.indoor;


import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;

public interface IIsIndoorProvider {

    boolean isIndoor(Location location);

    BuildingElement findBuildingElement(Location location);
}
