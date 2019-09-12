package com.comakeit.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/leaveRequests/{employee_id}", method = RequestMethod.GET)
	public List<LeaveEntity> viewLeaveRequest(@PathVariable String employee_id) {
		return employeeLeaveService.getLeaveRequests(employee_id);
	}

	@RequestMapping(value = "/viewLeaves/{employee_id}", method = RequestMethod.GET)
	public List<LeaveEntity> viewLeavesOfEmployee(@PathVariable String employee_id) {
		return employeeLeaveService.getLeavesOfEmployee(employee_id);
	}

	@RequestMapping(value = "/viewAppliedLeaves/{employee_id}", method = RequestMethod.GET)
	public List<LeaveEntity> viewAppliedLeavesOfEmployee(@PathVariable String employee_id) {
		return employeeLeaveService.getAppliedLeavesOfEmployee(employee_id);
	}

	@RequestMapping(value = "/viewLeaveBalance/{employee_id}", method = RequestMethod.GET)
	public LeaveBalanceEntity viewLeaveBalance(@PathVariable String employee_id) {
		return employeeLeaveService.getLeaveBalance(employee_id);
	}

}
