package com.runsystem.springbootdemo.payloads.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginRequest {
    /** username from request */
    private String username;
    /** password from request */
    private String password;
}
