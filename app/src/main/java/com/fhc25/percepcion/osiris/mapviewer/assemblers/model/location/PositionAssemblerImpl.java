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
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PositionDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Position;

public class PositionAssemblerImpl extends
		SimpleAssembler<PositionDTO, Position> {

	@Override
	public Position createDomainObject(PositionDTO positionDTO) {

		Position position = new Position();
		position.setLatitude(positionDTO.getLatitude());
		position.setLongitude(positionDTO.getLongitude());

		return position;
	}

	@Override
	public PositionDTO createDataTransferObject(Position position) {

		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setLatitude(position.getLatitude());
		positionDTO.setLongitude(position.getLongitude());

		return positionDTO;
	}

}
