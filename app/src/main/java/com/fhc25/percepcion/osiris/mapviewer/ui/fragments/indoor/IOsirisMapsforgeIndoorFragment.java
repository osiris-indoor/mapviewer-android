package com.fhc25.percepcion.osiris.mapviewer.ui.fragments.indoor;

import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalStateManager;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;
import com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.IMapsforgeIndoorMapView;

public interface IOsirisMapsforgeIndoorFragment {

    void initFromViewState(final IInternalViewState internalViewState);

    IMapsforgeIndoorMapView getIndoorMapView();

    void setInternalStateManager(IInternalStateManager internalStateManager);

    void setMapFileName(String fileName);
}
