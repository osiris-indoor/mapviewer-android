package com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.fhc25.percepcion.osiris.mapviewer.R;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Building;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.ui.controllers.FloorSelectorViewController;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.OsirisOverlayManager;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge.MapsforgeOsirisOverlayManager;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level.FloorSelectorView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsforgeIndoorMapViewNoImpairment extends RelativeLayout implements IMapsforgeIndoorMapView {

    private static final String TAG = MapsforgeIndoorMapViewNoImpairment.class.getName();

    private MapsforgeMapView mapsforgeMapView;
    private FloorSelectorView floorSelectorView;
    private MapsforgeOsirisOverlayManager mapsforgeOsirisOverlayManager;

    private FloorSelectorViewController floorSelectorViewController;

    private ProgressDialog progressDialog;

    public MapsforgeIndoorMapViewNoImpairment(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mapsforgeView = inflater.inflate(R.layout.mapsforge_indoor_map, this, false);

        mapsforgeMapView = (MapsforgeMapView) mapsforgeView.findViewById(R.id.map_view);
        floorSelectorView = (FloorSelectorView) mapsforgeView.findViewById(R.id.floor_radio_group);

        mapsforgeOsirisOverlayManager = new MapsforgeOsirisOverlayManager(context.getResources(), mapsforgeMapView.getMapView(),
                new VisualTheme(context));

        floorSelectorViewController = new FloorSelectorViewController(floorSelectorView, mapsforgeOsirisOverlayManager);

        floorSelectorView.addObserver(floorSelectorViewController);
        mapsforgeMapView.addObserver(floorSelectorViewController);

        this.addView(mapsforgeView);
    }

    @Override
    public MapsforgeMapView getMapsforgeMapView() {
        return mapsforgeMapView;
    }

    @Override
    public FloorSelectorView getFloorSelectorView() {
        return floorSelectorView;
    }

    @Override
    public OsirisOverlayManager getOsirisOverlayManager() {
        return mapsforgeOsirisOverlayManager;
    }

    @Override
    public FloorSelectorViewController getFloorSelectorViewController() {
        return floorSelectorViewController;
    }

    private void initFromBuildings(BuildingGroup buildingGroup) {

        if (buildingGroup.getBuildings().size() == 1) {
            Building building = buildingGroup.getAllBuildings().iterator().next();

            final List<String> levels = new ArrayList<String>(building.getLevels());
            Collections.sort(levels, Collections.reverseOrder());

            floorSelectorView.post(new Runnable() {
                @Override
                public void run() {
                    floorSelectorView.load(levels);
                }
            });

        } else if (buildingGroup.getBuildings().size() == 2 && buildingGroup.getBuildings().containsKey("none")) {

            for (Building building : buildingGroup.getAllBuildings()) {

                if (!building.getName().equals("none")) {
                    final List<String> levels = new ArrayList<String>(building.getLevels());
                    Collections.sort(levels, Collections.reverseOrder());

                    floorSelectorView.post(new Runnable() {
                        @Override
                        public void run() {
                            floorSelectorView.load(levels);
                        }
                    });
                }
            }
        } else {
            Lgr.e(TAG, "Floor selector is not prepared for managing more than one building");
        }
    }

    @Override
    public void initFromViewState(IInternalViewState internalViewState) {
        mapsforgeOsirisOverlayManager.buildFromViewState(internalViewState);

        BuildingGroup buildingGroup = internalViewState.getBuildingGroup();
        initFromBuildings(buildingGroup);
    }

    @Override
    public void setMap(String mapFileName) {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), mapFileName);
        mapsforgeMapView.setMapFile(file);
    }

    @Override
    public void updateIndoorOverlay() {
        mapsforgeOsirisOverlayManager.deepUpdate();
    }

    @Override
    public void destroy() {

        mapsforgeMapView.destroy();
        mapsforgeOsirisOverlayManager.destroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mapsforgeMapView.saveState(savedInstanceState);
        mapsforgeOsirisOverlayManager.saveIntoBundle(savedInstanceState);
        floorSelectorView.saveStateToBundle(savedInstanceState);
    }

    @Override
    public void loadFromBundle(Bundle savedInstanceState) {
        mapsforgeMapView.loadState(savedInstanceState);
        mapsforgeOsirisOverlayManager.loadFromBundle(savedInstanceState);
        floorSelectorView.loadStateFromBundle(savedInstanceState);
    }

    @Override
    public void pauseMapView() {
        mapsforgeMapView.onPause();
    }

    @Override
    public void showProgressDialog(final int titleResource) {

        Lgr.e(TAG, "onProgressDialog show!!");

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        post(new Runnable() {

            @Override
            public void run() {
                progressDialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
                progressDialog.setTitle(titleResource);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }

    @Override
    public void setProgressDialogTitle(final int titleResource) {

        Lgr.e(TAG, "onProgressDialog set title!!");

        post(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.setTitle(titleResource);
                }
            }
        });
    }

    @Override
    public void dismissProgressDialog() {

        Lgr.e(TAG, "onProgressDialog dismiss!!");

        post(new Runnable() {

            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                progressDialog = null;
            }
        });
    }

}
