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

package com.fhc25.percepcion.osiris.mapviewer.model.states;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalViewState;

public class InternalViewState implements IInternalViewState {

    private IInternalState internalState;

    public InternalViewState(IInternalState internalState) {
        this.internalState = internalState;
    }


    @Override
    public MetaData getMetadata() {
        return internalState.getMetadata();
    }

    @Override
    public void setMetadata(MetaData metaData) {
        internalState.setMetadata(metaData);
    }

    @Override
    public BuildingGroup getBuildingGroup() {
        return internalState.getBuildingGroup();
    }
}
