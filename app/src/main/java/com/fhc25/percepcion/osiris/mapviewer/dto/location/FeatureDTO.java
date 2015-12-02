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
