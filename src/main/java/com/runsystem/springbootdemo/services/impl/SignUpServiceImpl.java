package com.runsystem.springbootdemo.services.impl;

import com.runsystem.springbootdemo.models.User;
import com.runsystem.springbootdemo.payloads.request.SignUpRequest;
import com.runsystem.springbootdemo.repositories.UserRepository;
import com.runsystem.springbootdemo.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {
    /** create bean PasswordEncoder */
    @Autowired
    PasswordEncoder passwordEncoder;

    /** create bean UserRepository */
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean addUser(SignUpRequest signUpRequest) {
        User user = userRepository.findUserByUsername(signUpRequest.getUsername());
        if (user != null) {
            return false;
        }
        user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
        return true;
    }

}
