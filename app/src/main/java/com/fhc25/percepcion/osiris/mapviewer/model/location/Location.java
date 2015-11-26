package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Represents a position in the geographic space including indoor information
 * such as floor and the building
 */
public class Location implements Serializable {

    private Position position;
    private Integer floor;
    private String building;

    public static Comparator<Location> COMPARATOR = new Comparator<Location>() {
        @Override
        public int compare(Location l1, Location l2) {
            int posComp = Position.COMPARATOR.compare(l1.getPosition(), l2.getPosition());
            int floorComp = l1.getFloor().compareTo(l2.getFloor());

            if (posComp != 0) {
                return posComp;
            } else if (floorComp != 0) {
                return floorComp;
            }
            return 0;
        }
    };

    public Location() {
    }

    /**
     * Constructor from basic position and floor
     *
     * @param lat
     * @param lon
     * @param floor
     */
    public Location(double lat, double lon, int floor) {
        this.position = new Position(lat, lon);
        this.floor = floor;
    }

    /**
     * Gets the position of the location
     *
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the location
     *
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the floor of the location
     *
     * @return floor
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * Sets the floor of the location
     *
     * @param floor
     */
    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    /**
     * Gets the building name of the location
     *
     * @return building name
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets the building name of the location
     *
     * @param building
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof Location) {
            Location location = (Location) object;

            boolean equal = position.equals(location.position);

            // We let the floor be null
            if (floor != null) {
                equal = equal && floor.equals(location.floor);
            } else {
                equal = equal && (location.floor == null);
            }

            // We let the building be null
            if (building != null) {
                equal = equal && building.equals(location.building);
            } else {
                equal = equal && (location.building == null);
            }

            return equal;

        }

        return false;
    }

    @Override
    public String toString() {
        String out = Location.class.getName();

        out += " position: " + this.position.toString();
        out += " floor: " + this.floor;
        out += " building: " + this.building;

        return out;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = hash * 89 + (position != null ? position.hashCode() : 0);
        hash = hash * 89 + (floor != null ? floor.hashCode() : 0);
        hash = hash * 89 + (building != null ? building.hashCode() : 0);

        return hash;
    }
}
