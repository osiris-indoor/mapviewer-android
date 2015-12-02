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
