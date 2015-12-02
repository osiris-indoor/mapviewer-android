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

package com.fhc25.percepcion.osiris.mapviewer.manager.states;

import android.os.Bundle;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;

public class BundleInternalStateManager {

    public BundleInternalStateManager() {}

    public void saveInternalState(Bundle bundle, IInternalState internalState) {

        bundle.putSerializable("internalState.BuildingGroup",
                internalState.getBuildingGroup());
        bundle.putSerializable("internalState.Metadata",
                internalState.getMetadata());
    }

    public void loadInternalState(Bundle bundle, IInternalState internalState) {

        if (bundle.containsKey("internalState.BuildingGroup")) {
            internalState.setBuildingGroup((BuildingGroup) bundle
                    .getSerializable("internalState.BuildingGroup"));
        } else {
            Lgr.e(this.getClass().getName(), "BuildingGroup element not found in bundle");
        }

        if (bundle.containsKey("internalState.Metadata")) {
            internalState.setMetadata((MetaData) bundle.getSerializable("internalState.Metadata"));
        } else {
            Lgr.e(this.getClass().getName(), "Metadata element not found in bundle");
        }

    }
}
