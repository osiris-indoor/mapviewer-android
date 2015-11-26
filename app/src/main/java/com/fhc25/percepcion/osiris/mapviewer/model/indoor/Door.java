
package com.fhc25.percepcion.osiris.mapviewer.model.indoor;


import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

import java.io.Serializable;

/**
 * Model class representing a door inside a building
 */
public class Door extends BuildingPoint implements Serializable {

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param position
	 * @param level
	 */
	public Door(Long id, String name, Point position, String level) {
		super(id, name, position, level);
	}

}
