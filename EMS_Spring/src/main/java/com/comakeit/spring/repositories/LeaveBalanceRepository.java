package com.comakeit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comakeit.spring.entities.LeaveBalanceEntity;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceEntity, Integer>{
}
