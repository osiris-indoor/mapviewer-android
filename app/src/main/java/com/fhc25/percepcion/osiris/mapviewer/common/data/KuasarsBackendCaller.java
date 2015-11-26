package com.fhc25.percepcion.osiris.mapviewer.common.data;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.kuasars.KError;
import com.fhc25.percepcion.osiris.mapviewer.common.kuasars.KRestClient;
import com.fhc25.percepcion.osiris.mapviewer.common.kuasars.KRestListener;
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

public class KuasarsBackendCaller implements IBackendCaller {

    private static final String TAG = KuasarsBackendCaller.class.getName();

    private class KuasarsCall implements ICancellableTask {

        private ICallback<InputStream> callback;
        private boolean isCancelled = false;

        public KuasarsCall(ICallback<InputStream> callback) {
            this.callback = callback;
        }

        private KRestListener restListener = new KRestListener() {

            @Override
            public void onComplete(InputStream response) {
                if (!isCancelled) {
                    callback.onFinish(null, response);
                }
            }

            @Override
            public void onError(KError error) {
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

        public KRestListener getRestListener() {
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
        KRestClient.RequestMethod method = getMethod(req);

        Lgr.i(TAG, req.toString());

        String url = req.getUrl();

        KuasarsCall kuasarsCall = new KuasarsCall(callback);

        try {
            KRestClient.Execute(method, url, headers, params, kuasarsCall.getRestListener());
        } catch (Exception e) {
            Lgr.e(TAG, e);
            callback.onFinish(new Failure(e.getMessage()), null);
        }

        return kuasarsCall;
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

    private KRestClient.RequestMethod getMethod(RequestConfiguration requestConfiguration) {

        KRestClient.RequestMethod method = null;

        switch (requestConfiguration.getMethod()) {

            case "GET":
                method = KRestClient.RequestMethod.GET;
                break;
            case "POST":
                method = KRestClient.RequestMethod.POST;
                break;
            case "PUT":
                method = KRestClient.RequestMethod.PUT;
                break;
            case "DELETE":
                method = KRestClient.RequestMethod.DELETE;
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
