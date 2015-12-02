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

package com.fhc25.percepcion.osiris.mapviewer.manager;

import android.content.Context;

import com.fhc25.percepcion.osiris.mapviewer.common.data.IBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.data.IOsirisEndpoint;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMapFileRepository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMapRepository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMetadataRepository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.impl.MapFileRepository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.impl.MapRepository;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.impl.MetadataRepository;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.BuildingsManager;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.MapsforgeMapManager;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.MetadataManager;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingGroupBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.states.BundleInternalStateManager;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;

public class ApplicationManager {

    private static final String TAG = ApplicationManager.class.getName();

    private final static int defaultPageSize = 100;

    private final IInternalState internalState;

    private final IOsirisEndpoint endpoint;
    private final IBackendCaller backendCaller;
    private final String appId;

    private IMetadataRepository metadataRepository;
    private IMapFileRepository mapFileRepository;
    private IMapRepository mapRepository;

    private MetadataManager metadataManager;
    private BuildingsManager buildingsManager;
    private MapsforgeMapManager mapsforgeMapManager;

    private BundleInternalStateManager bundleInternalStateManager;

    private BuildingGroupBuilder buildingGroupBuilder;

    public ApplicationManager(Context context, IInternalState internalState, IOsirisEndpoint endpoint,
                              IBackendCaller backendCaller, String appId) {

        this.internalState = internalState;
        this.endpoint = endpoint;
        this.backendCaller = backendCaller;
        this.appId = appId;

        buildingGroupBuilder = new BuildingGroupBuilder();

        metadataRepository = new MetadataRepository(endpoint, backendCaller, appId);
        mapFileRepository = new MapFileRepository(endpoint, backendCaller, appId);

        MapRepository repository = new MapRepository(endpoint, backendCaller, appId);
        repository.setDefaultPageSize(defaultPageSize);
        mapRepository = repository;

        metadataManager = new MetadataManager(internalState, metadataRepository);
        buildingsManager = new BuildingsManager(internalState, mapRepository, buildingGroupBuilder);
        mapsforgeMapManager = new MapsforgeMapManager(mapFileRepository);
        bundleInternalStateManager = new BundleInternalStateManager();
    }

    public IMetadataRepository getMetadataRepository() {
        return metadataRepository;
    }

    public MetadataManager getMetadataManager() {
        return metadataManager;
    }

    public BuildingsManager getBuildingsManager() {
        return buildingsManager;
    }

    public MapsforgeMapManager getMapsforgeMapManager() {
        return mapsforgeMapManager;
    }

    public BundleInternalStateManager getBundleInternalStateManager() {
        return bundleInternalStateManager;
    }

}
