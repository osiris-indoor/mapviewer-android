package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

public class ShellBuilder2 implements BuildingElementBuilder<Shell> {

    @Override
    public Shell build(Feature feature) {

        Shell shell = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("way") &&
                feature.getProperties().containsKey("indoor") &&
                feature.getProperties().get("indoor").matches("level")) {

            shell = createShell(feature);
        }

        return shell;
    }

    private Shell createShell(Feature feature) {

        LineString line = (LineString) feature.getGeometry();
        Long id = Long.parseLong(feature.getProperties().get("@id"));

        String level = feature.getProperties().get("level");

        try {
            Integer.parseInt(level);
        } catch (NumberFormatException e) {
            return null;
        }

        return new Shell(id, "", line, level);
    }
}
