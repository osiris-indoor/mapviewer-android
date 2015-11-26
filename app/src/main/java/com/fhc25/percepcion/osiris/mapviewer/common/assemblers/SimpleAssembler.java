
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
