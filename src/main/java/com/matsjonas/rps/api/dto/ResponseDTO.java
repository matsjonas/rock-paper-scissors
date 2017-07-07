package com.matsjonas.rps.api.dto;

/**
 * Simple data transfer object representing a simple response message.
 * <p>
 * Instances will be serialized to JSON and used to communicate between client and server over the api.
 */
public class ResponseDTO {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
