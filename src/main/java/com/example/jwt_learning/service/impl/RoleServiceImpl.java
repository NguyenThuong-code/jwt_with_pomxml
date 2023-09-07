package com.example.jwt_learning.service.impl;

import com.example.jwt_learning.models.Role;
import com.example.jwt_learning.models.RoleName;
import com.example.jwt_learning.repository.IRoleRepository;
import com.example.jwt_learning.service.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
