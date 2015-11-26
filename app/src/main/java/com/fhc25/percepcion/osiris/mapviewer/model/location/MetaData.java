
package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;

/**
 * Metadata information for a ceratain application. Calculated by the backend,
 * its purpose is to check if the model saved in the backend has suffered any
 * modification. Includes the checksum of the backend model, and map geographic
 * information
 */
public class MetaData implements Serializable {

	private String routingChecksum = "";
	private String osmchecksum = "";

	private Double minLatitude = 0.0;
	private Double minLongitude = 0.0;
	private Double maxLatitude = 0.0;
	private Double maxLongitude = 0.0;
	private String appId = "";

	/**
	 * Gets the app ID related to this metadata
	 * 
	 * @return app ID
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * Sets the app ID related to this metadata
	 * 
	 * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * Sets the checksum related to the routing information in the backend
	 * 
	 * @param checksum
	 */
	public void setRoutingChecksum(String checksum) {
		routingChecksum = checksum;
	}
	
	/**
	 * Gets the checksum related to the routing information in the backend
	 * 
	 * @return routing checksum
	 */
	public String getRoutingChecksum() {
		return routingChecksum;
	}

	/**
	 * Sets the checksum related to the OSM file in the backend
	 * 
	 * @param checksum
	 */
	public void setOsmchecksum(String checksum) {
		osmchecksum = checksum;
	}
	
	/**
	 * Gets the checksum related to the OSM file saved in the backend
	 * 
	 * @return osm checksum
	 */
	public String getOsmchecksum() {
		return osmchecksum;
	}

	/**
	 * Sets the checksum related to the OSM file saved in the backend
	 * 
	 * @param chkSum
	 */
	public void setChkSum(String chkSum) {
		this.osmchecksum = chkSum;
	}

	/**
	 * Gets the minimum latitude of the OSM map
	 * 
	 * @return min latitude
	 */
	public Double getMinLatitude() {
		return minLatitude;
	}

	/**
	 * Sets the minimum latitude of the OSM map
	 * 
	 * @param minLatitude
	 */
	public void setMinLatitude(Double minLatitude) {
		this.minLatitude = minLatitude;
	}

	/**
	 * Gets the minimum longitude of the OSM map
	 * 
	 * @return min longitude
	 */
	public Double getMinLongitude() {
		return minLongitude;
	}

	/**
	 * Sets the minimum longitude of the OSM map
	 * 
	 * @param minLongitude
	 */
	public void setMinLongitude(Double minLongitude) {
		this.minLongitude = minLongitude;
	}

	/**
	 * Gets the maximum latitude of the OSM map
	 * 
	 * @return max latitude
	 */
	public Double getMaxLatitude() {
		return maxLatitude;
	}

	/**
	 * Sets the maximum latitude of the OSM map
	 * 
	 * @param maxLatitude
	 */
	public void setMaxLatitude(Double maxLatitude) {
		this.maxLatitude = maxLatitude;
	}

	/**
	 * Gets the maximum longitude of the OSM map
	 * 
	 * @return max longitude
	 */
	public Double getMaxLongitude() {
		return maxLongitude;
	}

	/**
	 * Sets the maximum longitudeo of the OSM map
	 * 
	 * @param maxLongitude
	 */
	public void setMaxLongitude(Double maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

	public double getCenterLong() {
		return (maxLongitude + minLongitude) / 2.0;
	}

	public double getCenterLat() {
		return (maxLatitude + minLatitude) / 2.0;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (! (obj instanceof MetaData)) {
			return false;
		}

		MetaData metaData = (MetaData) obj;

		if (metaData.osmchecksum != null || osmchecksum !=null) {

			if (metaData.osmchecksum == null || osmchecksum == null || !osmchecksum.equals(metaData.osmchecksum)) {
				return false;
			}
		}

		if (metaData.routingChecksum != null || routingChecksum !=null) {

			if (metaData.routingChecksum == null || routingChecksum == null || !routingChecksum.equals(metaData.routingChecksum)) {
				return false;
			}
		}

		if (metaData.appId != null || appId !=null) {

			if (metaData.appId == null || appId == null || !appId.equals(metaData.appId)) {
				return false;
			}
		}

		if (metaData.minLatitude != null || minLatitude !=null) {

			if (metaData.minLatitude == null || minLatitude == null || !minLatitude.equals(metaData.minLatitude)) {
				return false;
			}
		}

		if (metaData.minLongitude != null || minLongitude !=null) {

			if (metaData.minLongitude == null || minLongitude == null || !minLongitude.equals(metaData.minLongitude)) {
				return false;
			}
		}

		if (metaData.maxLatitude != null || maxLatitude !=null) {

			if (metaData.maxLatitude == null || maxLatitude == null || !maxLatitude.equals(metaData.maxLatitude)) {
				return false;
			}
		}

		if (metaData.maxLongitude != null || maxLongitude !=null) {

			if (metaData.maxLongitude == null || maxLongitude == null || !maxLongitude.equals(metaData.maxLongitude)) {
				return false;
			}
		}


		return true;
	}
}
