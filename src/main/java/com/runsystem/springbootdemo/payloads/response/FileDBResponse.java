package com.runsystem.springbootdemo.payloads.response;

import lombok.Data;

@Data
public class FileDBResponse {
    /** name of fileDB for response */
    private String name;
    /** url of fileDB for response */
    private String url;
    /** type of fileDB for response */
    private String type;
    /** size of fileDB for response */
    private long size;

    /**
     * constructor
     * @param name String type for response
     * @param url String type for response
     * @param size Long type for response
     */
    public FileDBResponse(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }
}
