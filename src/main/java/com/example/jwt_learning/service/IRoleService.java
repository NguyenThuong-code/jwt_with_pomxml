package com.example.jwt_learning.service;

import com.example.jwt_learning.models.Role;
import com.example.jwt_learning.models.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);

}
