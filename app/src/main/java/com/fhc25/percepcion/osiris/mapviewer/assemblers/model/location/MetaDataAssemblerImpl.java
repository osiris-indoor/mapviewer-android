package com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location;

import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.SimpleAssembler;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.MetaDataDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

public class MetaDataAssemblerImpl extends SimpleAssembler<MetaDataDTO, MetaData> {

    @Override
    public MetaData createDomainObject(MetaDataDTO metadataDTO) {

        MetaData metadata = new MetaData();

        metadata.setRoutingChecksum(metadataDTO.getRoutingChecksum());
        metadata.setOsmchecksum(metadataDTO.getOsmchecksum());
        metadata.setMinLatitude(Double.parseDouble(metadataDTO.getMinLatitude()));
        metadata.setMinLongitude(Double.parseDouble(metadataDTO.getMinLongitude()));
        metadata.setMaxLatitude(Double.parseDouble(metadataDTO.getMaxLatitude()));
        metadata.setMaxLongitude(Double.parseDouble(metadataDTO.getMaxLongitude()));
        metadata.setAppId(metadataDTO.getAppId());

        return metadata;
    }

    @Override
    public MetaDataDTO createDataTransferObject(MetaData metadata) {

        MetaDataDTO metadataDTO = new MetaDataDTO();

        metadataDTO.setRoutingChecksum(metadata.getRoutingChecksum());
        metadataDTO.setOsmchecksum(metadata.getOsmchecksum());
        metadataDTO.setMinLatitude(metadata.getMinLatitude().toString());
        metadataDTO.setMinLongitude(metadata.getMinLongitude().toString());
        metadataDTO.setMaxLatitude(metadata.getMaxLatitude().toString());
        metadataDTO.setMaxLongitude(metadata.getMaxLongitude().toString());
        metadataDTO.setAppId(metadata.getAppId());

        return metadataDTO;
    }

}
