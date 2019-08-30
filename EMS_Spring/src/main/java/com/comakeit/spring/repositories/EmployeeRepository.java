package com.comakeit.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
	@Query("select e from EmployeeEntity e ORDER BY employee_id")
	public List<EmployeeEntity> getEmployeesList();

	@Query("select e from EmployeeEntity e where e.manager_id= :manager_id ORDER BY e.email")
	public List<EmployeeEntity> getEmployeesOfManager(@Param("manager_id") String manager_id);

	@Query("select e from EmployeeEntity e where e.salary BETWEEN :salaryFrom AND :salaryTo")
	public List<EmployeeEntity> getEmployeesBySalary(@Param("salaryFrom") double salaryFrom,
			@Param("salaryTo") double salaryTo);

}
