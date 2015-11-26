package com.fhc25.percepcion.osiris.mapviewer.ui.application;

import android.app.Application;
import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.R;
import com.fhc25.percepcion.osiris.mapviewer.common.data.IBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.common.data.KuasarsBackendCaller;
import com.fhc25.percepcion.osiris.mapviewer.data.IOsirisEndpoint;
import com.fhc25.percepcion.osiris.mapviewer.data.OsirisEndpoint;
import com.fhc25.percepcion.osiris.mapviewer.manager.ApplicationManager;
import com.fhc25.percepcion.osiris.mapviewer.manager.IApplicationManagerProvider;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.InternalState;
import com.fhc25.percepcion.osiris.mapviewer.model.states.InternalStatePersistor;
import com.fhc25.percepcion.osiris.mapviewer.model.states.InternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalStateManager;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalStatePersistor;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;

public class OsirisApplication extends Application implements IApplicationManagerProvider, IInternalStateManager {

    private static final String TAG = OsirisApplication.class.getName();

    private ApplicationManager applicationManager;
    private IInternalState internalState;
    private IInternalViewState internalViewState;
    private IBackendCaller backendCaller;
    private IOsirisEndpoint endpoint;

    private IInternalStatePersistor internalStatePersistor;

    @Override
    public void onCreate() {

        String appHost = getResources().getString(R.string.app_host);
        String appId = getResources().getString(R.string.app_id);

        createApplicationManager(appId, appHost);

        internalStatePersistor = new InternalStatePersistor(getBaseContext(), this);
    }

    @Override
    public ApplicationManager getApplicationManager() {
        return applicationManager;
    }

    private void createApplicationManager(String appId, String host) {

        endpoint = new OsirisEndpoint(host);
        internalState = new InternalState();
        internalViewState = new InternalViewState(internalState);
        backendCaller = new KuasarsBackendCaller();

        applicationManager = new ApplicationManager(getApplicationContext(), internalState, endpoint, backendCaller, appId);
    }

    @Override
    public IInternalViewState getViewState() {
        return internalViewState;
    }

    @Override
    public void saveToBundle(Bundle instanceState) {
        applicationManager.getBundleInternalStateManager().saveInternalState(instanceState, internalState);
    }

    @Override
    public void loadFromBundle(Bundle instanceState) {
        applicationManager.getBundleInternalStateManager().loadInternalState(instanceState, internalState);
    }

    @Override
    public void persistInternalStatePersistent() {
        internalStatePersistor.persistInternalStatePersistent(internalState);
    }

    @Override
    public void persistInternalStateVariable() {
        internalStatePersistor.persistInternalStateVariable(internalState);
    }

    @Override
    public void loadInternalState() {
        internalStatePersistor.loadInternalState(internalState);
    }

    @Override
    public MetaData persistedDataExists() {
        return internalStatePersistor.persistedDataExists();
    }
}
