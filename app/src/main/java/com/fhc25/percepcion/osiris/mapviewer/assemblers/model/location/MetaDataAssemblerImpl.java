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
