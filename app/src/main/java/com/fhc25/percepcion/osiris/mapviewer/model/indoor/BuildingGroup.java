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

import com.fhc25.percepcion.osiris.mapviewer.manager.indoor.IIsIndoorProvider;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Model class representing a group of Buildings
 */
public class BuildingGroup implements Serializable, IIsIndoorProvider {

	private Map<String, Building> buildings = new Hashtable<String, Building>();

	/**
	 * Gets a dictionary associating an building's name with the Building model
	 * 
	 * @return dictionary of building names and Building models
	 */
	public Map<String, Building> getBuildings() {
		return buildings;
	}

	/**
	 * Gets all the building names that conforms the BuildingGroup
	 * 
	 * @return collection of names
	 */
	public Collection<String> getBuildingNames() {
		return buildings.keySet();
	}

	/**
	 * Gets a specific building model given its name
	 * 
	 * @param buildingName
	 * @return building model
	 */
	public Building getBuilding(String buildingName) {
		return buildings.get(buildingName);
	}

	/**
	 * Gets all the Building models included in this BuildingGroup
	 * 
	 * @return collection of building models
	 */
	public Collection<Building> getAllBuildings() {
		return buildings.values();
	}

	/**
	 * Gets all the BuildingElements included in all the Buildings in this
	 * BuildingGroup
	 * 
	 * @return collection of BuildingElements
	 */
	public Collection<BuildingElement> getAllBuildingElements() {
		List<BuildingElement> list = new ArrayList<BuildingElement>();
		for (Building building : buildings.values()) {
			list.addAll(building.getBuildingElements());
		}
		return list;
	}

	/**
	 * Adds a BuildingElement to the BuildingGroup structure, adding it to the
	 * corresponding Building model
	 * 
	 * @param building_name
	 * @param element
	 */
	public void put(String building_name, BuildingElement element) {
		if (!buildings.containsKey(building_name)) {
			Building building = new Building();
			building.setName(building_name);
			buildings.put(building_name, building);
		}
		Building building = buildings.get(building_name);
		building.add(element);
	}

	/**
	 * Finds the building model where a certain location lays inside
	 * 
	 * @param location
	 * @return building model or null
	 */
	public Building findBuilding(Location location) {
		return findBuilding(location.getPosition().getLatitude(), location
				.getPosition().getLongitude(), location.getFloor().toString());
	}

	/**
	 * Finds the building model where a certain location lays inside
	 * 
	 * @param lat
	 * @param lon
	 * @param floor
	 * @return building model or null
	 */
	public Building findBuilding(double lat, double lon, String floor) {

		for (Map.Entry<String, Building> entry : buildings.entrySet()) {
			Building b = entry.getValue();
			BuildingElement e = b.isPointInside(lat, lon, floor);
			if (e != null) {
                return b;
            }
		}
		return null;
	}

	/**
	 * Checks if a certain geographic point lays inside the building group, specifying
	 * in which building element is inside
	 * 
	 * @param location
	 * @return building element or null
	 */
    @Override
	public BuildingElement findBuildingElement(Location location) {

        if (location.getFloor() == null) {
            return null;
        }

		return findBuildingElement(location.getPosition().getLatitude(),
				location.getPosition().getLongitude(), location.getFloor()
						.toString());
	}

	/**
	 * Checks if a certain geographic point lays inside the building, specifying
	 * in which building element is inside
	 * 
	 * @param lat
	 * @param lon
	 * @param floor
	 * @return building element or null
	 */
	public BuildingElement findBuildingElement(double lat, double lon, String floor) {

		for (Map.Entry<String, Building> entry : buildings.entrySet()) {
			Building b = entry.getValue();
			BuildingElement e = b.isPointInside(lat, lon, floor);
			if (e != null) {
                return e;
            }
		}
		return null;
	}

	@Override
	public boolean isIndoor(Location location) {

		for (Building building : getAllBuildings()) {
			if (building.isPointInside(location) != null) {
				return true;
			}
		}

		return false;
	}
}
