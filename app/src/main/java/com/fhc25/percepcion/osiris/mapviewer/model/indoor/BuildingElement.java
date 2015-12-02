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
