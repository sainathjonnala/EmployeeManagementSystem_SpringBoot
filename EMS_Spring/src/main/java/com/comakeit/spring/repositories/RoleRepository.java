package com.comakeit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer>{

}
