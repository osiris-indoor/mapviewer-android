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

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.List;


/**
 * Performs all the requests to backend via REST, but returns a InputStream
 */
public class RestClientStream {

    private static final String TAG = RestClientStream.class.getCanonicalName();

    private static int mResponseCode = 0;
    private static String mMessage = "";

    private static int CONNECTION_TIMEOUT = 10000;
    private static int SOCKETCONNECTION_TIMEOUT = 15000;

    public enum RequestMethod {
        GET
    }

    public static void Execute(final RequestMethod method,
                               final String url,
                               final List<NameValuePair> headers,
                               final List<NameValuePair> params,
                               final RestListenerStream listener) throws Exception {
        new Thread() {

            @Override
            public void run() {
                switch (method) {
                    case GET: {
                        // add parameters
                        String combinedParams = "";
                        if (params != null) {
                            combinedParams += "?";
                            for (NameValuePair p : params) {

                                String paramString = "";
                                try {
                                    paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                if (combinedParams.length() > 1)
                                    combinedParams += "&" + paramString;
                                else
                                    combinedParams += paramString;
                            }
                        }
                        HttpGet request = new HttpGet(url + combinedParams);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }
                        executeRequest(request, url, listener);
                        break;
                    }
                }
            }

        }.start();
    }

    /**
     * Executes the rest request.
     *
     * @param request  the request to be performed.
     * @param url      the url where to request.
     * @param listener a listener for callbacks.
     *                 <br>Return values are: successful response, http error response code, server timeout (508) and
     *                 internal error (-1).
     */
    private static void executeRequest(HttpUriRequest request, String url, RestListenerStream listener) {

        HttpClient client = getHttpClient();
        serverConnection(client, request, listener);
    }

    private static void serverConnection(HttpClient client, HttpUriRequest request, RestListenerStream listener) {
        try {
            HttpResponse httpResponse = client.execute(request);
            mResponseCode = httpResponse.getStatusLine().getStatusCode();
            mMessage = httpResponse.getStatusLine().getReasonPhrase();
            HttpEntity entity = httpResponse.getEntity();

            if (mResponseCode >= 200 && mResponseCode <= 299) {
                InputStream isResponse = null;
                if (entity != null) {
                    isResponse = entity.getContent();
                }
                listener.onComplete(isResponse);
            } else {
                String errorText = convertStreamToString(entity.getContent());
                Lgr.e(TAG, errorText);
                RestError error = null;
                try {
                    error = new RestError(errorText);
                } catch (JSONException je) {
                    error = new RestError(-1, 3, "Malformed response");
                }
                listener.onError(error);
            }
            Lgr.v(TAG, "ResponseCode: " + mResponseCode);
        } catch (ConnectTimeoutException e) {
            //e.printStackTrace();
            //listener.onError(new KError(508, 0, e.getMessage()));
            listener.onConnectionFailed();
        } catch (SocketTimeoutException e) {
            //e.printStackTrace();
            //listener.onError(new KError(508, 0, e.getMessage()));
            listener.onConnectionFailed();
        } catch (UnknownHostException e) {
            Lgr.e(TAG, e.getMessage());
            listener.onConnectionFailed();
        } catch (IOException e) {
            Lgr.e(TAG, e.getMessage());
            listener.onConnectionFailed();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(new RestError(-1, 0, e.getMessage()));
        }
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

    public static HttpClient getHttpClient() {
        try {

            HttpParams params = new BasicHttpParams();

            // Turn off stale checking.  Our connections break all the time anyway,
            // and it's not worth it to pay the penalty of checking every time.
            HttpConnectionParams.setStaleCheckingEnabled(params, false);

            // Default connection and socket timeout of 20 seconds.  Tweak to taste.
            HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, SOCKETCONNECTION_TIMEOUT);
            HttpConnectionParams.setSocketBufferSize(params, 8192);

            // Don't handle redirects -- return them to the caller.  Our code
            // often wants to re-POST after a redirect, which we must do ourselves.
            HttpClientParams.setRedirecting(params, false);

            SSLSocketFactory mySSLSocketFactory = SSLSocketFactory.getSocketFactory();

            // disable ssl check on debug
            /*
            if (DisableSSLcertificateCheck ) {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                mySSLSocketFactory = new MySSLSocketFactory(trustStore);
                HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
                mySSLSocketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            }
            */

            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", mySSLSocketFactory, 443));
            ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);

            return new DefaultHttpClient(manager, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

}
