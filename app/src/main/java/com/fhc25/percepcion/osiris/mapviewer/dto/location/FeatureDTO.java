
package com.fhc25.percepcion.osiris.mapviewer.dto.location;

import java.util.List;
import java.util.Map;

public class FeatureDTO {

    private Map<String, String> properties;
    private List<Map<String, String>> propertiesRelations;
    private String id;
    private GeometryDTO geometryDTO;


    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public List<Map<String, String>> getPropertiesRelations() {
        return propertiesRelations;
    }

    public void setPropertiesRelations(List<Map<String, String>> propertiesRelations) {
        this.propertiesRelations = propertiesRelations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeometryDTO getGeometryDTO() {
        return geometryDTO;
    }

    public void setGeometryDTO(GeometryDTO geometryDTO) {
        this.geometryDTO = geometryDTO;
    }

}
