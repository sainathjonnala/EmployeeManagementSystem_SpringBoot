package com.comakeit.spring.services;

import java.security.SecureRandom;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LeaveEntity;
import com.comakeit.spring.repositories.EmployeeRepository;
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
	@Autowired
	private EmployeeRepository employeeRepository;

	public boolean applyLeave(LeaveEntity leave) {
		System.out.println(leave);
		leaveBalance = leaveBalanceRepository.findById(leave.getEmployee().getLeave_balance().getId()).get();
		Period period = Period.between(leave.getFrom_date(), leave.getTo_date());
		int leaveDuration = period.getDays();
		if (leaveDuration >= 5) {
			return false;
		}
		int leave_balance_count;
		if (leave.getLeave_type().equals("casual")) {
			leave_balance_count = leaveBalance.getCasual_leaves();
			if (leave_balance_count >= 1 && leaveDuration <= leave_balance_count) {
				leaveBalance.setCasual_leaves(leave_balance_count - leaveDuration);
				leave.setLeave_id(createLeaveId());
				System.out.println(leave);
				leaveRepository.save(leave);
				return true;
			}
		} else if (leave.getLeave_type().equals("loss_of_pay")) {
			leave_balance_count = leaveBalance.getLoss_of_pay();
			if (leave_balance_count >= 1 && leaveDuration <= leave_balance_count) {
				leavesList = leaveRepository.getLeavesOfaMonth(leave.getEmployee().getEmployee_id(),
						leave.getFrom_date().getMonthValue(), leave.getTo_date().getMonthValue());
				if (leavesList.size() < 3) {
					Period leaves_period;
					int leaves_leaveDuration = 0;
					for (LeaveEntity iterator : leavesList) {
						leaves_period = Period.between(iterator.getFrom_date(), iterator.getTo_date());
						leaves_leaveDuration += leaves_period.getDays();
					}
					if (leaves_leaveDuration + leaveDuration <= 3) {
						leaveBalance.setLoss_of_pay(leave_balance_count - leaveDuration);
						leave.setLeave_id(createLeaveId());
						leaveRepository.save(leave);
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<LeaveEntity> getLeavesOfEmployee(String employee_id) {
		return leaveRepository.getLeavesOfEmployee(employee_id);
	}

	public List<LeaveEntity> getLeaveRequests(String employee_id) {
		return leaveRepository.getLeaveRequests(employee_id);
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
		int leaveDuration = period.getDays();
		leaveBalance = leaveBalanceRepository.findById(leave.getEmployee().getLeave_balance().getId()).get();
		int leave_balance_count;
		if (leave.getLeave_type().equals("casual")) {
			leave_balance_count = leaveBalance.getCasual_leaves();
			leaveBalance.setCasual_leaves(leave_balance_count + leaveDuration);
			leave.setStatus("rejected");
			leaveRepository.save(leave);
			return true;
		} else if (leave.getLeave_type().equals("loss_of_pay")) {
			leave_balance_count = leaveBalance.getLoss_of_pay();
			leaveBalance.setLoss_of_pay(leave_balance_count + leaveDuration);
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
		int leaveDuration = period.getDays();

		leaveBalance = leaveBalanceRepository.findById(leave.getEmployee().getLeave_balance().getId()).get();
		int leave_balance_count;

		if (leave.getLeave_type().equals("casual")) {
			leave_balance_count = leaveBalance.getCasual_leaves();
			leaveBalance.setCasual_leaves(leave_balance_count + leaveDuration);
			leave.setStatus("cancelled");
			leaveBalanceRepository.save(leaveBalance);
			leaveRepository.save(leave);
			return true;
		} else if (leave.getLeave_type().equals("loss_of_pay")) {
			leave_balance_count = leaveBalance.getLoss_of_pay();
			leaveBalance.setLoss_of_pay(leave_balance_count + leaveDuration);
			leave.setStatus("cancelled");
			leaveBalanceRepository.save(leaveBalance);
			leaveRepository.save(leave);
			return true;
		}

		return false;
	}

	public List<LeaveEntity> getAppliedLeavesOfEmployee(String employee_id) {
		return leaveRepository.getAppliedLeavesOfEmployee(employee_id);
	}

	public LeaveBalanceEntity getLeaveBalance(String employee_id) {
		return employeeRepository.findById(employee_id).get().getLeave_balance();
	}
	
	public static String createLeaveId() {
		return "LV" + new SecureRandom().nextInt() % 100000;
	}
	
}
