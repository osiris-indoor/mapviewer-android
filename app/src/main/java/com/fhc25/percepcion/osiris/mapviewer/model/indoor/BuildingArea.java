
package com.fhc25.percepcion.osiris.mapviewer.model.indoor;

import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;

import java.io.Serializable;

/**
 * Model class that represents a BuildingElement which has an area geometry
 * associated with it. It is used to model building parts that are related to a
 * hole area such as Rooms
 */
public abstract class BuildingArea extends BuildingElement implements Serializable {

	private LineString geometry;

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param geometry
	 * @param level
	 */
	public BuildingArea(Long id, String name, LineString geometry, String level) {
		super(id, name, level);
		this.geometry = geometry;
	}

	/**
	 * Gets the geometry associated with the BuildingArea
	 * 
	 * @return LineString representing an area
	 */
	public LineString getGeometry() {
		return geometry;
	}

}
