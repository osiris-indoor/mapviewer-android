
package com.fhc25.percepcion.osiris.mapviewer.model.indoor;


import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Model class representing a Building. It is made of BuildingLevels and
 * BuildingElements.
 */
public class Building implements Serializable {

	private String name;

	private Map<String, ArrayList<Long>> namesIds = new Hashtable<String, ArrayList<Long>>();
	private Map<Long, BuildingElement> elements = new Hashtable<Long, BuildingElement>();

	private Map<String, BuildingLevel> levels = new Hashtable<String, BuildingLevel>();

	/**
	 * Default constructor
	 */
	public Building() {
	}

	/**
	 * Sets the name of the building
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the building
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds a building element to the building and to the related BuildingLevel
	 * 
	 * @param element
	 */
	public void add(BuildingElement element) {
		elements.put(element.getID(), element);
		addElementToByNameSearch(element);
		addElementToLevels(element);
	}

	private void addElementToByNameSearch(BuildingElement element) {
		if (!namesIds.containsKey(element.getName())) {
            namesIds.put(element.getName(), new ArrayList<Long>());
        }
		namesIds.get(element.getName()).add(element.getID());
	}

	private void addElementToLevels(BuildingElement element) {
		if (!levels.containsKey(element.getLevel())) {
            levels.put(element.getLevel(), new BuildingLevel());
        }
		levels.get(element.getLevel()).add(element);
	}

	/**
	 * Find BuildingElements by its name String
	 * 
	 * @param name
	 * @return collection of building elements
	 */
	public Collection<BuildingElement> findByName(String name) {
		List<BuildingElement> lQueryElements = new ArrayList<BuildingElement>();

		List<Long> lIds = namesIds.get(name);
		for (Long i : lIds) {
			lQueryElements.add(elements.get(i));
		}

		return lQueryElements;
	}

	/**
	 * Find a BuildingElement by its ID
	 * 
	 * @param id
	 * @return building element
	 */
	public BuildingElement findById(Long id) {
		return elements.get(id);
	}

	/**
	 * Gets all the building elements belonging to this Building
	 * 
	 * @return collection of building elements
	 */
	public Collection<BuildingElement> getBuildingElements() {
		return elements.values();
	}

	/**
	 * Gets the BuildingLevel related to a certain level
	 * 
	 * @param level
	 * @return BuildingLevel
	 */
	public BuildingLevel getLevel(String level) {
		return levels.get(level);
	}

	/**
	 * Gets all the levels that are present in this Building
	 * 
	 * @return collection of levels
	 */
	public Collection<String> getLevels() {
		return levels.keySet();
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
	public BuildingElement isPointInside(double lat, double lon, String floor) {

		if (floor == null) {
			return null;
		}

		BuildingLevel buildingLevel = levels.get(floor);
		BuildingArea area = null;
		if (buildingLevel != null) {
            area = buildingLevel.isPointInside(lat, lon);
        }
		return area;
	}

	/**
	 * Checks if a certain geographic point lays inside the building, specifying
	 * in which building element is inside
	 * 
	 * @param location
	 * @return building element or null
	 */
	public BuildingElement isPointInside(Location location) {

		if (location.getFloor() == null) {
			return null;
		}

		return isPointInside(location.getPosition().getLatitude(), location
				.getPosition().getLongitude(), location.getFloor().toString());
	}

}
