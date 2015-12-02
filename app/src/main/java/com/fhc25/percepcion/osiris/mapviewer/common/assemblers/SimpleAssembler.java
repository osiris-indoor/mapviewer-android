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

package com.fhc25.percepcion.osiris.mapviewer.common.assemblers;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base abstract class for Assemblers in this application. Transforms
 * data-transfer objects T into domain objects K
 * 
 * @param <T>
 *            data-transfer object type
 * @param <K>
 *            domain object type
 */
public abstract class SimpleAssembler<T, K> implements Assembler<T, K> {

	/**
	 * Transforms a group of data-transfer objects into domain objects.
	 * Implementation of the Assembler interface.
	 * 
	 * @param collection of data-transfer objects
	 * @return Collection of domain objects
	 */
	public Collection<K> createDomainObjects(Collection<T> collection) {

		Collection<K> solution = null;

		if (collection != null) {

			solution = new ArrayList<K>();
			for (T t : collection) {
				solution.add(createDomainObject(t));
			}
		}

		return solution;
	}

	/**
	 * Transforms a group of domain objects into data-transfer objects.
	 * Implementation of the Assembler interface
	 *
	 * @param collection of domain objects
	 * @return Collection of data-transfer objects
	 */
	public Collection<T> createDataTransferObjects(Collection<K> collection) {

        Collection<T> solution = null;

		if (collection != null) {

			solution = new ArrayList<T>();
			for (K k : collection) {
				solution.add(createDataTransferObject(k));
			}
		}

		return solution;
	}
}
