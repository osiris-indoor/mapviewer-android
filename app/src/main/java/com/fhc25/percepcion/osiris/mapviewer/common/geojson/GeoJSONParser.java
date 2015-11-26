package com.fhc25.percepcion.osiris.mapviewer.common.geojson;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.GeometryDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.LineStringDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PointDTO;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.PolygonDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Writer;

public class GeoJSONParser {

    static private final String TAG = GeoJSONParser.class.getName();

    /**
     * Parses a PointDTO to GeoJSON format and writes it into the output stream
     *
     * @param point
     * @param output
     */
    public void writePointDTO(PointDTO point, Writer output) {
        try {
            output.write(pointDTO2JSON(point).toString());
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }
    }

    /**
     * Gets the JSON representation of a PointDTO
     *
     * @param point
     * @return jsonObject
     */
    public JSONObject pointDTO2JSON(PointDTO point) {
        JSONObject root = new JSONObject();
        try {
            root.put("type", "Point");
            JSONArray coords = new JSONArray();
            coords.put(point.getLongitude());
            coords.put(point.getLatitude());
            root.put("coordinates", coords);
        } catch (JSONException e) {
            Lgr.e(TAG, e);
        }
        return root;
    }

    /**
     * Parses a LineDTO to GeoJSON format and writes it into the output stream
     *
     * @param lineString
     * @param output
     */
    public void writeLineStringDTO(LineStringDTO lineString, Writer output) {
        try {
            output.write(lineString2JSON(lineString).toString());
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }
    }

    /**
     * Gets the JSON representation of a LineStringDTO
     *
     * @param lineString
     * @return jsonObject
     */
    public JSONObject lineString2JSON(LineStringDTO lineString) {
        JSONObject root = new JSONObject();
        try {
            root.put("type", "LineString");
            JSONArray coords = new JSONArray();
            for (PointDTO point : lineString.getCollectionPointDTO()) {
                JSONArray coordsPoint = new JSONArray();
                coordsPoint.put(point.getLongitude());
                coordsPoint.put(point.getLatitude());
                coords.put(coordsPoint);
            }
            root.put("coordinates", coords);
        } catch (JSONException e) {
            Lgr.e(TAG, e);
        }
        return root;
    }

    /**
     * Parses a Polygon to GeoJSON format and writes it into the output stream
     *
     * @param polygon
     * @param output
     */
    public void writePolygonDTO(PolygonDTO polygon, Writer output) {
        try {
            output.write(polygonDTO2JSON(polygon).toString());
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }
    }

    /**
     * Gets the JSON representation of a PolygonDTO
     *
     * @param polygon
     * @return jsonObject
     */
    public JSONObject polygonDTO2JSON(PolygonDTO polygon) {
        JSONObject root = new JSONObject();
        try {
            root.put("type", "Polygon");
            JSONArray coords = new JSONArray();

            for (LineStringDTO lineString : polygon.getCollectionLineStringDTO()) {
                JSONArray coordsLine = new JSONArray();
                for (PointDTO point : lineString.getCollectionPointDTO()) {
                    JSONArray coordsPoint = new JSONArray();
                    coordsPoint.put(point.getLongitude());
                    coordsPoint.put(point.getLatitude());
                    coordsLine.put(coordsPoint);
                }
                coords.put(coordsLine);
            }
            root.put("coordinates", coords);
        } catch (JSONException e) {
            Lgr.e(TAG, e);
        }
        return root;
    }

    /**
     * Gets the JSON representation of a generic geometryDTO instance
     *
     * @param geometry
     * @return jsonObject
     */
    public JSONObject geometryDTO2JSON(GeometryDTO geometry) {
        JSONObject root = new JSONObject();
        if (geometry instanceof PointDTO) {
            root = this.pointDTO2JSON((PointDTO) geometry);
        } else if (geometry instanceof LineStringDTO) {
            root = this.lineString2JSON((LineStringDTO) geometry);
        } else if (geometry instanceof PolygonDTO) {
            root = this.polygonDTO2JSON((PolygonDTO) geometry);
        }
        return root;
    }

}
