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
