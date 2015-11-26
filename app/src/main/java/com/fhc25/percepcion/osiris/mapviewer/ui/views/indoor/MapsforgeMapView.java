package com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.util.MercatorProjection;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapDatabase;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.mapsforge.map.reader.header.MapFileInfo;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by alvaroarranz on 03/07/15.
 */
public class MapsforgeMapView extends RelativeLayout {

    private static final String TAG = MapsforgeMapView.class.getName();

    private static final String LAT_ID = "map_view_latitude";
    private static final String LON_ID = "map_view_longitude";
    private static final String ZOOM_ID = "map_view_zoom";
    private static final String MAPFILE_ID = "map_view_mapfile";

    private static final byte MAX_ZOOM = 22;

    private double latitude = 0;
    private double longitude = 0;
    private byte zoom = 16;
    private File mapFile;

    private MapView mapView;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;

    private Observer.Collection observers = new Observer.Collection();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public MapsforgeMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        createMap(context);
        addView(mapView);
    }

    public MapView getMapView() {
        return mapView;
    }

    public double getMapLatitude() {
        return latitude;
    }

    public byte getZoom() {
        return mapView.getModel().mapViewPosition.getZoomLevel();
    }

    public double getMapLongitude() {
        return longitude;
    }

    public void setZoomLevel(byte zoom) {
        this.zoom = zoom;
        mapView.getModel().mapViewPosition.setZoomLevel(zoom);
    }

    public void setMapPosition(double lat, double lon) {

        latitude = lat;
        longitude = lon;

        LatLong center = new LatLong(latitude, longitude);
        MapPosition mapPosition = new MapPosition(center, zoom);

        mapView.getModel().mapViewPosition.setMapPosition(mapPosition);
    }

    public LatLong getProjectionFromPixels(int x, int y) {

        if (mapView.getWidth() <= 0 || mapView.getHeight() <= 0) {
            return null;
        }

        MapPosition mapPosition = this.mapView.getModel().mapViewPosition
                .getMapPosition();

        // calculate the pixel coordinates of the top left corner
        LatLong latLong = mapPosition.latLong;

        double pixelX = MercatorProjection.longitudeToPixelX(
                latLong.longitude,
                MercatorProjection.getMapSize(mapPosition.zoomLevel,
                        mapView.getModel().displayModel.getTileSize()));
        double pixelY = MercatorProjection.latitudeToPixelY(latLong.latitude,
                mapPosition.zoomLevel,
                mapView.getModel().displayModel.getTileSize());
        pixelX -= this.mapView.getWidth() >> 1;
        pixelY -= this.mapView.getHeight() >> 1;

        // convert the pixel coordinates to a LatLong and return it
        return new LatLong(
                MercatorProjection.pixelYToLatitude(pixelY + y,
                        MercatorProjection.getMapSize(mapPosition.zoomLevel,
                                mapView.getModel().displayModel.getTileSize())),
                MercatorProjection.pixelXToLongitude(pixelX + x,
                        MercatorProjection.getMapSize(mapPosition.zoomLevel,
                                mapView.getModel().displayModel.getTileSize())));

    }

    public void saveState(Bundle savedInstanceState) {

        savedInstanceState.putDouble(LAT_ID, latitude);
        savedInstanceState.putDouble(LON_ID, longitude);
        savedInstanceState.putByte(ZOOM_ID, zoom);
        savedInstanceState.putSerializable(MAPFILE_ID, mapFile);
    }

    private void createMap(Context context) {

        mapView = new MapView(context);
        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(false);
        mapView.setBuiltInZoomControls(false);

        mapView.getModel().mapViewPosition.setZoomLevelMax(MAX_ZOOM);

        // create a tile cache of suitable size
        tileCache = AndroidUtil.createTileCache(context,
                "mapcache", mapView.getModel().displayModel.getTileSize(), 1f,
                mapView.getModel().frameBufferModel.getOverdrawFactor());

        mapView.getModel().mapViewPosition.addObserver(new org.mapsforge.map.model.common.Observer() {

            @Override
            public void onChange() {
                LatLong center = mapView.getModel().mapViewPosition
                        .getMapPosition().latLong;
                latitude = center.latitude;
                longitude = center.longitude;
                zoom = mapView.getModel().mapViewPosition.getZoomLevel();

                observers.onPositionChange(latitude, longitude, zoom);
            }

        });
    }

    public void destroy() {

        // Destroy layers
        for (Layer layer : mapView.getLayerManager().getLayers()) {
            mapView.getLayerManager().getLayers().remove(layer);
            //layer.onDestroy();
        }

        // Destroy tiles caches
        tileCache.destroy();
        // Destroy mapview
        mapView.getModel().mapViewPosition.destroy();
        mapView.destroy();

        org.mapsforge.map.android.graphics.AndroidResourceBitmap.clearResourceBitmaps();
    }

    public void loadState(Bundle savedInstanceState) {

        if (savedInstanceState.containsKey(MAPFILE_ID)) {
            mapFile = (File) savedInstanceState.getSerializable(MAPFILE_ID);
        }
        if (savedInstanceState.containsKey(LAT_ID)) {
            latitude = savedInstanceState.getDouble(LAT_ID);
        }
        if (savedInstanceState.containsKey(LON_ID)) {
            longitude = savedInstanceState.getDouble(LON_ID);
        }
        if (savedInstanceState.containsKey(ZOOM_ID)) {
            zoom = savedInstanceState.getByte(ZOOM_ID);
        }
        if (mapView != null) {
            setMapFile(mapFile);
            setMapPosition(latitude, longitude);
            setZoomLevel(zoom);
        }
    }

    public void onPause() {

        if (tileRendererLayer != null
                && mapView.getLayerManager().getLayers()
                .contains(tileRendererLayer)) {
            mapView.getLayerManager().getLayers().remove(tileRendererLayer);
            tileRendererLayer.onDestroy();
            tileRendererLayer = null;
        }
    }

    public void setMapFile(File mapFile) {

        if (mapFile.exists() && mapView != null) {

            this.mapFile = mapFile;

            if (tileRendererLayer != null
                    && mapView.getLayerManager().getLayers()
                    .contains(tileRendererLayer)) {
                mapView.getLayerManager().getLayers()
                        .remove(tileRendererLayer);
                tileRendererLayer.onDestroy();
                tileRendererLayer = null;
            }

            tileCache.destroy();
            // create a tile cache of suitable size
            tileCache = AndroidUtil.createTileCache(getContext(),
                    "mapcache", mapView.getModel().displayModel.getTileSize(), 1f,
                    mapView.getModel().frameBufferModel.getOverdrawFactor());

            MapDatabase mapDatabase = new MapDatabase();
            final FileOpenResult result = mapDatabase.openFile(mapFile);
            if (result.isSuccess()) {
                final MapFileInfo mapFileInfo = mapDatabase.getMapFileInfo();
                MapPosition mapPosition = null;
                if (mapFileInfo != null && mapFileInfo.startPosition != null) {
                    mapPosition = new MapPosition(mapFileInfo.startPosition,
                            (byte) mapFileInfo.startZoomLevel);
                } else {
                    mapPosition = new MapPosition(new LatLong(latitude, longitude),
                            zoom);
                }

                mapView.getModel().mapViewPosition.setMapPosition(mapPosition);
            }

            tileRendererLayer = new TileRendererLayer(tileCache,
                    mapView.getModel().mapViewPosition, false, true,
                    AndroidGraphicFactory.INSTANCE);

            try {
                tileRendererLayer.setMapFile(mapFile);
                tileRendererLayer
                        .setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
                tileRendererLayer.setTextScale(1.5f);
                mapView.getLayerManager().getLayers().add(tileRendererLayer);

                setMapPosition(latitude, longitude);
                setZoomLevel(zoom);
            } catch (Exception e) {
                Lgr.e(TAG, e);
            }
        } else {
            Lgr.w(TAG, "Could not find  " + mapFile.toString() + " or mapview == null");
        }
    }

    public interface Observer {

        void onPositionChange(double latitude, double longitude, byte zoom);

        class Collection extends ArrayList<Observer> implements Observer {

            @Override
            public void onPositionChange(double latitude, double longitude, byte zoom) {

                for (Observer obs : this) {
                    obs.onPositionChange(latitude, longitude, zoom);
                }
            }
        }

    }

}
