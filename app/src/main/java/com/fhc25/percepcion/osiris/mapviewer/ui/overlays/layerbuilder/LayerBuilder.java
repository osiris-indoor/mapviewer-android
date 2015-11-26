package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layerbuilder;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Building;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingLevel;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.IVisualsBuilder;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layers.IndoorLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Specific implementation of the {@link ILayerBuilder} interface. Creates all
 * kind of layers necessary for the Indoor application
 */
public class LayerBuilder implements ILayerBuilder {

    private static final String TAG = LayerBuilder.class.getName();

    private IVisualsBuilder builder;

    /**
     * Main constructor
     *
     * @param builder
     */
    public LayerBuilder(IVisualsBuilder builder) {
        this.builder = builder;
    }

    @Override
    public IndoorLayer buildIndoor(Building building) {

        IndoorLayer layer = new IndoorLayer(building.getName());

        boolean shellAdded = false;

        for (String level : building.getLevels()) {

            Integer iLevel = 0;

            try {
                iLevel = Integer.parseInt(level);
            } catch (NumberFormatException e) {
                iLevel = 0;
                Lgr.e(TAG, "level with no integer parseable value: " + level);
                Lgr.e(TAG, e);
            }

            layer.addAll(iLevel, build(building.getLevel(level)));

            if (!shellAdded) {

                for (BuildingElement element : building.getLevel(level)
                        .getBuildingElements()) {
                    if (element instanceof Shell) {
                        layer.addToTinyZoom(builder
                                .buildTinyBuilding((Shell) element));
                        shellAdded = true;
                    }
                }
            }
        }

        return layer;
    }

    private Collection<VisualElement> build(BuildingLevel buildingLevel) {

        List<VisualElement> visualElements = new ArrayList<VisualElement>();

        for (BuildingElement buildingElement : buildingLevel
                .getBuildingElements()) {

            VisualElement visualElement = builder.build(buildingElement);
            visualElements.add(visualElement);
        }

        Collections.sort(visualElements, VisualElement.ZDEPTH_COMPARATOR);

        return visualElements;
    }

}
