package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format1;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

public class ShellBuilder implements BuildingElementBuilder<Shell> {

    @Override
    public Shell build(Feature feature) {

        Shell shell = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("way") &&
                feature.getProperties().containsKey("@role") &&
                feature.getProperties().get("@role").matches("shell")) {

            shell = createShell(feature);
        }

        return shell;
    }

    private Shell createShell(Feature feature) {
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

        return new Shell(id, "", line, level);
    }
}
