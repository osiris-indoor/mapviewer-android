package com.fhc25.percepcion.osiris.mapviewer.data;

import com.fhc25.percepcion.osiris.mapviewer.common.data.Endpoint;

public interface IOsirisEndpoint extends Endpoint {

    String getMapFileService();

    String getMapService();

    String getMetadataService();

}
