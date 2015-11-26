package com.fhc25.percepcion.osiris.mapviewer.common.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RequestConfiguration {

    private Endpoint endpoint;
    private String path = "";
    private String pathParams = "";
    private String queryParams = "";
    private String method = "";
    private String body = "";
    private Collection<Map.Entry<String, String> > headers;

    public RequestConfiguration(Endpoint endpoint, String path, String method) {
        this.endpoint = endpoint;
        this.method = method;
        this.path = path;

        body = null;
        headers = new ArrayList<Map.Entry<String, String> >();
    }

    public RequestConfiguration(Endpoint endpoint, String path, String pathParams, String queryParams, String method) {
        this.endpoint = endpoint;
        this.path = path;
        this.pathParams = pathParams;
        this.queryParams = queryParams;
        this.method = method;

        body = null;
        headers = new ArrayList<Map.Entry<String, String> >();
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathParams() {
        return pathParams;
    }

    public void setPathParams(String pathParams) {
        this.pathParams = pathParams;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    public String getUrl() {
        return endpoint.getHost() + path + pathParams + queryParams;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public Collection<Map.Entry<String, String> > getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setBodyObject(Object object) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            this.body = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String key, String value) {

        headers.add(new AbstractMap.SimpleEntry<String, String>(key, value));
    }

    public Map.Entry<String, String> findHeaderKey(String key) {

        for (Map.Entry<String, String> pair : headers) {
            if (pair.getKey().equals(key)) {
                return pair;
            }
        }

        return null;
    }

    public boolean containsHeaderKey(String key) {

        return findHeaderKey(key) != null;
    }

    @Override
    public String toString() {
        return method + " " + getUrl() + " body: " + body + " with security:";
    }

}
