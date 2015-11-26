package com.fhc25.percepcion.osiris.mapviewer.data.indoor.impl;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.data.IBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.common.data.RequestConfiguration;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.data.IOsirisEndpoint;
import com.fhc25.percepcion.osiris.mapviewer.data.Repository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMapFileRepository;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MapsforgeMap;

import java.io.InputStream;

public class MapFileRepository extends Repository implements IMapFileRepository {

    private IOsirisEndpoint endpoint;

    public MapFileRepository(IOsirisEndpoint endpoint, IBackendCaller backendCaller, String appId) {
        super(backendCaller, appId);
        this.endpoint = endpoint;
    }

    @Override
    public ICancellableTask getMapFile(final ICallback<MapsforgeMap> callback) {

        RequestConfiguration requestConfiguration = new RequestConfiguration(endpoint, endpoint.getMapFileService(), "GET");

        return requestStream(requestConfiguration, new ICallback<InputStream>() {
            @Override
            public void onFinish(Failure error, InputStream data) {

                MapsforgeMap mapsforgeMap = null;

                if (error == null) {
                    mapsforgeMap = new MapsforgeMap(data);
                }

                callback.onFinish(error, mapsforgeMap);
            }
        });

    }
}
