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
