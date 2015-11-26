package com.fhc25.percepcion.osiris.mapviewer.assemblers.utils;

import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.SimpleAssembler;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

public class LocationFeatureAssembler extends SimpleAssembler<Feature, Location> {


    @Override
    public Location createDomainObject(Feature dataTransferObject) {

        Point point = (Point) dataTransferObject.getGeometry();
        int floor = 0;

        if (dataTransferObject.getProperties().containsKey("level")) {
            floor = Integer.parseInt(dataTransferObject.getProperties().get("level"));
        }

        return new Location(point.getLatitude(), point.getLongitude(), floor);
    }

    @Override
    public Feature createDataTransferObject(Location domainObject) {
        return null;
    }
}
