package com.example.jwt_learning.service.impl;

import com.example.jwt_learning.models.Users;
import com.example.jwt_learning.repository.IUserRepository;
import com.example.jwt_learning.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
  private IUserRepository userRepository;
    @Override
    public Optional<Users> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }
}
