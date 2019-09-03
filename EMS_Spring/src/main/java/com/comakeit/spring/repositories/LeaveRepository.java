package com.comakeit.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.LeaveEntity;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity, String> {

	@Query("select l from LeaveEntity l where l.employee.employee_id =: employee_id")
	public String removeLeavesOfEmployee(@Param("employee_id") String employee_id);

	@Query("select l from LeaveEntity l where l.employee.employee_id=:employee_id AND l.status='approved' OR l.status='pending' AND MONTH(l.from_date)=:start_month AND MONTH(l.to_date)=:end_month")
	public List<LeaveEntity> getLeavesOfaMonth(@Param("employee_id") String employee_id,
			@Param("start_month") int start_month, @Param("end_month") int end_month);

	@Query("select l from LeaveEntity l where l.employee.employee_id =:employee_id")
	public List<LeaveEntity> getLeavesOfEmployee(@Param("employee_id") String employee_id);

	@Query("select l from LeaveEntity l where l.employee.manager_id =:manager_id AND l.status='pending'")
	public List<LeaveEntity> getLeaveRequests(@Param("manager_id") String manager_id);

	@Query("select l from LeaveEntity l where l.employee.employee_id =:employee_id AND (l.status='pending' OR l.status='approved')")
	public List<LeaveEntity> getAppliedLeavesOfEmployee(@Param("employee_id") String employee_id);

}
