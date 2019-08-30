package com.comakeit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity,String >{
	@Query("select e from EmployeeEntity e where e.login.username =:username")
	public EmployeeEntity getEmployee(@Param("username") String username);
		
}
