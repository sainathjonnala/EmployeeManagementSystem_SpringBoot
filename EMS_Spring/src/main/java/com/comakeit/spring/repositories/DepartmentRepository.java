package com.comakeit.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String>{

	@Query("select d from DepartmentEntity d ORDER BY department_name desc")
	public List<DepartmentEntity> getDepartmentsList();
}
