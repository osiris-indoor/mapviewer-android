package com.fhc25.percepcion.osiris.mapviewer.assemblers.utils;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;

public class LocationAndroidParser {

    public static Location parseLocation(android.location.Location location) {

        Location locationOwn = new Location(location.getLatitude(), location.getLongitude(), 0);

        Integer floor = 0;

        if (location.getExtras() != null && location.getExtras().containsKey("FLOOR")) {
            floor = location.getExtras().getInt("FLOOR");
            locationOwn.setFloor(floor);
        } else {
            locationOwn.setFloor(floor);
        }

        return locationOwn;
    }

    public static android.location.Location parseLocation(Location location, String provider, Float accuracy) {

        android.location.Location androidLocation = new android.location.Location(provider);

        androidLocation.setLatitude(location.getPosition().getLatitude());
        androidLocation.setLongitude(location.getPosition().getLongitude());

        androidLocation.getExtras().putInt("FLOOR", location.getFloor());

        if (accuracy != null) {
            androidLocation.setAccuracy(accuracy);
        }

        return androidLocation;
    }
}
