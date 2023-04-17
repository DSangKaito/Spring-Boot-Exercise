package com.runsystem.springbootdemo.controllers;

import com.runsystem.springbootdemo.common.Validation;
import com.runsystem.springbootdemo.config.CustomUserDetails;
import com.runsystem.springbootdemo.config.JwtTokenProvider;
import com.runsystem.springbootdemo.payloads.request.LoginRequest;
import com.runsystem.springbootdemo.payloads.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    /** create bean AuthenticationManager */
    @Autowired
    AuthenticationManager authenticationManager;

    /** create bean JwtTokenProvider */
    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * @param loginRequest LoginRequest style from request
     * @return LoginResponse
     */
    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        Validation validation = new Validation(loginRequest.getUsername(), loginRequest.getPassword());
        validation.checkValid();
        // Xác thực từ username và password.
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }
}
