package com.fhc25.percepcion.osiris.mapviewer.common.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import java.io.IOException;
import java.util.Collection;

public class JSONParser<T> {

    static private final String TAG = JSONParser.class.getName();

    private final Class<T> typeClass;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Main constructor
     *
     * @param typeClass
     */
    public JSONParser(Class<T> typeClass) {
        this.typeClass = typeClass;
    }


    /**
     * Transforms a json expression in an instance
     *
     * @param json
     * @return
     */
    public T parseObject(String json) {

        T objectParsed = null;
        try {
            objectParsed = mapper.readValue(json, this.typeClass);
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }

        return objectParsed;
    }

    /**
     * Transforms a json expressin in a collection of domain instances
     *
     * @param json
     * @return
     */
    public Collection<T> parseObjectCollection(String json, TypeReference typeReference) {

        Collection<T> objectParsed = null;
        try {
            objectParsed = mapper.readValue(json, typeReference);
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }

        return objectParsed;
    }

    /**
     * Transforms an object into a json string
     *
     * @param object
     * @return
     */
    public String toJSON(T object) {

        String json = null;

        try {
            json = mapper.writeValueAsString(object);
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }

        return json;
    }

    /**
     * Transforms a Collection of objects into a json string
     *
     * @param object
     * @return
     */
    public String toJSON(Collection<T> object) {

        String json = null;

        try {
            json = mapper.writeValueAsString(object);
        } catch (IOException e) {
            Lgr.e(TAG, e);
        }

        return json;
    }

}
