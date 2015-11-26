package com.fhc25.percepcion.osiris.mapviewer.model.states.api;

import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

public interface IInternalStateManager {

    IInternalViewState getViewState();

    void saveToBundle(Bundle instanceState);

    void loadFromBundle(Bundle instanceState);

    void persistInternalStatePersistent();

    void persistInternalStateVariable();

    void loadInternalState();

    MetaData persistedDataExists();
}
