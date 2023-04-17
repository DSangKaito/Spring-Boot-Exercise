package com.runsystem.springbootdemo.payloads.response;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;

@Getter
@Setter
@ResponseBody
@Slf4j
public class DataResponse {
    /** status variable for response */
    private String status;
    /** message variable for response */
    private String message;
    /** object variable for message */
    private Object object;

    /**
     * constructor
     * @param status String type for response
     * @param message String type for message
     */
    public DataResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.object = null;
    }

    /**
     * constructor
     * @param status String type for response
     * @param object Object type for message
     */
    public DataResponse(String status, Object object) {
        this.status = status;
        this.object = object;
        this.message = null;
    }

    /**
     * constructor
     * @param status String type for response
     * @param object Object type for message
     * @param message String type for response
     */
    public DataResponse(String status, Object object, String message) {
        this.status = status;
        this.object = object;
        this.message = message;
    }
}
