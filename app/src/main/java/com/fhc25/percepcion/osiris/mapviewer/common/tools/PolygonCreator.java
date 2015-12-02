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
