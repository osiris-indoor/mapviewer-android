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
import com.fhc25.percepcion.osiris.mapviewer.dto.location.FeatureDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeatureAssemblerImpl extends SimpleAssembler<FeatureDTO, Feature> {

    private GeometryAssemblerImpl geometryAssembler = new GeometryAssemblerImpl();

    @Override
    public Feature createDomainObject(FeatureDTO featureDTO) {

        Feature feature = new Feature();

        feature.setId(featureDTO.getId());

        feature.setProperties(new HashMap<String, String>(featureDTO
                .getProperties()));
        feature.setPropertiesRelations(new ArrayList<Map<String, String>>(
                featureDTO.getPropertiesRelations()));
        feature.setGeometry(geometryAssembler.createDomainObject(featureDTO.getGeometryDTO()));

        return feature;
    }

    @Override
    public FeatureDTO createDataTransferObject(Feature feature) {

        FeatureDTO featureDTO = new FeatureDTO();

        featureDTO.setId(feature.getId());

        featureDTO.setProperties(new HashMap<String, String>(feature
                .getProperties()));
        featureDTO.setPropertiesRelations(new ArrayList<Map<String, String>>(
                feature.getPropertiesRelations()));
        featureDTO.setGeometryDTO(geometryAssembler.createDataTransferObject(feature.getGeometry()));

        return featureDTO;
    }

}
