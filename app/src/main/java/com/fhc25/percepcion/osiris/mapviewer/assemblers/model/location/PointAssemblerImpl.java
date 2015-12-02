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
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PointDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

public class PointAssemblerImpl extends SimpleAssembler<PointDTO, Point> {

    @Override
    public Point createDomainObject(PointDTO pointDTO) {

        Point point = new Point();

        point.setLatitude(pointDTO.getLatitude());
        point.setLongitude(pointDTO.getLongitude());

        return point;
    }

    @Override
    public PointDTO createDataTransferObject(Point point) {

        PointDTO pointDTO = new PointDTO();

        pointDTO.setLatitude(point.getLatitude());
        pointDTO.setLongitude(point.getLongitude());

        return pointDTO;
    }
}
