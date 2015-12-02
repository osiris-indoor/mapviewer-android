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

import java.util.Collection;

/**
 * Interface that establishes the capacity of assemble objects of type T from K
 * and vice versa
 *
 * @param <T>
 *            data-transfer object type
 * @param <K>
 *            domain object type
 */
public interface Assembler<T, K> {

	/**
	 * Creates a certain domain object from a data transfer object
	 *
	 * @param dataTransferObject
	 * @return domain object
	 */
	K createDomainObject(T dataTransferObject);

	/**
	 * Creates a data-transfer object from a domain object
	 *
	 * @param domainObject
	 * @return data-transfer object
	 */
	T createDataTransferObject(K domainObject);

	/**
	 * Transforms a group of data-transfer objects into domain objects
	 *
	 * @param collectionTimetableClassProfessorDTO
	 * @return Collection of domain objects
	 */
	Collection<K> createDomainObjects(
			Collection<T> collectionTimetableClassProfessorDTO);

	/**
	 * Transforms a group of domain objects into data-transfer objects
	 *
	 * @param collectionTimetableClassProfessor
	 * @return Collection of data-transfer objects
	 */
	Collection<T> createDataTransferObjects(
			Collection<K> collectionTimetableClassProfessor);

}
