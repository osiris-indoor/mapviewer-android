package com.fhc25.percepcion.osiris.mapviewer.ui.fragments.indoor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fhc25.percepcion.osiris.mapviewer.R;
import com.fhc25.percepcion.osiris.mapviewer.manager.ApplicationManager;
import com.fhc25.percepcion.osiris.mapviewer.manager.IApplicationManagerProvider;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalStateManager;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.IMapsforgeIndoorMapView;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.MapsforgeIndoorMapViewNoImpairment;

import org.mapsforge.map.android.graphics.AndroidGraphicFactory;

public class OsirisMapsforgeIndoorFragment extends Fragment implements IOsirisMapsforgeIndoorFragment {

    private static final String TAG = OsirisMapsforgeIndoorFragment.class.getName();

    private MapsforgeIndoorMapViewNoImpairment mapsforgeIndoorMapView;
    private IInternalStateManager internalStateManager;

    private ApplicationManager applicationManager;

    private String mapFileName = "defaultMap.map";

    private ProgressBar progressBar;

    @Override
    public void setInternalStateManager(IInternalStateManager internalStateManager) {
        this.internalStateManager = internalStateManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(this.getActivity()
                .getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        IApplicationManagerProvider applicationManagerProvider = (IApplicationManagerProvider) getActivity().getApplication();
        applicationManager = applicationManagerProvider.getApplicationManager();

        internalStateManager = (IInternalStateManager) getActivity().getApplication();

        View view = inflater.inflate(R.layout.mapsforge_indoor_map_fragment, container, false);

        mapsforgeIndoorMapView = (MapsforgeIndoorMapViewNoImpairment) view.findViewById(R.id.indoor_map_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        if (savedInstanceState != null) {
            initFromViewState(internalStateManager.getViewState());
            mapsforgeIndoorMapView.loadFromBundle(savedInstanceState);
            mapFileName = savedInstanceState.getString("mapFileName");
        }

        return view;
    }

    @Override
    public void setMapFileName(String mapFileName) {
        this.mapFileName = mapFileName;
        mapsforgeIndoorMapView.setMap(mapFileName);
        mapsforgeIndoorMapView.updateIndoorOverlay();
    }

    @Override
    public void onResume() {
        super.onResume();

        mapsforgeIndoorMapView.setMap(mapFileName);
        mapsforgeIndoorMapView.updateIndoorOverlay();
    }

    @Override
    public void onPause() {
        super.onPause();

        mapsforgeIndoorMapView.pauseMapView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapsforgeIndoorMapView.destroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mapsforgeIndoorMapView.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("mapFileName", mapFileName);
    }

    @Override
    public void initFromViewState(final IInternalViewState internalViewState) {
        mapsforgeIndoorMapView.initFromViewState(internalViewState);
    }

    @Override
    public IMapsforgeIndoorMapView getIndoorMapView() {
        return mapsforgeIndoorMapView;
    }

    private void showProgressBar(final boolean show) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (show) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
