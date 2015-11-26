
package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Represents an simple geographic position, with just a latitude and a
 * longitude. No indoor information is included.
 */
public class Position implements Serializable {

    public static Comparator<Position> COMPARATOR = new Comparator<Position>() {
        @Override
        public int compare(Position p1, Position p2) {
            if (p1.getLatitude() != p2.getLatitude()) {
                return Double.compare(p1.getLatitude(), p2.getLatitude());
            } else if (p1.getLongitude() != p2.getLongitude()) {
                return Double.compare(p1.getLongitude(), p2.getLongitude());
            }
            return 0;
        }
    };

	private Double longitude;
	private Double latitude;

	public Position() {
	}

	public Position(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Position) {
			Position position = (Position) object;
			return longitude.equals(position.longitude)
					&& latitude.equals(position.latitude);
		}

		return false;
	}

	@Override
	public String toString() {

		String out = "Position";

		out += " longitude: " + longitude;
		out += " latitude: " + latitude;

		return out;
	}

    @Override
    public int hashCode() {
        int hash = 5;

        hash = hash * 89 + (latitude != null ? latitude.hashCode() : 0);
        hash = hash * 89 + (longitude != null ? longitude.hashCode() : 0);

        return hash;
    }


}
