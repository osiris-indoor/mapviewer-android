
package com.fhc25.percepcion.osiris.mapviewer.dto.location;

import java.util.List;

public class LineStringDTO extends GeometryDTO {

    private List<PointDTO> collectionPointDTO;
    private PointDTO centroidDTO;

    public List<PointDTO> getCollectionPointDTO() {
        return collectionPointDTO;
    }

    public void setCollectionPointDTO(List<PointDTO> collectionPointDTO) {
        this.collectionPointDTO = collectionPointDTO;
    }

    public PointDTO getCentroidDTO() {
        return centroidDTO;
    }

    public void setCentroidDTO(PointDTO centroidDTO) {
        this.centroidDTO = centroidDTO;
    }
}
