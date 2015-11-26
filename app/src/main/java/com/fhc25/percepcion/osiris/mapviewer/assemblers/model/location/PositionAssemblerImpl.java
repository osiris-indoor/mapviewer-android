
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
