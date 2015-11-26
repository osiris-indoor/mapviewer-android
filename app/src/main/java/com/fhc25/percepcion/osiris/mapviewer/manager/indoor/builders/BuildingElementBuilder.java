package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;

/**
 * Interface describing the capability of building BuildingElements. Different specializations
 * produce different builders for specific building elements
 *
 */
public interface BuildingElementBuilder<T extends BuildingElement> {

    T build(Feature feature);
}
