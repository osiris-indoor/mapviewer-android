
package com.fhc25.percepcion.osiris.mapviewer.data.indoor.api;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MapsforgeMap;

public interface IMapFileRepository {

    ICancellableTask getMapFile(final ICallback<MapsforgeMap> callback);

}
