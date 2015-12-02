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
import com.fhc25.percepcion.osiris.mapviewer.dto.location.LineStringDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PointDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

import java.util.ArrayList;
import java.util.List;

public class LineStringAssemblerImpl extends
        SimpleAssembler<LineStringDTO, LineString> {

    private PointAssemblerImpl pointAssembler = new PointAssemblerImpl();

    @Override
    public LineString createDomainObject(LineStringDTO lineStringDTO) {

        LineString lineString = new LineString();

        List<Point> pointsList = new ArrayList<>();

        for (PointDTO pointDTO : lineStringDTO.getCollectionPointDTO()) {
            pointsList.add(pointAssembler.createDomainObject(pointDTO));
        }

        lineString.setCollectionPoint(pointsList);

        if (lineStringDTO.getCentroidDTO() != null) {
            lineString.setCentroid(pointAssembler
                    .createDomainObject(lineStringDTO.getCentroidDTO()));
        }

        return lineString;
    }

    @Override
    public LineStringDTO createDataTransferObject(LineString lineString) {

        LineStringDTO lineStringDTO = new LineStringDTO();

        List<PointDTO> listPointDTO = new ArrayList<>();

        for (Point point : lineString.getCollectionPoint()) {
            listPointDTO.add(pointAssembler.createDataTransferObject(point));
        }

        lineStringDTO.setCollectionPointDTO(listPointDTO);

        if (lineString.getCentroid() != null) {
            lineStringDTO.setCentroidDTO(pointAssembler
                    .createDataTransferObject(lineString.getCentroid()));
        }

        return lineStringDTO;
    }

}
