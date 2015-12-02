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

package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.IIsIndoorProvider;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.LocalizedVisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class for outdoor/indoor layer applications where the belonging to a
 * building has to be tested
 */
public abstract class BuildingBasedMixedOverlay extends MixedOverlay {

    private IIsIndoorProvider isIndoorProvider;

    public BuildingBasedMixedOverlay(String identificationName, IIsIndoorProvider isIndoorProvider) {
        super(identificationName);
        this.isIndoorProvider = isIndoorProvider;
    }

    /**
     * Adds a group of VisualElements correspondingly depending whether they are
     * inside of the building (indoor element) or not (outdoor element)
     *
     * @param visualElements
     */
    protected void addAll(Collection<LocalizedVisualElement> visualElements) {

        // Separate the addition in different Collections in order to optimize
        // the refreshing process
        for (LocalizedVisualElement localizedVisualElement : visualElements) {
            List<VisualElement> list = new ArrayList<VisualElement>();
            list.add(localizedVisualElement);

            if (isIndoorProvider.isIndoor(localizedVisualElement.getLocation())) {
                addAllToLevelOverlay(list, localizedVisualElement.getLocation()
                        .getFloor());
            } else {
                addAllToSimpleOverlay(list);
            }
        }
    }

}
