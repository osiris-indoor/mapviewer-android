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
