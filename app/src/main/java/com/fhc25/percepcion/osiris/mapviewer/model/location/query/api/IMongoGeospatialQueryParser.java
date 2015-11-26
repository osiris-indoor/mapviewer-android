package com.fhc25.percepcion.osiris.mapviewer.model.location.query.api;

import com.fhc25.percepcion.osiris.mapviewer.model.location.query.MongoGeospatialQuery;

public interface IMongoGeospatialQueryParser {

    String toJSON(MongoGeospatialQuery query);
}
