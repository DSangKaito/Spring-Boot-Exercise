package com.runsystem.springbootdemo.payloads.response;


import lombok.Data;

@Data
public class SignUpResponse {
    private String message;

    public SignUpResponse(String message) {
        this.message = message;
    }
}
