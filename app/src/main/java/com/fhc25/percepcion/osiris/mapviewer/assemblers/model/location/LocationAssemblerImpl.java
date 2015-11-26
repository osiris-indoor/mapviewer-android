
package com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location;

import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.SimpleAssembler;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.LocationDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;

public class LocationAssemblerImpl extends
		SimpleAssembler<LocationDTO, Location> {

	private PositionAssemblerImpl positionAssembler = new PositionAssemblerImpl();

	@Override
	public Location createDomainObject(LocationDTO locationDTO) {

		Location location = new Location();
		location.setBuilding(locationDTO.getBuilding());
		location.setFloor(locationDTO.getFloor());
		location.setPosition(positionAssembler.createDomainObject(locationDTO
				.getPosition()));
		
		if (location.getFloor() == null) {
            location.setFloor(0);
        }
		
		if (location.getBuilding() == null) {
            location.setBuilding("");
        }

		return location;
	}

	@Override
	public LocationDTO createDataTransferObject(Location location) {

		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setBuilding(location.getBuilding());
		locationDTO.setFloor(location.getFloor());
		locationDTO.setPosition(positionAssembler
				.createDataTransferObject(location.getPosition()));

		return locationDTO;
	}

}
