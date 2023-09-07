package com.example.jwt_learning.repository;

import com.example.jwt_learning.models.Role;
import com.example.jwt_learning.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
