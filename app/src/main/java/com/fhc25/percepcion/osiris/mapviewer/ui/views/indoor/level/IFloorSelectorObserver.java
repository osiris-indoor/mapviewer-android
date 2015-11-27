package com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level;

import java.util.LinkedList;

public interface IFloorSelectorObserver {

    void onFloorSelectedChanged(String floor);

    class Collection extends LinkedList<IFloorSelectorObserver> implements IFloorSelectorObserver {

        @Override
        public void onFloorSelectedChanged(String floor) {

            for (IFloorSelectorObserver obs : this) {
                obs.onFloorSelectedChanged(floor);
            }
        }
    }
}
