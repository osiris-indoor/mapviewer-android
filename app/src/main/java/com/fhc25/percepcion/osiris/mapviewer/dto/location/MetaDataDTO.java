
package com.fhc25.percepcion.osiris.mapviewer.dto.location;

public class MetaDataDTO {

	private String routingChecksum;
	private String osmchecksum;

	private String minLatitude;
	private String minLongitude;
	private String maxLatitude;
	private String maxLongitude;
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

    public String getOsmchecksum() {
        return osmchecksum;
    }

    public void setOsmchecksum(String checksum) {
        osmchecksum = checksum;
    }

	public void setRoutingChecksum(String checksum) {
		routingChecksum = checksum;
	}

	public String getRoutingChecksum() {
		return routingChecksum;
	}

	public String getMinLatitude() {
		return minLatitude;
	}

	public void setMinLatitude(String minLatitude) {
		this.minLatitude = minLatitude;
	}

	public String getMinLongitude() {
		return minLongitude;
	}

	public void setMinLongitude(String minLongitude) {
		this.minLongitude = minLongitude;
	}

	public String getMaxLatitude() {
		return maxLatitude;
	}

	public void setMaxLatitude(String maxLatitude) {
		this.maxLatitude = maxLatitude;
	}

	public String getMaxLongitude() {
		return maxLongitude;
	}

	public void setMaxLongitude(String maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

}
