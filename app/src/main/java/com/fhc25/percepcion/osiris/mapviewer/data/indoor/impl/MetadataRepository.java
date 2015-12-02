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

import com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location.MetaDataAssemblerImpl;
import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.ParsingCallback;
import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.Assembler;
import com.fhc25.percepcion.osiris.mapviewer.common.data.IBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.common.data.RequestConfiguration;
import com.fhc25.percepcion.osiris.mapviewer.common.json.JSONParser;
import com.fhc25.percepcion.osiris.mapviewer.data.IOsirisEndpoint;
import com.fhc25.percepcion.osiris.mapviewer.data.Repository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMetadataRepository;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.MetaDataDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

public class MetadataRepository extends Repository implements IMetadataRepository {

    private Assembler<MetaDataDTO, MetaData> assembler = new MetaDataAssemblerImpl();
    private JSONParser<MetaDataDTO> parser = new JSONParser<>(MetaDataDTO.class);
    private IOsirisEndpoint endpoint;

    public MetadataRepository(IOsirisEndpoint endpoint, IBackendCaller backendCaller, String appId) {
        super(backendCaller, appId);
        this.endpoint = endpoint;
    }

    @Override
    public ICancellableTask getMetadata(ICallback<MetaData> callback) {

        RequestConfiguration requestConfiguration = new RequestConfiguration(endpoint, endpoint.getMetadataService(), "GET");

        return request(requestConfiguration, new ParsingCallback<>(parser, assembler, callback));
    }
}
