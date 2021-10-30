package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.models.ERole;
import com.example.demo.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(ERole name);
}
