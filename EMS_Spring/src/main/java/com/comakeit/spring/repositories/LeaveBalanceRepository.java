package com.comakeit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.LeaveBalanceEntity;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceEntity, Integer>{
	@Query("SELECT l from LeaveBalanceEntity l where l.id =:leaveBalanceId")
	public LeaveBalanceEntity getLeaveBalance(@Param("leaveBalanceId") int leaveBalanceId);
}
