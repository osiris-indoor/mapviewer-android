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

import android.os.AsyncTask;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class BackgroundBackendCaller implements IBackendCaller {

    private static final String TAG = BackgroundBackendCaller.class.getName();

    private boolean synchronous = false;
    private boolean finishedTask = false;

    public void setSynchronous(boolean synchronous) {
        this.synchronous = synchronous;
    }

    private class MyAsyncReturn {

        private Failure error;
        private int responseCode;
        private String returnMsg;

        public MyAsyncReturn(Failure error) {
            this.error = error;
        }

        public MyAsyncReturn(int responseCode, String returnMsg) {
            this.returnMsg = returnMsg;
            this.responseCode = responseCode;
        }

        public Failure getError() {
            return error;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getReturnMsg() {
            return returnMsg;
        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, MyAsyncReturn> implements ICancellableTask {

        private RequestConfiguration req;
        private ICallback<String> callback;

        /**
         *
         * @param req
         * @param callback
         */
        public MyAsyncTask(RequestConfiguration req, ICallback<String> callback) {
            this.req = req;
            this.callback = callback;
        }

        @Override
        protected MyAsyncReturn doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            MyAsyncReturn asyncReturn = null;

            Lgr.i(TAG, req.toString());

            try {
                URL url = new URL(req.getEndpoint().getHost() + req.getUrl());
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod(req.getMethod());

                for (Map.Entry<String, String> header : req.getHeaders()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }

                if (req.getMethod().equals("GET") || req.getMethod().equals("DELETE")) {
                    connection.connect();
                } else {
                    if (req.getBody() != null) {
                        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                        wr.write(req.getBody().getBytes());
                        wr.close();
                        wr.flush();
                    } else {
                        Lgr.e(TAG, "Body was not set");
                    }
                }

                String decodedString;
                String returnMsg = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(connection
                        .getInputStream()));
                while ((decodedString = in.readLine()) != null) {
                    returnMsg += decodedString;
                }
                in.close();
                connection.disconnect();
                asyncReturn = new MyAsyncReturn(connection.getResponseCode(), returnMsg);

                Lgr.i(TAG, "response: " + returnMsg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                asyncReturn = new MyAsyncReturn(new Failure(e.getMessage()));
            } catch (ProtocolException e) {
                e.printStackTrace();
                asyncReturn = new MyAsyncReturn(new Failure(e.getMessage()));
            } catch (IOException e) {
                e.printStackTrace();
                asyncReturn = new MyAsyncReturn(new Failure(e.getMessage()));
            }

            return asyncReturn;
        }

        @Override
        protected void onPostExecute(MyAsyncReturn result){
            callback.onFinish(result.getError(), result.getReturnMsg());
            finishedTask = true;
        }

        @Override
        public boolean cancel() {
            return this.cancel(true);
        }
    }

    /*
    * TODO: pick the information in req and pass just the URL to the background process. The class needs to be designed condisering that req can change when exiting this function
    * */
    @Override
    public ICancellableTask request(RequestConfiguration req, ICallback<String> callback) {

        finishedTask = false;

        MyAsyncTask task = new MyAsyncTask(req, callback);

        task.execute();

        if (synchronous) {
            while (!finishedTask) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Lgr.e(TAG, e);
                    callback.onFinish(new Failure(e.getMessage()), null);
                }
            }
        }

        return task;
    }

    @Override
    public ICancellableTask requestStream(RequestConfiguration req, ICallback<InputStream> callback) {
        return null;
    }

}
