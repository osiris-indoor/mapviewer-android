
package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.InputStream;

/**
 * Mapsforge map loaded as an InputStream
 */
public class MapsforgeMap {

	private InputStream mInputStream;

	public MapsforgeMap(InputStream input){
		mInputStream = input;
	}
	
	public InputStream getInputStream(){
		return mInputStream;
	}
	
}
