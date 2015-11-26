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
