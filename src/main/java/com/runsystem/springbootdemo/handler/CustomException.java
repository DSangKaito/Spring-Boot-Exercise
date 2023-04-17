package com.runsystem.springbootdemo.handler;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    /** create status variable */
    private String status;

    /**
     * Constructor of this class
     * @param status String style
     * @param message String style
     */
    public CustomException(String status, String message) {
        super(message);
        this.status = status;
    }
}
