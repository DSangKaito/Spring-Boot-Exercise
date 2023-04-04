package com.runsystem.springbootdemo.controllers;

import com.runsystem.springbootdemo.common.Message;
import com.runsystem.springbootdemo.common.Validation;
import com.runsystem.springbootdemo.payloads.request.SignUpRequest;
import com.runsystem.springbootdemo.payloads.response.SignUpResponse;
import com.runsystem.springbootdemo.services.SignUpService;
import com.runsystem.springbootdemo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignUpController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    SignUpService signUpService;

    @PutMapping("/add")
    public SignUpResponse addUser(@RequestBody SignUpRequest signUpRequest){
        if(signUpRequest == null) return new SignUpResponse(Message.INPUT_DATA_NULL);
        Validation validation = new Validation(signUpRequest.getUsername(),signUpRequest.getPassword(),signUpRequest.getRePassword());
        validation.checkValid();
        if (!signUpService.addUser(signUpRequest)) return new SignUpResponse(Message.ACCOUNT_EXITS);
        return new SignUpResponse(Message.LOGIN_SUCCESS);
    }
}
