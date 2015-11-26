package com.fhc25.percepcion.osiris.mapviewer.common.tasks;

import java.util.ArrayList;

public interface ITaskObserver {

    void onTaskFinishSuccess();

    void onTaskFinishFailure();

    class Collection extends ArrayList<ITaskObserver> implements ITaskObserver {

        @Override
        public void onTaskFinishSuccess() {

            for (ITaskObserver observer : this) {
                observer.onTaskFinishSuccess();
            }
        }

        @Override
        public void onTaskFinishFailure() {

            for (ITaskObserver observer : this) {
                observer.onTaskFinishFailure();
            }
        }
    }

}
