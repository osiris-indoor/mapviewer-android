
package com.fhc25.percepcion.osiris.mapviewer.model.indoor;

import java.io.Serializable;

/**
 * Model class that represents a Building part
 */
public class BuildingElement implements Serializable {

	private Long id;
	private String name;
	private String level;

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param level
	 */
	public BuildingElement(Long id, String name, String level) {
		this.id = id;
		this.name = name;
		this.level = level;
	}

	/**
	 * Gets the BuildingElement's ID
	 * 
	 * @return ID
	 */
	public Long getID() {
		return id;
	}

	/**
	 * Gets the BuildingElement's name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the BuildingElement's level
	 * 
	 * @return level
	 */
	public String getLevel() {
		return level;
	}

}
