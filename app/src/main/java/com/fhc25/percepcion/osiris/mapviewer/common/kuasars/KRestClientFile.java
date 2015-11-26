package com.fhc25.percepcion.osiris.mapviewer.common.kuasars;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class KRestClientFile extends KRestClient {

    private static final String TAG = KRestClientFile.class.getName();

    public enum RequestMethod {
        POST
    }

    public static void ExecuteWithFile(final RequestMethod method,
                                       final String url,
                                       final List<NameValuePair> headers,
                                       final File fileName,
                                       final KRestListener listener) throws Exception {

        new Thread() {

            @Override
            public void run() {

                switch (method) {
                    case POST:
                        HttpPost request = new HttpPost(url);
                        // add headers
                        if (headers != null) {
                            for (NameValuePair h : headers)
                                request.addHeader(h.getName(), h.getValue());
                        }

                        byte[] audioArray = getBytesFromFile(fileName);
                        ByteArrayBody bab = new ByteArrayBody(audioArray, "audio.raw");

                        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        reqEntity.addPart("file", bab);

                        request.setEntity(reqEntity);

                        Lgr.v(TAG, "Request " + request.toString());
                        executeRequest(request, url, listener);

                        break;
                }

            }

        }.start();
    }


    private static byte[] getBytesFromFile(File file) {

        int size = (int) file.length();
        byte[] bytes = new byte[size];

        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

}
