package com.runsystem.springbootdemo.payloads.response;

import lombok.Data;

@Data
public class LoginResponse {
    /** JWT */
    private String jwt;

    /**
     * constructor
     * @param jwt response jwt to FE
     */
    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }
}
