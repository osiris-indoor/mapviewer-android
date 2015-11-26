package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Stairway;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

public class StairwayBuilder implements BuildingElementBuilder<Stairway> {

    @Override
    public Stairway build(Feature feature) {

        Stairway stairway = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("way") &&
                feature.getProperties().containsKey("@role") &&
                feature.getProperties().get("@role").matches("buildingpart") &&
                feature.getProperties().containsKey("buildingpart") &&
                feature.getProperties().get("buildingpart").matches("verticalpassage") &&
                (feature.getProperties().get("buildingpart:verticalpassage").matches("stairway")
                        || feature.getProperties().get("buildingpart:verticalpassage").matches
                        ("stairs"))) {

            stairway = createStairway(feature);
        }

        return stairway;
    }

    private Stairway createStairway(Feature feature) {
        LineString line = (LineString) feature.getGeometry();
        Long id = Long.parseLong(feature.getProperties().get("@id"));
        String level = "";
        for (int i = 0; i < feature.getPropertiesRelations().size(); i++) {
            if (feature.getPropertiesRelations().get(i).containsKey("level")) {
                level = feature.getPropertiesRelations().get(i).get("level");
            }
        }

        try {
            Integer.parseInt(level);
        } catch (NumberFormatException e) {
            return null;
        }

        return new Stairway(id, "", line, level);
    }
}
