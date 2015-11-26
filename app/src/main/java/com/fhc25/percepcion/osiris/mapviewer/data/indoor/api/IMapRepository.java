
package com.fhc25.percepcion.osiris.mapviewer.data.indoor.api;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Feature;
import com.fhc25.percepcion.osiris.mapviewer.model.location.query.MongoGeospatialQuery;
import com.fhc25.percepcion.osiris.mapviewer.model.location.query.api.IMongoGeospatialQueryParser;

import java.util.Collection;

public interface IMapRepository {

    ICancellableTask getMap(MongoGeospatialQuery query, String layer, IMongoGeospatialQueryParser mongoGeospatialQueryParser,
                            final ICallback<Collection<Feature>> callback);
}
