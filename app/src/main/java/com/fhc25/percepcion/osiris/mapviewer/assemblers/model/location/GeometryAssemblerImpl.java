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

package com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location;

import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.SimpleAssembler;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.GeometryDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.LineStringDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PointDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PolygonDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Geometry;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Polygon;

public class GeometryAssemblerImpl extends
        SimpleAssembler<GeometryDTO, Geometry> {

    @Override
    public Geometry createDomainObject(GeometryDTO geometryDTO) {

        Geometry geometry = null;

        if (geometryDTO instanceof PointDTO) {
            PointAssemblerImpl pointAssembler = new PointAssemblerImpl();
            geometry = pointAssembler
                    .createDomainObject((PointDTO) geometryDTO);
        } else if (geometryDTO instanceof LineStringDTO) {
            LineStringAssemblerImpl lineStringAssembler = new LineStringAssemblerImpl();
            geometry = lineStringAssembler
                    .createDomainObject((LineStringDTO) geometryDTO);
        } else if (geometryDTO instanceof PolygonDTO) {
            PolygonAssemblerImpl polygonAssembler = new PolygonAssemblerImpl();
            geometry = polygonAssembler
                    .createDomainObject((PolygonDTO) geometryDTO);
        }

        return geometry;
    }

    @Override
    public GeometryDTO createDataTransferObject(Geometry geometry) {

        GeometryDTO geometryDTO = null;

        if (geometry instanceof Point) {
            PointAssemblerImpl pointAssembler = new PointAssemblerImpl();
            geometryDTO = pointAssembler
                    .createDataTransferObject((Point) geometry);
        } else if (geometry instanceof LineString) {
            LineStringAssemblerImpl lineStringAssembler = new LineStringAssemblerImpl();
            geometryDTO = lineStringAssembler
                    .createDataTransferObject((LineString) geometry);
        } else if (geometry instanceof Polygon) {
            PolygonAssemblerImpl polygonAssembler = new PolygonAssemblerImpl();
            geometryDTO = polygonAssembler
                    .createDataTransferObject((Polygon) geometry);
        }

        return geometryDTO;
    }
}
