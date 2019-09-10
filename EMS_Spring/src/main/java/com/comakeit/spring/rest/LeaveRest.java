package com.comakeit.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LeaveEntity;
import com.comakeit.spring.services.EmployeeLeaveService;

@RestController
@RequestMapping("EMS")
public class LeaveRest {

	LeaveEntity leave;
	@Autowired
	private EmployeeLeaveService employeeLeaveService;

	@RequestMapping(value = "/applyLeave", method = RequestMethod.POST)
	public boolean applyForLeave(@RequestBody LeaveEntity leave) {
		return employeeLeaveService.applyLeave(leave);
	}

	@RequestMapping(value = "/cancelLeave", method = RequestMethod.PUT)
	public boolean cancelLeaveRequest(@RequestBody LeaveEntity leave) {
		return employeeLeaveService.cancelLeave(leave);
	}

	@RequestMapping(value = "/rejectLeave", method = RequestMethod.PUT)
	public boolean rejectLeaveRequest(@RequestBody LeaveEntity leave) {
		return employeeLeaveService.rejectLeave(leave);
	}

	@RequestMapping(value = "/acceptLeave", method = RequestMethod.PUT)
	public boolean acceptLeaveRequest(@RequestBody LeaveEntity leave) {
		return employeeLeaveService.acceptLeaveRequest(leave);
	}

	@RequestMapping(value = "/leaveRequests", method = RequestMethod.POST)
	public List<LeaveEntity> viewLeaveRequest(@RequestBody EmployeeEntity employee) {
		return employeeLeaveService.getLeaveRequests(employee);
	}

	@RequestMapping(value = "/viewLeaves", method = RequestMethod.POST)
	public List<LeaveEntity> viewLeavesOfEmployee(@RequestBody EmployeeEntity employee) {
		return employeeLeaveService.getLeavesOfEmployee(employee);
	}

	@RequestMapping(value = "/viewAppliedLeaves", method = RequestMethod.POST)
	public List<LeaveEntity> viewAppliedLeavesOfEmployee(@RequestBody EmployeeEntity employee) {
		return employeeLeaveService.getAppliedLeavesOfEmployee(employee);
	}

	@RequestMapping(value = "/viewLeaveBalance", method = RequestMethod.POST)
	public LeaveBalanceEntity viewLeaveBalance(@RequestBody EmployeeEntity employee) {
		return employeeLeaveService.getLeaveBalance(employee);
	}

}
