package com.fhc25.percepcion.osiris.mapviewer.model.states.api;


import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

public interface IInternalStatePersistor {

    void persistInternalState(IInternalState internalState);

    void persistInternalStatePersistent(IInternalState internalState);

    void persistInternalStateVariable(IInternalState internalState);

    void loadInternalState(IInternalState internalState);

    MetaData persistedDataExists();
}
