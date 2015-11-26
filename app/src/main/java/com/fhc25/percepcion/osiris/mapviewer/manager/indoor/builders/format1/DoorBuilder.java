package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Door;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

public class DoorBuilder implements BuildingElementBuilder<Door> {

    @Override
    public Door build(Feature feature) {

        Door door = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("node") &&
                feature.getProperties().containsKey("door") &&
                feature.getProperties().get("door").matches("yes")) {
            door = createDoor(feature);
        }

        return door;
    }

    private Door createDoor(Feature feature) {
        Point point = (Point) feature.getGeometry();
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

        return new Door(id, "", point, level);
    }

}
