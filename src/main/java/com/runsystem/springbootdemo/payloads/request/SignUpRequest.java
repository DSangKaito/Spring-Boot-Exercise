package com.runsystem.springbootdemo.payloads.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private String rePassword;
}
