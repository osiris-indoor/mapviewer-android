package com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location;

import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.SimpleAssembler;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PolygonDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Polygon;

public class PolygonAssemblerImpl extends SimpleAssembler<PolygonDTO, Polygon> {

    private LineStringAssemblerImpl lineStringAssembler = new LineStringAssemblerImpl();

    @Override
    public Polygon createDomainObject(PolygonDTO polygonDTO) {

        Polygon polygon = new Polygon();
        polygon.setCollectionLineString(lineStringAssembler
                .createDomainObjects(polygonDTO.getCollectionLineStringDTO()));

        return polygon;
    }

    @Override
    public PolygonDTO createDataTransferObject(Polygon polygon) {

        PolygonDTO polygonDTO = new PolygonDTO();
        polygonDTO
                .setCollectionLineStringDTO(lineStringAssembler
                        .createDataTransferObjects(polygon
                                .getCollectionLineStringDTO()));

        return polygonDTO;
    }

}
