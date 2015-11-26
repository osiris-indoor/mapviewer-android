package com.fhc25.percepcion.osiris.mapviewer.manager.indoor;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMetadataRepository;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;

public class MetadataManager {

    private IInternalState internalState;
    private IMetadataRepository metadataRepository;

    public MetadataManager(IInternalState internalState, IMetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
        this.internalState = internalState;
    }

    public ICancellableTask getMetadata(final ICallback<MetaData> callback) {

        return metadataRepository.getMetadata(new ICallback<MetaData>() {
            @Override
            public void onFinish(Failure error, MetaData data) {
                if (error == null) {
                    internalState.setMetadata(data);
                }

                callback.onFinish(error, data);
            }
        });
    }

}
