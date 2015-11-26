package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Corridor;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

import java.util.Locale;

public class CorridorBuilder implements BuildingElementBuilder<Corridor> {

    @Override
    public Corridor build(Feature feature) {

        Corridor corridor = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("way") &&
                feature.getProperties().containsKey("@role") &&
                feature.getProperties().get("@role").matches("buildingpart") &&
                feature.getProperties().containsKey("buildingpart") &&
                feature.getProperties().get("buildingpart").matches("corridor")) {

            corridor = createCorridor(feature);
        }

        return corridor;
    }

    private Corridor createCorridor(Feature feature) {
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

        String name = "";
        String locale = Locale.getDefault().getLanguage();

        if (feature.getProperties().containsKey("name:" + locale)) {
            name = feature.getProperties().get("name:" + locale);
        } else if (feature.getProperties().containsKey("name")) {
            name = feature.getProperties().get("name");
        }

        return new Corridor(id, name, line, level);
    }


}
