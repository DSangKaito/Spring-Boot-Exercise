package com.runsystem.springbootdemo.payloads.response;

import lombok.Data;

@Data
public class SignUpResponse {
    /** message */
    private String message;

    /**
     * constructor
     * @param message String type for response
     */
    public SignUpResponse(String message) {
        this.message = message;
    }
}
