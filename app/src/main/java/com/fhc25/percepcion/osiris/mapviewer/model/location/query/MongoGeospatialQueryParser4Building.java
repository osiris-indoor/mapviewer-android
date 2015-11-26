package com.fhc25.percepcion.osiris.mapviewer.model.location.query;

import com.fhc25.percepcion.osiris.mapviewer.assemblers.model.location.GeometryAssemblerImpl;
import com.fhc25.percepcion.osiris.mapviewer.common.geojson.GeoJSONParser;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.dto.location.GeometryDTO;
import com.fhc25.percepcion.osiris.mapviewer.model.location.query.api.IMongoGeospatialQueryParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoGeospatialQueryParser4Building implements IMongoGeospatialQueryParser {

    static private final String TAG = MongoGeospatialQueryParser4Building.class.getName();

    @Override
    public String toJSON(MongoGeospatialQuery query) {

        String jsonString = "";

        if (query instanceof MongoGeospatialNEARQuery) {
            jsonString = toJSON((MongoGeospatialNEARQuery) query);
        } else if( query instanceof MongoGeospatialGEOQuery) {
            jsonString = toJSON((MongoGeospatialGEOQuery) query);
        }

        return jsonString;
    }

    private String toJSON(MongoGeospatialNEARQuery query) {

        JSONObject root = new JSONObject();

        try {
            GeoJSONParser gjson = new GeoJSONParser();

            GeometryDTO geometryDTO = null;
            GeometryAssemblerImpl geometryAssembler = new GeometryAssemblerImpl();
            geometryDTO = geometryAssembler.createDataTransferObject(query.getGeometry());

            JSONObject jsonGeometry = gjson.geometryDTO2JSON(geometryDTO);

            JSONObject geometry = new JSONObject();
            geometry.put("$geometry", jsonGeometry);
            geometry.put("$maxDistance", query.getMaxDistance());

            JSONObject operator = new JSONObject();

            String searchOperator = "";

            if (query.getSearchType() == MongoGeospatialNEARQuery.SearchTypeNEAR.NEAR) {
                searchOperator = "$near";
            } else if (query.getSearchType() == MongoGeospatialNEARQuery.SearchTypeNEAR.NEAR_SPHERE) {
                searchOperator = "$nearSphere";
            }

            operator.put(searchOperator, geometry);
            root.put("geometry", operator);

            JSONObject roleBuildingPart = new JSONObject();
            roleBuildingPart.put("properties.@role", "buildingpart");

            JSONObject roleShell = new JSONObject();
            roleShell.put("properties.@role", "shell");

            JSONObject roleDoor = new JSONObject();
            roleDoor.put("properties.door", "yes");

            JSONArray orArray = new JSONArray();
            orArray.put(roleBuildingPart);
            orArray.put(roleShell);
            orArray.put(roleDoor);

            orArray.put(createJsonObjectType("indoor", "way", "room"));
            orArray.put(createJsonObjectType("indoor", "way", "office"));
            orArray.put(createJsonObjectType("door", "node", "sliding"));
            orArray.put(createJsonObjectType("door", "node", "hinged"));
            orArray.put(createJsonObjectType("indoor", "way", "corridor"));
            orArray.put(createJsonObjectType("indoor", "way", "elevator"));
            orArray.put(createJsonObjectType("indoor", "way", "level"));

            root.put("$or", orArray);

        } catch (JSONException e) {
            Lgr.e(TAG, e);
        }

        return root.toString();
    }


    private String toJSON(MongoGeospatialGEOQuery query) {

        JSONObject root = new JSONObject();

        try {
            GeoJSONParser gjson = new GeoJSONParser();

            GeometryDTO geometryDTO = null;
            GeometryAssemblerImpl geometryAssembler = new GeometryAssemblerImpl();
            geometryDTO = geometryAssembler.createDataTransferObject(query.getGeometry());

            JSONObject jsonGeometry = gjson.geometryDTO2JSON(geometryDTO);

            JSONObject geometry = new JSONObject();
            geometry.put("$geometry", jsonGeometry);

            String searchTypeOperator = "";

            if (query.getSearchType() == MongoGeospatialGEOQuery.SearchTypeGEO.GEO_WITHIN) {
                searchTypeOperator = "$geoWithin";
            } else if (query.getSearchType() == MongoGeospatialGEOQuery.SearchTypeGEO.GEO_INTERSECTS) {
                searchTypeOperator = "$geoIntersects";
            }

            JSONObject operator = new JSONObject();
            operator.put(searchTypeOperator, geometry);

            root.put("geometry", operator);

            JSONObject roleBuildingPart = new JSONObject();
            roleBuildingPart.put("properties.@role", "buildingpart");

            JSONObject roleShell = new JSONObject();
            roleShell.put("properties.@role", "shell");

            JSONObject roleDoor = new JSONObject();
            roleDoor.put("properties.door", "yes");

            JSONArray orArray = new JSONArray();
            orArray.put(roleBuildingPart);
            orArray.put(roleShell);
            orArray.put(roleDoor);

            orArray.put(createJsonObjectType("indoor", "way", "room"));
            orArray.put(createJsonObjectType("indoor", "way", "office"));
            orArray.put(createJsonObjectType("door", "node", "sliding"));
            orArray.put(createJsonObjectType("door", "node", "hinged"));
            orArray.put(createJsonObjectType("indoor", "way", "corridor"));
            orArray.put(createJsonObjectType("indoor", "way", "elevator"));
            orArray.put(createJsonObjectType("indoor", "way", "level"));

            root.put("$or", orArray);

        } catch (JSONException e) {
            Lgr.e(TAG, e);
        }

        return root.toString();
    }

    private JSONObject createJsonObjectType(String property, String type, String name) throws JSONException {

        JSONObject wayObj = new JSONObject();
        wayObj.put("properties.@type", type);

        JSONObject typeObj = new JSONObject();
        typeObj.put("properties." + property, name);

        JSONArray array = new JSONArray();
        array.put(wayObj);
        array.put(typeObj);

        JSONObject andObj = new JSONObject();
        andObj.put("$and", array);

        return andObj;
    }

}
