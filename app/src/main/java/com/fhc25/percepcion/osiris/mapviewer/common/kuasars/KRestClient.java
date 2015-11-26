package com.fhc25.percepcion.osiris.mapviewer.common.kuasars;

import android.util.Log;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Performs all the requests to backend via REST.
 */
public class KRestClient {
    private static final String TAG = "KRestClient";

    private static int CONNECTION_TIMEOUT = 10000;
    private static int SOCKETCONNECTION_TIMEOUT = 15000;

    public enum RequestMethod {
        GET,
        POST,
        PUT,
        PATCH,
        HEAD,
        DELETE
    }

    private static int responseCode = 0;
    private static String message;
    private static String response;
    private static StringEntity se;

    /**
     * Executes the requests.
     *
     * @param method   the type of method (POST, GET, DELETE, PUT).
     * @param url      the url of the request.
     * @param headers  the headers to include.
     * @param params   the params to include (if any)
     * @param listener a listener for callbacks.
     * @throws Exception
     */
    public static void Execute(final RequestMethod method,
                               final String url,
                               final List<NameValuePair> headers,
                               final List<NameValuePair> params,
                               final KRestListener listener) throws Exception {
        new Thread() {
            @Override
            public void run() {
                switch (method) {
                    case GET: {
                        // add parameters
                        String combinedParams = "";
                        if (params != null) {
                            if (!params.isEmpty()) {
                                combinedParams += "?";
                            }
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

                    case POST: {
                        HttpPost request = new HttpPost(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }
                        if (params != null) {
                            try {
                                if (params.get(0).getName() == "json") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.UTF_8);
                                    //se.setContentEncoding( HTTP.DEFAULT_CONTENT_CHARSET);

                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending json parameter: " + params.get(0).getValue());
                                    if (se != null) {
                                        Lgr.v(TAG, "Entity string: " + se.toString());
                                        if (se.getContentEncoding() != null) {
                                            Lgr.v(TAG, "Entity encoding: " + request.getEntity().getContentEncoding().getValue());
                                        }
                                    }

                                } else if (params.get(0).getName() == "xml") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending xml parameter: " + params.get(0).getValue());
                                } else {
                                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.DEFAULT_CONTENT_CHARSET));
                                    Lgr.v(TAG, "Sending UrlEncodedForm parameters ");
                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Lgr.v(TAG, "Request " + request.toString());
                        executeRequest(request, url, listener);

                        break;
                    }

                    case PUT: {
                        HttpPut request = new HttpPut(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }
                        if (params != null) {
                            try {
                                if (params.get(0).getName() == "json") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending json parameter: " + params.get(0).getValue());
                                } else if (params.get(0).getName() == "xml") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending xml parameter: " + params.get(0).getValue());
                                } else if (params.get(0).getName() == "string") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending string parameter: " + params.get(0).getValue());
                                } else {
                                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.DEFAULT_CONTENT_CHARSET));
                                    Lgr.v(TAG, "Sending UrlEncodedForm parameters ");
                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Lgr.v(TAG, "Request " + request.toString());
                        executeRequest(request, url, listener);

                        break;
                    }

                    case PATCH: {
                        HttpPatch request = new HttpPatch(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }
                        if (params != null) {
                            try {
                                if (params.get(0).getName() == "json") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending json parameter: " + params.get(0).getValue());
                                } else if (params.get(0).getName() == "xml") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending xml parameter: " + params.get(0).getValue());
                                } else if (params.get(0).getName() == "string") {
                                    se = new StringEntity(params.get(0).getValue(), HTTP.DEFAULT_CONTENT_CHARSET);
                                    request.setEntity(se);
                                    Lgr.v(TAG, "Sending string parameter: " + params.get(0).getValue());
                                } else {
                                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.DEFAULT_CONTENT_CHARSET));
                                    Lgr.v(TAG, "Sending UrlEncodedForm parameters ");
                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Lgr.v(TAG, "Request " + request.toString());
                        executeRequest(request, url, listener);

                        break;
                    }

                    case HEAD: {
                        HttpHead request = new HttpHead(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }

                        Lgr.v(TAG, "Request " + request.toString());

                        executeRequest(request, url, listener);

                        break;
                    }

                    case DELETE: {
                        HttpDelete request = new HttpDelete(url);

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
     * Executes the requests.
     *
     * @param method   the type of method (POST, GET, DELETE, PUT).
     * @param url      the url of the request.
     * @param headers  the headers to include.
     * @param listener a listener for callbacks.
     * @throws Exception
     */
    public static void Execute(final File file,
                               final RequestMethod method,
                               final String url,
                               final ArrayList<NameValuePair> headers,
                               final KRestListener listener) throws Exception {
        new Thread() {
            @Override
            public void run() {

                switch (method) {
                    case GET:
                        // Do nothing
                        break;

                    case POST: {

                        HttpPost request = new HttpPost(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }

                        // code file
                        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        Log.d(TAG, "UPLOAD: file length = " + file.length());
                        Log.d(TAG, "UPLOAD: file exist = " + file.exists());
                        FileBody bodyPart = new FileBody(file);
                        entity.addPart("file", bodyPart);
                        request.setEntity(entity);

                        Log.i(TAG, "Request with File:" + request.getEntity());

                        executeRequest(request, url, listener);
                    }
                    break;

                    case PUT: {
                        HttpPut request = new HttpPut(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }

                        // code file
                        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        Log.d(TAG, "UPLOAD: file length = " + file.length());
                        Log.d(TAG, "UPLOAD: file exist = " + file.exists());
                        FileBody bodyPart = new FileBody(file);
                        entity.addPart("file", bodyPart);
                        request.setEntity(entity);


                        Log.v(TAG, "Request " + request.toString());
                        executeRequest(request, url, listener);
                    }
                    break;

                    case DELETE:
                        // Do nothing
                        break;

                } // switch end
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
    protected static void executeRequest(HttpUriRequest request, String url, KRestListener listener) {

        HttpClient client = getHttpClient();
        //HttpResponse httpResponse;

        serverConnection(client, request, listener);

        /*
        if (Utils.isOnlineNoToast(Kuasars.getContext())) {
            serverConnection(client, request, listener);
        } else {
            try {
                Thread.currentThread().sleep(2000);
                Lgr.v(TAG, "waits 2 seconds");
                if (Utils.isOnlineNoToast(Kuasars.getContext())) {
                    serverConnection(client, request, listener);
                } else {
                    Thread.currentThread().sleep(5000);
                    Lgr.v(TAG, "waits 5 seconds");
                    if (Utils.isOnlineNoToast(Kuasars.getContext())) {
                        serverConnection(client, request, listener);
                    } else {
                        listener.onConnectionFailed();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                listener.onError(new KError(-1, 2, e.getMessage()));
            }

        }
        */
    }

    private static void serverConnection(HttpClient client, HttpUriRequest request, KRestListener listener) {
        try {
            HttpResponse httpResponse = client.execute(request);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();
            HttpEntity entity = httpResponse.getEntity();

            if (responseCode >= 200 && responseCode <= 299) {
                InputStream isResponse = null;
                if (entity != null) {
                    isResponse = entity.getContent();
                }
                listener.onComplete(isResponse);
            } else {
                String errorText = convertStreamToString(entity.getContent());
                Lgr.e(TAG, errorText);
                KError error = null;
                try {
                    error = new KError(errorText);
                } catch (JSONException je) {
                    error = new KError(-1, 3, "Malformed response");
                }
                listener.onError(error);
            }
            Lgr.v(TAG, "ResponseCode: " + responseCode);
        } catch (ConnectTimeoutException e) {
            Lgr.e(TAG, e.getMessage());
            //e.printStackTrace();
            //listener.onError(new KError(508, 0, e.getMessage()));
            listener.onConnectionFailed();
        } catch (SocketTimeoutException e) {
            Lgr.e(TAG, e.getMessage() + " Socket timeout");
            //e.printStackTrace();
            //listener.onError(new KError(508, 0, "Server timeout"));
            listener.onConnectionFailed();
        } catch (UnknownHostException e) {
            Lgr.e(TAG, e.getMessage() + " Connection failed");
            /*
            KError error = null;
            try {
                error = new KError(e.getMessage());
            } catch (JSONException e1) {
                Lgr.e(TAG, e.getMessage());
            }
            listener.onError(error);
            */

            listener.onConnectionFailed();

        } catch (IOException e) {
            Lgr.e(TAG, e.getMessage());

            KError error = null;
            try {
                error = new KError(e.getMessage());
            } catch (JSONException e1) {
                Lgr.e(TAG, e.getMessage());
            }

            listener.onError(error);

        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(new KError(-1, 0, e.getMessage()));
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

    private static String convertGZIPStreamToString(GZIPInputStream is) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        int lineCount = 0;
        try {
            Reader decoder = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(decoder);
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                lineCount++;
            }
            if (lineCount == 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
            is.close();
            decoder.close();
            reader.close();

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
            /*
            HttpConnectionParams.setConnectionTimeout(params, Kuasars.getConnectionTimeout());
            HttpConnectionParams.setSoTimeout(params, Kuasars.getSocketTimeout());
            */
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