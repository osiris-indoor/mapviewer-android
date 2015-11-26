
package com.fhc25.percepcion.osiris.mapviewer.model.indoor;

import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

import java.io.Serializable;

/**
 * Model class representing an office inside a building
 */
public class Office extends BuildingArea implements Serializable {

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param geometry
	 * @param level
	 */
	public Office(Long id, String name, LineString geometry, String level) {
		super(id, name, geometry, level);
	}

}
