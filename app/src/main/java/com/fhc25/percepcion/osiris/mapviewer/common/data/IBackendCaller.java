package com.fhc25.percepcion.osiris.mapviewer.common.data;


import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;

import java.io.InputStream;

public interface IBackendCaller {

    ICancellableTask request(RequestConfiguration req, ICallback<String> callback);

    ICancellableTask requestStream(RequestConfiguration req, ICallback<InputStream> callback);
}
