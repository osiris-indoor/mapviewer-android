
package com.fhc25.percepcion.osiris.mapviewer.dto.location;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = PointDTO.class, name = "point"),
		@JsonSubTypes.Type(value = LineStringDTO.class, name = "lineString"),
		@JsonSubTypes.Type(value = PolygonDTO.class, name = "polygon"), })
public class GeometryDTO {

}
