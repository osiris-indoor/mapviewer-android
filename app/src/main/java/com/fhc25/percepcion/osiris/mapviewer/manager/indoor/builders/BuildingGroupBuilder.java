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

package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.CorridorBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.DoorBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.ElevatorBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.OfficeBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.RoomBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.ShellBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1.StairwayBuilder;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.CorridorBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.DoorBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.ElevatorBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.OfficeBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.RoomBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.ShellBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2.StairwayBuilder2;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;


/**
 * Parse {@link Feature} to {@link BuildingElement} objects. This task is part
 * of the process of transforming OSM features received from the backend into
 * actual building structure model
 */
public class BuildingGroupBuilder implements IBuildingGroupBuilder {

    private static final String TAG = BuildingGroupBuilder.class.getName();

    private Collection<BuildingElementBuilder> mBuilders = new
            LinkedList<BuildingElementBuilder>();

    /**
     * Main constructor
     */
    public BuildingGroupBuilder() {
        initBuilders();
    }

    private void initBuilders() {

        mBuilders.add(new DoorBuilder());
        mBuilders.add(new CorridorBuilder());
        mBuilders.add(new ElevatorBuilder());
        mBuilders.add(new OfficeBuilder());
        mBuilders.add(new RoomBuilder());
        mBuilders.add(new ShellBuilder());
        mBuilders.add(new StairwayBuilder());

        mBuilders.add(new DoorBuilder2());
        mBuilders.add(new CorridorBuilder2());
        mBuilders.add(new ElevatorBuilder2());
        mBuilders.add(new OfficeBuilder2());
        mBuilders.add(new RoomBuilder2());
        mBuilders.add(new ShellBuilder2());
        mBuilders.add(new StairwayBuilder2());
    }

    /**
     * Overwrites the default Builders
     *
     * @param builders
     */
    public void setElementBuilders(Collection<BuildingElementBuilder> builders) {
        mBuilders = builders;
    }

    /**
     * Parse function
     *
     * @param feature
     * @return building element
     */
    @Override
    public BuildingElement buildBuildingElement(Feature feature) {
        BuildingElement element = null;

        for (BuildingElementBuilder eb : mBuilders) {
            element = eb.build(feature);

            if (element != null) {
                Lgr.i(TAG, element.toString());
                return element;
            }
        }

        return element;
    }

    @Override
    public BuildingGroup buildBuildingGroup(Collection<Feature> features) {
        BuildingGroup buildingGroup = new BuildingGroup();

        for (Feature feature : features) {
            processFeature(feature, buildingGroup);
        }
        Lgr.e(TAG, "Number of building elements loaded: " + buildingGroup.getAllBuildingElements()
                .size());
        return buildingGroup;
    }

    /**
     * Transforms a FeatureDTO object into a representable OverlayElement and
     * adds it to the layerManager
     *
     * @param feature
     */
    private void processFeature(Feature feature, BuildingGroup buildingGroup) {
        BuildingGroupBuilder buildingParser = new BuildingGroupBuilder();
        BuildingElement element = buildingParser.buildBuildingElement(feature);
        String buildingName = "none";

        for (Map<String, String> entry : feature.getPropertiesRelations()) {
            if (entry.containsKey("@type") && entry.containsKey("building")) {
                if (entry.get("@type").matches("relation")
                        && entry.get("building").matches("yes")
                        && entry.containsKey("name")) {
                    buildingName = entry.get("name");
                }
            }
        }

        if (element != null && !buildingName.matches("")) {
            buildingGroup.put(buildingName, element);
        }
    }

}
