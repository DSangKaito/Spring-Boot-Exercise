package com.runsystem.springbootdemo.repositories;

import com.runsystem.springbootdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserId(Integer id);

    User findUserByUsername(String username);
}
