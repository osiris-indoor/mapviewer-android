package com.fhc25.percepcion.osiris.mapviewer.common.errors;

public class Failure {

    private String message;

    public Failure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
