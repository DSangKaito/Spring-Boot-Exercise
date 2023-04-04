package com.runsystem.springbootdemo.services;

import com.runsystem.springbootdemo.payloads.request.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public interface SignUpService {
    public boolean addUser(SignUpRequest signUpRequest);
}
