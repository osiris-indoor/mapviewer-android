
package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Polygon geometric object. It is represented as a collection of LineStrings
 */
public class Polygon extends Geometry implements Serializable {

    private Collection<LineString> collectionLineString;

    public Polygon() {
    }

    public Polygon(Collection<LineString> lines) {
        collectionLineString = lines;
    }

    public Collection<LineString> getCollectionLineStringDTO() {
        return collectionLineString;
    }

    public void setCollectionLineString(
            Collection<LineString> collectionLineString) {
        this.collectionLineString = collectionLineString;
    }

    @Override
    public Collection<Point> getFormingPoints() {

        Collection<Point> points = new ArrayList<>();

        for (LineString lineString : collectionLineString) {
            points.addAll(lineString.getFormingPoints());
        }

        return points;
    }
}
