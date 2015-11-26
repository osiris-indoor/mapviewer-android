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
