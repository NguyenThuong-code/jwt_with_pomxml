package com.example.jwt_learning.service;

import com.example.jwt_learning.models.Users;
import org.apache.catalina.User;

import java.util.Optional;

public interface IUserService {
    Optional<Users>findByUsername(String name);
    Boolean existByUsername(String username);
    Boolean existByEmail(String email);
    Users save(Users user);
}
