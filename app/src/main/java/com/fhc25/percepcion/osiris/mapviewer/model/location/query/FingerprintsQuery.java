package com.fhc25.percepcion.osiris.mapviewer.model.location.query;

import java.io.Serializable;
import java.util.Date;

/**
 * Helper object that represents a complete fingerprints query to the backend
 */
public class FingerprintsQuery implements Serializable {

    private String id;
    private String user;
    private String space;
    private String building;
    private Double level;
    private Date minDate;
    private Date maxDate;
    private String orderField;
    private String order;

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getSpace() {
        return space;
    }

    public String getBuilding() {
        return building;
    }

    public Double getLevel() {
        return level;
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public String getOrderField() {
        return orderField;
    }

    public String getOrder() {
        return order;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setLevel(Double level) {
        this.level = level;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public FingerprintsQuery() {
    }

    public FingerprintsQuery(FingerprintsQuery fp) {
        
        user = fp.user;
        space = fp.space;
        building = fp.building;
        level = fp.level;
        minDate = fp.minDate;
        maxDate = fp.maxDate;

        orderField = fp.orderField;
        order = fp.order;
    }

}
