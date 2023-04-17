package com.runsystem.springbootdemo.payloads.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpRequest {
    /** username from request sign up */
    private String username;
    /** password from request sign up */
    private String password;
    /** password check from request sign up */
    private String rePassword;
}
