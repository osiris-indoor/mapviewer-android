package com.fhc25.percepcion.osiris.mapviewer.common.tasks;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

public abstract class Task<ReturnData> implements ITask {

    private static final String TAG = Task.class.getName();

    private ITaskObserver.Collection observers = new ITaskObserver.Collection();

    public void addTaskObserver(ITaskObserver observer) {
        observers.add(observer);
    }

    public void removeTaskObserver(ITaskObserver observer) {
        observers.remove(observer);
    }

    private ICallback<ReturnData> callback = new ICallback<ReturnData>() {

        @Override
        public void onFinish(Failure error, ReturnData data) {

            if (error != null) {
                onTaskError(error);
                observers.onTaskFinishFailure();
            } else {
                onResultTask(data);
                observers.onTaskFinishSuccess();
            }
        }
    };

    @Override
    public void runTask() {
        onMainTask(callback);
    }

    protected abstract void onMainTask(ICallback<ReturnData> callback);

    protected void onResultTask(ReturnData returnData) {
        // Nothing here by default
    }

    protected void onTaskError(Failure error) {
        Lgr.e(TAG, "Error received:" + error.getMessage());
    }

    public void notifyFinishedTask(Failure error) {
        callback.onFinish(error, null);
    }

}
