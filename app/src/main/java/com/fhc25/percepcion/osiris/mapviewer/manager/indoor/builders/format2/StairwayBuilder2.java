package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Stairway;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

import java.util.Locale;

public class StairwayBuilder2 implements BuildingElementBuilder<Stairway> {

    @Override
    public Stairway build(Feature feature) {

        Stairway stairway = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").equals("way") &&
                feature.getProperties().containsKey("indoor") &&
                feature.getProperties().get("indoor").equals("corridor") &&
                feature.getProperties().containsKey("stairs") &&
                feature.getProperties().get("stairs").equals("yes")) {

            stairway = createStairway(feature);
        }

        return stairway;
    }

    private Stairway createStairway(Feature feature) {
        LineString line = (LineString) feature.getGeometry();
        Long id = Long.parseLong(feature.getProperties().get("@id"));

        String level = feature.getProperties().get("level");

        String name = "";
        String locale = Locale.getDefault().getLanguage();

        if (feature.getProperties().containsKey("name:" + locale)) {
            name = feature.getProperties().get("name:" + locale);
        } else if (feature.getProperties().containsKey("name")) {
            name = feature.getProperties().get("name");
        }

        try {
            Integer.parseInt(level);
        } catch (NumberFormatException e) {
            return null;
        }

        return new Stairway(id, name, line, level);
    }
}
