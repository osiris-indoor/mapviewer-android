package com.fhc25.percepcion.osiris.mapviewer.data;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.data.IBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.common.data.RequestConfiguration;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;

import java.io.InputStream;

public class Repository {

    private String appId;
    private int defaultPageSize = 20;

    private IBackendCaller backendCaller;

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public Repository(IBackendCaller backendCaller, String appId) {
        this.appId = appId;
        this.backendCaller = backendCaller;
    }

    public ICancellableTask request(RequestConfiguration configuration, ICallback<String> callback) {

        addHeaders(configuration);
        return backendCaller.request(configuration, callback);
    }

    public ICancellableTask requestStream(RequestConfiguration configuration, ICallback<InputStream> callback) {
        addHeaders(configuration);
        return backendCaller.requestStream(configuration, callback);
    }

    private void addHeaders(RequestConfiguration configuration) {

        if (!configuration.containsHeaderKey("api_key")) {
            configuration.addHeader("api_key", appId);
        }

        if (!configuration.containsHeaderKey("Cache-Control")) {
            configuration.addHeader("Cache-Control", "no-cache");
        }

        if (!configuration.containsHeaderKey("Pragma")) {
            configuration.addHeader("Pragma", "no-cache");
        }

    }

    public ICancellableTask requestWithPagination(RequestConfiguration configuration, PaginationCallback callback) {

        PaginationCancellableTask cancellableTask = new PaginationCancellableTask();
        recursiveRequestWithPagination(cancellableTask, configuration, callback, defaultPageSize, 0);

        return cancellableTask;
    }

    private void recursiveRequestWithPagination(final PaginationCancellableTask cancellableTask, final RequestConfiguration configuration,
                                                final PaginationCallback callback, final int pageSize, final int pageIndex) {

        String originalPathParams = configuration.getPathParams();
        String pathParams = originalPathParams + ";pageSize=" + pageSize + ";pageIndex=" + pageIndex;
        configuration.setPathParams(pathParams);

        ICancellableTask task = request(configuration, new ICallback<String>() {
            @Override
            public void onFinish(Failure error, String data) {
                if (error != null) {
                    callback.onFinish(error, null);
                } else {
                    if (callback.pageQueryReturned(data)) {
                        recursiveRequestWithPagination(cancellableTask, configuration, callback, pageSize, pageIndex + 1);
                    } else {
                        callback.onFinish(null, null);
                    }
                }
            }
        });

        cancellableTask.setCancellableTask(task);
        configuration.setPathParams(originalPathParams);
    }

    public class PaginationCancellableTask implements ICancellableTask {

        private ICancellableTask cancellableTask;

        public void setCancellableTask(ICancellableTask cancellableTask) {
            this.cancellableTask = cancellableTask;
        }

        @Override
        public boolean cancel() {
            if (cancellableTask != null) {
                return cancellableTask.cancel();
            }

            return true;
        }
    }

    public interface PaginationCallback extends ICallback<Void> {

        /**
         * Returns if the pagination queries sould continue
         *
         * @param response
         * @return
         */
        boolean pageQueryReturned(String response);
    }
}
