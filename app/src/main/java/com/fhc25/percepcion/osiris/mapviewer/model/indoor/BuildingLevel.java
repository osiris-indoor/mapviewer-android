/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

package com.fhc25.percepcion.osiris.mapviewer.model.indoor;

import com.sromku.polygon.Point;
import com.sromku.polygon.Polygon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Model class representing a group of BuildingElements that has in common the
 * same floor (or level)
 */
public class BuildingLevel implements Serializable {

    private List<BuildingElement> elements = new ArrayList<BuildingElement>();
    private String name = "";

    /**
     * Default constructor
     */
    public BuildingLevel() {
    }

    /**
     * Gets the name of the BuildingLevel
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a BuildingElement to this BuildingLevel
     *
     * @param element
     */
    public void add(BuildingElement element) {
        elements.add(element);
        name = element.getLevel();
    }

    /**
     * Gets all the BuildingElements included in this BuildingLevel
     *
     * @return collection of building elements
     */
    public Collection<BuildingElement> getBuildingElements() {
        return elements;
    }

    /**
     * Gets exclusively the Room BuildingElements in this BuildingLevel
     *
     * @return collection of Rooms
     */
    public Collection<Room> getRooms() {
        List<Room> lrooms = new ArrayList<Room>();
        for (BuildingElement element : elements) {
            if (element instanceof Room) {
                lrooms.add((Room) element);
            }
        }
        return lrooms;
    }

    /**
     * Gets exclusively the Door BuildingElements in this BuildingLevel
     *
     * @return collection of Doors
     */
    public Collection<Door> getDoors() {
        List<Door> ldoors = new ArrayList<Door>();
        for (BuildingElement element : elements) {
            if (element instanceof Door) {
                ldoors.add((Door) element);
            }
        }
        return ldoors;
    }

    /**
     * Gets exclusively the Office BuildingElements in this BuildingLevel
     *
     * @return collection of Offices
     */
    public Collection<Office> getOffices() {
        List<Office> loffices = new ArrayList<Office>();
        for (BuildingElement element : elements) {
            if (element instanceof Office) {
                loffices.add((Office) element);
            }
        }
        return loffices;
    }

    /**
     * Gets exclusively the Corridor BuildingElements in this BuildingLevel
     *
     * @return collection of Corridors
     */
    public Collection<Corridor> getCorridors() {
        List<Corridor> lcorridors = new ArrayList<Corridor>();
        for (BuildingElement element : elements) {
            if (element instanceof Corridor) {
                lcorridors.add((Corridor) element);
            }
        }
        return lcorridors;
    }

    /**
     * Gets exclusively the Elevator BuildingElements in this BuildingLevel
     *
     * @return collection of Elevators
     */
    public Collection<Elevator> getElevators() {
        List<Elevator> lverticalPassages = new ArrayList<Elevator>();
        for (BuildingElement element : elements) {
            if (element instanceof Elevator) {
                lverticalPassages.add((Elevator) element);
            }
        }
        return lverticalPassages;
    }

    /**
     * Gets exclusively the Stairway BuildingElements in this BuildingLevel
     *
     * @return collection of Stairways
     */
    public Collection<Stairway> getStairways() {
        List<Stairway> lverticalPassages = new ArrayList<Stairway>();
        for (BuildingElement element : elements) {
            if (element instanceof Stairway) {
                lverticalPassages.add((Stairway) element);
            }
        }
        return lverticalPassages;
    }

    /**
     * Gets exclusively the Shell BuildingElements in this BuildingLevel
     *
     * @return collection of Shells
     */
    public Collection<Shell> getShells() {
        List<Shell> lshells = new ArrayList<Shell>();
        for (BuildingElement element : elements) {
            if (element instanceof Shell) {
                lshells.add((Shell) element);
            }
        }
        return lshells;
    }

    /**
     * Checks if a geographic position lays inside this BuildingLevel and in
     * which BuildingElement
     *
     * @param lat
     * @param lon
     * @return a BuildingElement or null
     */
    public BuildingArea isPointInside(double lat, double lon) {
        Point pin = new Point(lat, lon);

        for (BuildingElement element : elements) {
            if (element instanceof BuildingArea && ! (element instanceof Shell)) {
                BuildingArea area = (BuildingArea) element;

                Polygon.Builder builder = Polygon
                        .Builder();

                List<com.fhc25.percepcion.osiris.mapviewer.model.location.Point> cp = area
                        .getGeometry().getCollectionPoint();
                int size = cp.size();
                int count = 0;
                int vertexAdded = 0;
                for (com.fhc25.percepcion.osiris.mapviewer.model.location.Point point :
                        area.getGeometry().getCollectionPoint()) {
                    if (count < (size - 1)) {
                        builder.addVertex(new Point(point
                                .getLatitude(), point.getLongitude()));
                        vertexAdded++;
                    }
                    count++;
                }

                if (vertexAdded > 2) {
                    Polygon poly = builder.build();
                    if (poly.contains(pin)) {
                        return area;
                    }
                }
            }
        }
        return null;

    }

}
