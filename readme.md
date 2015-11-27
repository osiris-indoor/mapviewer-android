# Osiris Mapviewer for Android

## Introduction

This code contains an Android demo application for visualizing indoor areas using
the Osiris platform. The application, uses Mapsforge (https://github.com/mapsforge/mapsforge)
for displaying the background map image in the mobile device. Uses the overlay drawing
capabilities for displaying the different levels of the buildings.

## Configuration

The configuration parameters are set using Android resources files. Specifically,
the file "values/strings.xml" is used for loading the configuration.

The main configuration parameters that need to be set in the application are the following:

- Environment, set using the attribute name *osiris_app_host*, for instance

  `<string name="app_host">http://myserver.com/osiris</string>`

- Application ID, set by the attribute *osiris_app_id*

  `<string name="app_id">myOsirisApplication</string>`

The application will load this configuration at startup and download the map and indoor
information if necessary.

Also, the configuration of the URLs for the different osiris services can be found in
the file `OsirisEndpoint.java`
