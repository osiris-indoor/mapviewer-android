package com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.format2;

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.builders.BuildingElementBuilder;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Room;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

import java.util.Locale;

public class RoomBuilder2 implements BuildingElementBuilder<Room> {

    @Override
    public Room build(Feature feature) {

        Room room = null;

        if (feature.getProperties().containsKey("@type") &&
                feature.getProperties().get("@type").matches("way") &&
                feature.getProperties().containsKey("indoor") &&
                feature.getProperties().get("indoor").matches("room")) {

            room = createRoom(feature);
        }

        return room;
    }

    private Room createRoom(Feature feature) {
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

        return new Room(id, name, line, level);
    }
}
