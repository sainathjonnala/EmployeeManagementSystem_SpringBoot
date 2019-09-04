package com.comakeit.spring.services;

import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LeaveEntity;
import com.comakeit.spring.repositories.LeaveBalanceRepository;
import com.comakeit.spring.repositories.LeaveRepository;

@Service
public class EmployeeLeaveService {

	LeaveBalanceEntity leaveBalance;
	List<LeaveEntity> leavesList;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;
	@Autowired
	private LeaveRepository leaveRepository;

	public boolean applyLeave(LeaveEntity leave) {
		leaveBalance = leaveBalanceRepository.findById(leave.getEmployee().getLeave_balance().getId()).get();
		Period period = Period.between(leave.getFrom_date(), leave.getTo_date());
		int duration = period.getDays();
		if (duration >= 5) {
			return false;
		}
		int leave_balance_count;
		if (leave.getLeave_type().equals("casual")) {
			leave_balance_count = leaveBalance.getCasual_leaves();
			if (leave_balance_count >= 1 && duration <= leave_balance_count) {
				leaveBalance.setCasual_leaves(leave_balance_count - duration);
				leaveRepository.save(leave);
				return true;
			}
		} else if (leave.getLeave_type().equals("loss_of_pay")) {
			leave_balance_count = leaveBalance.getLoss_of_pay();
			if (leave_balance_count >= 1 && duration <= leave_balance_count) {
				leavesList = leaveRepository.getLeavesOfaMonth(leave.getEmployee().getEmployee_id(),
						leave.getFrom_date().getMonthValue(), leave.getTo_date().getMonthValue());
				if (leavesList.size() < 3) {
					Period leaves_period;
					int leaves_duration = 0;
					for (LeaveEntity iterator : leavesList) {
						leaves_period = Period.between(iterator.getFrom_date(), iterator.getTo_date());
						leaves_duration += leaves_period.getDays();
					}
					if (leaves_duration + duration <= 3) {
						leaveBalance.setLoss_of_pay(leave_balance_count - duration);
						leaveRepository.save(leave);
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<LeaveEntity> getLeavesOfEmployee(EmployeeEntity employee) {
		return leaveRepository.getLeavesOfEmployee(employee.getEmployee_id());
	}

	public List<LeaveEntity> getLeaveRequests(EmployeeEntity employee) {
		return leaveRepository.getLeaveRequests(employee.getEmployee_id());
	}

	public boolean acceptLeaveRequest(LeaveEntity leave) {
		leave = leaveRepository.findById(leave.getLeave_id()).get();
		leave.setStatus("approved");
		leaveRepository.save(leave);
		return true;
	}

	public boolean rejectLeave(LeaveEntity leave) {
		leave = leaveRepository.findById(leave.getLeave_id()).get();
		Period period = Period.between(leave.getFrom_date(), leave.getTo_date());
		int duration = period.getDays();
		leaveBalance = leaveBalanceRepository.findById(leave.getEmployee().getLeave_balance().getId()).get();
		int leave_balance_count;
		if (leave.getLeave_type().equals("casual")) {
			leave_balance_count = leaveBalance.getCasual_leaves();
			leaveBalance.setCasual_leaves(leave_balance_count + duration);
			leave.setStatus("rejected");
			leaveRepository.save(leave);
			return true;
		} else if (leave.getLeave_type().equals("loss_of_pay")) {
			leave_balance_count = leaveBalance.getLoss_of_pay();
			leaveBalance.setLoss_of_pay(leave_balance_count + duration);
			leave.setStatus("rejected");
			leaveRepository.save(leave);
			return true;
		}
		leaveBalanceRepository.save(leaveBalance);
		leaveRepository.save(leave);
		return false;
	}

	public boolean cancelLeave(LeaveEntity leave) {
		leave = leaveRepository.findById(leave.getLeave_id()).get();

		Period period = Period.between(leave.getFrom_date(), leave.getTo_date());
		int duration = period.getDays();

		leaveBalance = leaveBalanceRepository.findById(leave.getEmployee().getLeave_balance().getId()).get();
		int leave_balance_count;

		if (leave.getLeave_type().equals("casual")) {
			leave_balance_count = leaveBalance.getCasual_leaves();
			leaveBalance.setCasual_leaves(leave_balance_count + duration);
			leave.setStatus("cancelled");
			leaveBalanceRepository.save(leaveBalance);
			leaveRepository.save(leave);
			return true;
		} else if (leave.getLeave_type().equals("loss_of_pay")) {
			leave_balance_count = leaveBalance.getLoss_of_pay();
			leaveBalance.setLoss_of_pay(leave_balance_count + duration);
			leave.setStatus("cancelled");
			leaveBalanceRepository.save(leaveBalance);
			leaveRepository.save(leave);
			return true;
		}

		return false;
	}

	public List<LeaveEntity> getAppliedLeavesOfEmployee(EmployeeEntity employee) {
		return leaveRepository.getAppliedLeavesOfEmployee(employee.getEmployee_id());
	}

	public LeaveBalanceEntity getLeaveBalance(EmployeeEntity employee) {
		return leaveBalanceRepository.getLeaveBalance(employee.getLeave_balance().getId());
	}

}
