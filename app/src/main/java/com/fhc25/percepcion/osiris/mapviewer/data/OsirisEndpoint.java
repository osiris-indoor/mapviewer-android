package com.fhc25.percepcion.osiris.mapviewer.data;

public class OsirisEndpoint implements IOsirisEndpoint {

    private String host;

    public OsirisEndpoint(String host) {
        this.host = host;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getMapFileService() {
        return "/geolocation/territory/map/file";
    }

    @Override
    public String getMapService() {
        return "/geolocation/territory/search/";
    }

    @Override
    public String getMetadataService() {
        return "/geolocation/territory/map/metadata/";
    }

}
