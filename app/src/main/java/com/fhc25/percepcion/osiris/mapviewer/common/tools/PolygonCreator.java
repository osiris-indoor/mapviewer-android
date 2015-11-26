package com.fhc25.percepcion.osiris.mapviewer.common.tools;

import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Polygon;

import java.util.ArrayList;
import java.util.List;

public class PolygonCreator {

    public static Polygon createPolygon(MetaData metaData) {

        double latMin = metaData.getMinLatitude();
        double lonMin = metaData.getMinLongitude();
        double latMax = metaData.getMaxLatitude();
        double lonMax = metaData.getMaxLongitude();

        Polygon polygon = new Polygon();
        List<Point> lPoints = new ArrayList<Point>();

        lPoints.add(new Point(latMax, lonMax));
        lPoints.add(new Point(latMin, lonMax));
        lPoints.add(new Point(latMin, lonMin));
        lPoints.add(new Point(latMax, lonMin));
        lPoints.add(new Point(latMax, lonMax));

        LineString line = new LineString(lPoints);
        java.util.Collection<LineString> lLines = new ArrayList<LineString>();
        lLines.add(line);
        polygon.setCollectionLineString(lLines);

        return polygon;

    }
}
