package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Line geometry object. Ordered collection of Points
 */
public class LineString extends Geometry implements Serializable {

    private List<Point> collectionPoint;
    private Point centroid;

    public LineString() {
    }

    /**
     * Constructor from a collection of points
     *
     * @param points
     */
    public LineString(List<Point> points) {
        collectionPoint = points;
    }

    /**
     * Gets the ordered collection of points
     *
     * @return points collection
     */
    public List<Point> getCollectionPoint() {
        return collectionPoint;
    }

    /**
     * Sets the ordered points collection
     *
     * @param collectionPointDTO
     */
    public void setCollectionPoint(List<Point> collectionPointDTO) {
        this.collectionPoint = collectionPointDTO;
    }

    /**
     * Gets the centroid of the Line
     *
     * @return centroid
     */
    public Point getCentroid() {
        return centroid;
    }

    /**
     * Sets the Line's centroid
     *
     * @param centroid
     */
    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof LineString)) {
            return false;
        }

        if (o == this) {
            return true;
        }

        LineString l = (LineString) o;

        if (l.collectionPoint.size() != this.collectionPoint.size()) {
            return false;
        }

        if (!l.centroid.equals(this.centroid)) {
            return false;
        }

        Iterator<Point> p1 = l.collectionPoint.iterator();
        Iterator<Point> p2 = this.collectionPoint.iterator();

        while (p1.hasNext()) {
            if (!(p1.next().equals(p2.next()))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = hash * 89 + collectionPoint.hashCode();
        return hash;
    }

    @Override
    public Collection<Point> getFormingPoints() {
        return collectionPoint;
    }
}
