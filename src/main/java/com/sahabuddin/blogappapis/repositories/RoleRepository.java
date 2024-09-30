package com.sahabuddin.blogappapis.repositories;

import com.sahabuddin.blogappapis.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
