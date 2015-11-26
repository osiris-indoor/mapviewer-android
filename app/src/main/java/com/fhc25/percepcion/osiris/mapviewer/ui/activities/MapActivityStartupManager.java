package com.fhc25.percepcion.osiris.mapviewer.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.R;
import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.ErrorDialog;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.common.tasks.ITask;
import com.fhc25.percepcion.osiris.mapviewer.common.tasks.LinkedTasks;
import com.fhc25.percepcion.osiris.mapviewer.common.tasks.Task;
import com.fhc25.percepcion.osiris.mapviewer.common.tools.PolygonCreator;
import com.fhc25.percepcion.osiris.mapviewer.manager.ApplicationManager;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalStateManager;

import java.io.File;

public class MapActivityStartupManager {

    public abstract class StartupManagerTask<ReturnData> extends Task<ReturnData> {

        @Override
        public void onTaskError(Failure error) {
            super.onTaskError(error);
            mapsforgeFragmentProvider.getMapsforgeFragment().getIndoorMapView().dismissProgressDialog();
            ErrorDialog.showErrorDialog(activity, error);
            Lgr.e(TAG, "Error received:" + error.getMessage());
        }
    }

    private static final String TAG = MapActivityStartupManager.class.getName();

    private ApplicationManager applicationManager;
    private Activity activity;
    private IInternalStateManager internalStateManager;

    private IMapsforgeFragmentProvider mapsforgeFragmentProvider;

    private MetaData metaData;

    public MapActivityStartupManager(Activity activity, ApplicationManager applicationManager,
                                     IMapsforgeFragmentProvider mapsforgeFragmentProvider,
                                     IInternalStateManager internalStateManager) {
        this.activity = activity;
        this.applicationManager = applicationManager;
        this.mapsforgeFragmentProvider = mapsforgeFragmentProvider;
        this.internalStateManager = internalStateManager;
    }


    private Task<MetaData> metadataTask = new StartupManagerTask<MetaData>() {

        @Override
        public void onMainTask(ICallback<MetaData> callback) {
            applicationManager.getMetadataRepository().getMetadata(callback);
        }

        @Override
        public void onResultTask(MetaData metaData) {
            MapActivityStartupManager.this.metaData = metaData;
        }
    };

    private Task<File> mapFileTask = new StartupManagerTask<File>() {

        @Override
        protected void onMainTask(ICallback<File> callback) {
            mapsforgeFragmentProvider.getMapsforgeFragment().
                    getIndoorMapView().showProgressDialog(R.string.indoor_loading_mapfile);
            applicationManager.getMapsforgeMapManager().importMap(getMapFileName(), callback);
        }
    };

    private Task<Void> mapLoadTask = new StartupManagerTask<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {
            mapsforgeFragmentProvider.getMapsforgeFragment().setMapFileName(getMapFileName());
            notifyFinishedTask(null);
        }
    };

    private Task<BuildingGroup> indoorTask = new StartupManagerTask<BuildingGroup>() {

        @Override
        protected void onMainTask(ICallback<BuildingGroup> callback) {
            mapsforgeFragmentProvider.getMapsforgeFragment().getIndoorMapView().
                    setProgressDialogTitle(R.string.indoor_loading_buildings);
            applicationManager.getBuildingsManager().getBuildings(PolygonCreator.createPolygon(metaData), callback);
        }
    };

    private Task<Void> updateFragmentsTask = new Task<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {
            mapsforgeFragmentProvider.getMapsforgeFragment()
                    .initFromViewState(internalStateManager.getViewState());
            notifyFinishedTask(null);
        }
    };

    public Task<Void> centerMapTask = new Task<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {
            mapsforgeFragmentProvider.getMapsforgeFragment()
                    .getIndoorMapView().getMapsforgeMapView().setMapPosition(metaData.getCenterLat(), metaData.getCenterLong());
            notifyFinishedTask(null);
        }
    };

    private Task<Void> dismissProgressDialog = new Task<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {
            mapsforgeFragmentProvider.getMapsforgeFragment()
                    .getIndoorMapView().dismissProgressDialog();
            notifyFinishedTask(null);
        }
    };

    private Task<Void> saveMetaData = new Task<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {
            internalStateManager.getViewState().setMetadata(metaData);
            notifyFinishedTask(null);
        }
    };

    private Task<Void> loadPersistedInternalState = new Task<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {

            internalStateManager.loadInternalState();
            notifyFinishedTask(null);
        }
    };

    private Task<Void> persistInternalState = new Task<Void>() {
        @Override
        protected void onMainTask(ICallback<Void> callback) {

            Lgr.i(TAG, "Starting persisting state...");
            internalStateManager.persistInternalStatePersistent();
            internalStateManager.persistInternalStateVariable();
            Lgr.i(TAG, "State persisted OK");

            notifyFinishedTask(null);
        }
    };


    public void startupFromScratch() {

        LinkedTasks tasks = new LinkedTasks();

        tasks.then(metadataTask)
                .then(mapFileTask)
                .then(mapLoadTask)
                .then(indoorTask)
                .then(dismissProgressDialog)
                .then(updateFragmentsTask)
                .then(centerMapTask)
                .then(saveMetaData)
                .then(persistInternalState);

        tasks.runTask();
    }


    public void startupFromState(Bundle bundle) {
        // The state is loaded in the fragments themselves
    }

    public void startupFromFinishedSecondaryActivity() {
        // updateFragmentsTask.runTask();
    }

    public void startupFromRestart() {

        LinkedTasks tasks = new LinkedTasks();

        tasks.then(metadataTask)
            .then(new Task() {

                @Override
                protected void onMainTask(ICallback callback) {

                    if (!internalStateManager.persistedDataExists().equals(internalStateManager.getViewState().getMetadata())) {
                        internalStateManager.loadInternalState();
                    }

                    if (internalStateManager.getViewState().getMetadata().equals(metaData)) {

                        LinkedTasks tasks = new LinkedTasks();
                        tasks.then(updateFragmentsTask)
                                .then(mapLoadTask)
                                .then(centerMapTask);
                        tasks.runTasks();
                    } else {
                        createReloadTask().runTask();
                    }
                }
            });

        tasks.runTasks();
    }

    public void startupFromRecreatedMapFragment() {

        mapsforgeFragmentProvider.getMapsforgeFragment().setMapFileName(getMapFileName());
        mapsforgeFragmentProvider.getMapsforgeFragment()
                .getIndoorMapView().getMapsforgeMapView().setMapPosition(metaData.getCenterLat(), metaData.getCenterLong());
        mapsforgeFragmentProvider.getMapsforgeFragment()
                .initFromViewState(internalStateManager.getViewState());
    }

    private ITask createReloadTask() {

        LinkedTasks tasks = new LinkedTasks();

        tasks.then(mapFileTask)
                .then(mapLoadTask)
                .then(indoorTask)
                .then(dismissProgressDialog)
                .then(updateFragmentsTask)
                .then(centerMapTask)
                .then(saveMetaData)
                .then(persistInternalState);

        return tasks;
    }

    private String getMapFileName() {
        return activity.getResources().getString(R.string.default_map_file);
    }
}
