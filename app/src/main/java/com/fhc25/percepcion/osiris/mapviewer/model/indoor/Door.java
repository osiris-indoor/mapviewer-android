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
