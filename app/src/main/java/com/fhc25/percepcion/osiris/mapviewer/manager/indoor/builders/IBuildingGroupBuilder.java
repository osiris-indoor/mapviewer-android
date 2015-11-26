package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;

import java.util.Collection;

public interface IBuildingGroupBuilder {

    BuildingElement buildBuildingElement(Feature feature);

    BuildingGroup buildBuildingGroup(Collection<Feature> features);
}
