package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Corridor;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Door;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Elevator;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Office;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Room;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Stairway;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.DrawableMarker;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.IVisualsBuilder;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.LocalizedVisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

/**
 * Mapsforge specific implementation if the {@link IVisualsBuilder} interface.
 * This class is able to construct VisualElements from model objects
 */
public class MapsforgeVisualsBuilder implements IVisualsBuilder {

    private VisualTheme theme;

    public MapsforgeVisualsBuilder(VisualTheme theme) {
        this.theme = theme;
    }

    @Override
    public VisualElement build(BuildingElement buildingElement) {

        VisualElement visualElement = null;

        if (buildingElement instanceof Door) {
            visualElement = new MapsforgeDoor(theme, (Door) buildingElement);
        } else if (buildingElement instanceof Elevator) {
            visualElement = new MapsforgeElevator(theme, (Elevator) buildingElement);
        } else if (buildingElement instanceof Office) {
            visualElement = new MapsforgeOffice(theme, (Office) buildingElement);
        } else if (buildingElement instanceof Room) {
            visualElement = new MapsforgeRoom(theme, (Room) buildingElement);
        } else if (buildingElement instanceof Shell) {
            visualElement = new MapsforgeShell(theme, (Shell) buildingElement);
        } else if (buildingElement instanceof Stairway) {
            visualElement = new MapsforgeStairway(theme, (Stairway) buildingElement);
        } else if (buildingElement instanceof Corridor) {
            visualElement = new MapsforgeCorridor(theme, (Corridor) buildingElement);
        }

        return visualElement;
    }

    @Override
    public LocalizedVisualElement build(DrawableMarker marker) {
        return new MapsforgeVisualMarker(marker);
    }

    @Override
    public VisualElement buildTinyBuilding(Shell shell) {
        return new MapsforgeTinyBuilding(theme, shell);
    }

}
