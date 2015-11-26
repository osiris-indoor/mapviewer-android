
package com.fhc25.percepcion.osiris.mapviewer.dto.location;

public class LocationDTO {

	private PositionDTO position;
	private Integer floor;
	private String building;

	public PositionDTO getPosition() {
		return position;
	}

	public void setPosition(PositionDTO position) {
		this.position = position;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

}
