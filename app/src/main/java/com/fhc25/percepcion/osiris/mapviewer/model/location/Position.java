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
