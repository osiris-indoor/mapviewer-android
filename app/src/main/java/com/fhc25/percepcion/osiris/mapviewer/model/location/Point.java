/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Point geometry object. It is represented as a two dimensional geographic
 * point
 */
public class Point extends Geometry implements Serializable {

    private Double latitude;
    private Double longitude;

    public Point() {
        latitude = Double.valueOf(0.0);
        longitude = Double.valueOf(0.0);
    }

    public Point(Double lat, Double lon) {
        latitude = lat;
        longitude = lon;
    }

    /**
     * Gets the point's latitude
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets the point's latitude
     *
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the point's longitude
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets the point's longitude
     *
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Point)) {
            return false;
        }

        if (o == this) {
            return true;
        }

        Point p = (Point) o;

        if (p.latitude.equals(this.latitude) && p.longitude.equals(this.longitude)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = hash * 89 + (latitude != null ? latitude.hashCode() : 0);
        hash = hash * 89 + (longitude != null ? longitude.hashCode() : 0);

        return hash;
    }

    @Override
    public Collection<Point> getFormingPoints() {

        Collection<Point> points = new ArrayList<>();
        points.add(this);

        return points;
    }

    @Override
    public String toString() {

        return "(" + latitude + ", " + longitude + ")";
    }
}
