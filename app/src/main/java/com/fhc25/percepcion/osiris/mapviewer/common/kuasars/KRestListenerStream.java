package com.fhc25.percepcion.osiris.mapviewer.common.kuasars;

import java.io.InputStream;

public interface KRestListenerStream {

	/**
     * Called when a request completes with the given response.
     * @param response. Server response data
     */
    void onComplete(InputStream response);

    /**
     * Called when the Web Service method fails.
     * @param error. KError object.
     */
    void onError(KError error);

    /**
     * Called when the Web service method failed due to connection issues
     */
    void onConnectionFailed();
    
}
