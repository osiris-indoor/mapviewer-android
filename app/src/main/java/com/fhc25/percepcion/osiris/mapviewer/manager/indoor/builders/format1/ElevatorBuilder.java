package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Elevator;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

public class ElevatorBuilder implements BuildingElementBuilder<Elevator> {

    @Override
    public Elevator build(Feature feature) {

        Elevator elevator = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("way") &&
                feature.getProperties().containsKey("@role") &&
                feature.getProperties().get("@role").matches("buildingpart") &&
                feature.getProperties().containsKey("buildingpart") &&
                feature.getProperties().get("buildingpart").matches("verticalpassage") &&
                feature.getProperties().get("buildingpart:verticalpassage").matches("elevator")) {
            elevator = createElevator(feature);
        }

        return elevator;
    }

    private Elevator createElevator(Feature feature) {
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

        return new Elevator(id, "", line, level);
    }
}
