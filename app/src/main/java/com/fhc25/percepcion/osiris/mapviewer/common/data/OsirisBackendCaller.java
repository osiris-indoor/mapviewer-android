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

package com.fhc25.percepcion.osiris.mapviewer.common.data;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.restutils.RestError;
import com.fhc25.percepcion.osiris.mapviewer.common.restutils.RestClient;
import com.fhc25.percepcion.osiris.mapviewer.common.restutils.RestListener;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OsirisBackendCaller implements IBackendCaller {

    private static final String TAG = OsirisBackendCaller.class.getName();

    private class OsirisCall implements ICancellableTask {

        private ICallback<InputStream> callback;
        private boolean isCancelled = false;

        public OsirisCall(ICallback<InputStream> callback) {
            this.callback = callback;
        }

        private RestListener restListener = new RestListener() {

            @Override
            public void onComplete(InputStream response) {
                if (!isCancelled) {
                    callback.onFinish(null, response);
                }
            }

            @Override
            public void onError(RestError error) {
                if (!isCancelled) {
                    callback.onFinish(new Failure(error.getMessage() + ". " + error.getDescription()), null);
                }
            }

            @Override
            public void onConnectionFailed() {
                if (!isCancelled) {
                    callback.onFinish(new Failure("Connection failed"), null);
                }
            }
        };

        public RestListener getRestListener() {
            return restListener;
        }

        @Override
        public boolean cancel() {
            isCancelled = true;
            return true;
        }
    }

    @Override
    public ICancellableTask request(RequestConfiguration req, final ICallback<String> callback) {

        return requestStream(req, new ICallback<InputStream>() {
            @Override
            public void onFinish(Failure error, InputStream data) {
                if (error != null) {
                    callback.onFinish(error, null);
                } else {
                    callback.onFinish(null, convertStreamToString(data));
                }
            }
        });
    }

    @Override
    public ICancellableTask requestStream(RequestConfiguration req, ICallback<InputStream> callback) {

        List<NameValuePair> headers = getHeaders(req);
        List<NameValuePair> params = getBody(req);
        RestClient.RequestMethod method = getMethod(req);

        Lgr.i(TAG, req.toString());

        String url = req.getUrl();

        OsirisCall osirisCall = new OsirisCall(callback);

        try {
            RestClient.Execute(method, url, headers, params, osirisCall.getRestListener());
        } catch (Exception e) {
            Lgr.e(TAG, e);
            callback.onFinish(new Failure(e.getMessage()), null);
        }

        return osirisCall;
    }

    private List<NameValuePair> getHeaders(RequestConfiguration requestConfiguration) {

        List<NameValuePair> headers = new ArrayList<>();

        for (Map.Entry<String, String> pair : requestConfiguration.getHeaders()) {
            headers.add(new BasicNameValuePair(pair.getKey(), pair.getValue()));
        }

        return headers;
    }

    private List<NameValuePair> getBody(RequestConfiguration requestConfiguration) {

        List<NameValuePair> params = new ArrayList<>();

        if (requestConfiguration.getBody() != null) {
            params.add(new BasicNameValuePair("json", requestConfiguration.getBody()));
        }

        return  params;
    }

    private RestClient.RequestMethod getMethod(RequestConfiguration requestConfiguration) {

        RestClient.RequestMethod method = null;

        switch (requestConfiguration.getMethod()) {

            case "GET":
                method = RestClient.RequestMethod.GET;
                break;
            case "POST":
                method = RestClient.RequestMethod.POST;
                break;
            case "PUT":
                method = RestClient.RequestMethod.PUT;
                break;
            case "DELETE":
                method = RestClient.RequestMethod.DELETE;
                break;
        }

        return method;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        int lineCount = 0;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                lineCount++;
            }
            if (lineCount == 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
            is.close();

        } catch (IOException e) {
        }
        return sb.toString();
    }
}
