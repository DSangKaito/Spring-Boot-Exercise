package com.runsystem.springbootdemo.services.impl;

import com.runsystem.springbootdemo.config.CustomUserDetails;
import com.runsystem.springbootdemo.models.User;
import com.runsystem.springbootdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserById(Integer id) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findUserByUserId(id);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản!!!");
        }
        return new CustomUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản!!!");
        }
        return new CustomUserDetails(user);
    }


}
