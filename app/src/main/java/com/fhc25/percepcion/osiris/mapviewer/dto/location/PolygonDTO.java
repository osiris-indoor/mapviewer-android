
package com.fhc25.percepcion.osiris.mapviewer.dto.location;

import java.util.Collection;

public class PolygonDTO extends GeometryDTO {

    private Collection<LineStringDTO> collectionLineStringDTO;

    public Collection<LineStringDTO> getCollectionLineStringDTO() {
        return collectionLineStringDTO;
    }

    public void setCollectionLineStringDTO(
            Collection<LineStringDTO> collectionLineStringDTO) {
        this.collectionLineStringDTO = collectionLineStringDTO;
    }

}
