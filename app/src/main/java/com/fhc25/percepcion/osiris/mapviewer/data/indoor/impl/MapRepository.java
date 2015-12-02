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

package com.fhc25.percepcion.osiris.mapviewer.data.indoor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location.FeatureAssemblerImpl;
import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.Assembler;
import com.fhc25.percepcion.osiris.mapviewer.common.data.IBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.common.data.RequestConfiguration;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.json.JSONParser;
import com.fhc25.percepcion.osiris.mapviewer.data.IOsirisEndpoint;
import com.fhc25.percepcion.osiris.mapviewer.data.Repository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMapRepository;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.FeatureDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.query.MongoGeospatialQuery;
import com.fhc25.percepcion.osiris.mapviewer.model.location.query.api.IMongoGeospatialQueryParser;

import java.util.ArrayList;
import java.util.Collection;

public class MapRepository extends Repository implements IMapRepository {

    private IOsirisEndpoint endpoint;

    private JSONParser<FeatureDTO> parser = new JSONParser<>(FeatureDTO.class);
    private Assembler<FeatureDTO, Feature> assembler = new FeatureAssemblerImpl();

    public MapRepository(IOsirisEndpoint endpoint, IBackendCaller backendCaller, String appId) {

        super(backendCaller, appId);
        this.endpoint = endpoint;
    }

    @Override
    public ICancellableTask getMap(MongoGeospatialQuery query, String layer, IMongoGeospatialQueryParser mongoGeospatialQueryParser,
                                   final ICallback<Collection<Feature>> callback) {

        RequestConfiguration requestConfiguration = new RequestConfiguration(endpoint, endpoint.getMapService(), "POST");
        requestConfiguration.setQueryParams("?layer=" + layer);
        requestConfiguration.addHeader("Content-Type", "application/json");

        requestConfiguration.setBody(mongoGeospatialQueryParser.toJSON(query));

        final Collection<Feature> features = new ArrayList<>();

        return requestWithPagination(requestConfiguration, new PaginationCallback() {
            @Override
            public boolean pageQueryReturned(String response) {

                Collection<FeatureDTO> receivedFeaturesDTO = parser.parseObjectCollection(response, new TypeReference<Collection<FeatureDTO>>() {});

                if (!receivedFeaturesDTO.isEmpty()) {
                    Collection<Feature> receivedFeatures = assembler.createDomainObjects(receivedFeaturesDTO);
                    features.addAll(receivedFeatures);
                    return true;
                }

                return false;
            }

            @Override
            public void onFinish(Failure error, Void data) {
                callback.onFinish(error, features);
            }
        });
    }
}
