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

package com.fhc25.percepcion.osiris.mapviewer.common.restutils;


import java.io.InputStream;

/**
 * Listener for the webservice calls using rest.
 */
public interface RestListener {

    /**
     * Called when a request completes with the given response.
     * @param response. Server response data
     */
    void onComplete(InputStream response);

    /**
     * Called when the Web Service method fails.
     * @param error. KError object.
     */
    void onError(RestError error);

    /**
     * Called when the Web service method failed due to connection issues
     */
    void onConnectionFailed();

}
