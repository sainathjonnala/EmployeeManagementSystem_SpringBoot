package com.comakeit.spring.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.spring.constants.Constant;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LeaveEntity;

@Controller
public class LeaveController {

	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<List<LeaveEntity>> leavesList;
	ModelAndView modelView = new ModelAndView();
	String restResponse;

	@RequestMapping(value = "/applyLeave", method = RequestMethod.POST)
	public ModelAndView applyForLeave(LeaveEntity leave, @RequestParam("start_date") String from_date,
			@RequestParam("end_date") String to_date, @SessionAttribute("employee") EmployeeEntity employee) {
		leave.setFrom_date(LocalDate.parse(from_date));
		leave.setTo_date(LocalDate.parse(to_date));
		leave.setStatus("pending");
		leave.setApply_to(employee.getManager_id());
		leave.setEmployee(employee);

		restResponse = restTemplate.postForObject(Constant.url + "/EMS/applyLeave", leave, String.class);

		if (restResponse.equals("true"))
			modelView.setViewName("EmployeeHomePage.jsp?message=success");
		else
			modelView.setViewName("EmployeeHomePage.jsp?message=failed");

		return modelView;
	}

	@RequestMapping(value = "/viewLeaves", method = RequestMethod.GET)
	public ModelAndView viewLeavesOfEmployee(@SessionAttribute("employee") EmployeeEntity employee) {

		leavesList = restTemplate.exchange(Constant.url + "/EMS/viewLeaves/" + employee.getEmployee_id(),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<LeaveEntity>>() {
				});

		modelView.setViewName("EmployeeHomePage.jsp?action=view_leaves");
		modelView.addObject("leavesList", leavesList.getBody());
		modelView.addObject("employee", employee);
		return modelView;
	}

	@RequestMapping(value = "/viewAppliedLeaves", method = RequestMethod.GET)
	public ModelAndView viewAppliedLeavesOfEmployee(@SessionAttribute("employee") EmployeeEntity employee) {

		leavesList = restTemplate.exchange(Constant.url + "/EMS/viewAppliedLeaves/" + employee.getEmployee_id(),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<LeaveEntity>>() {
				});

		modelView.setViewName("EmployeeHomePage.jsp?action=cancel_leave");
		modelView.addObject("leavesList", leavesList.getBody());
		return modelView;
	}

	@RequestMapping(value = "/rejectLeave", method = RequestMethod.GET)
	public ModelAndView rejectLeaveRequest(LeaveEntity leave) {

		restTemplate.put(Constant.url + "/EMS/rejectLeave", leave);

		modelView.setViewName("/viewLeaveRequests");
		return modelView;
	}

	@RequestMapping(value = "/acceptLeave", method = RequestMethod.GET)
	public ModelAndView acceptLeaveRequest(LeaveEntity leave) {

		restTemplate.put(Constant.url + "/EMS/acceptLeave", leave);
		modelView.setViewName("/viewLeaveRequests");
		return modelView;
	}

	@RequestMapping(value = "/cancelLeave", method = RequestMethod.GET)
	public ModelAndView cancelLeaveRequest(LeaveEntity leave) {

		restTemplate.put(Constant.url + "/EMS/cancelLeave", leave);
		modelView.setViewName("/viewAppliedLeaves");
		return modelView;
	}

	@RequestMapping(value = "/viewLeaveRequests", method = RequestMethod.GET)
	public ModelAndView viewLeaveRequestsOfManager(@SessionAttribute("employee") EmployeeEntity employee) {

		leavesList = restTemplate.exchange(Constant.url + "/EMS/leaveRequests/" + employee.getEmployee_id(),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<LeaveEntity>>() {
				});
		modelView.setViewName("EmployeeHomePage.jsp?action=view_leave_requests");
		modelView.addObject("leavesList", leavesList.getBody());
		return modelView;
	}

	@RequestMapping(value = "/viewLeaveBalance", method = RequestMethod.GET)
	public ModelAndView viewLeaveBalance(@SessionAttribute("employee") EmployeeEntity employee) {

		LeaveBalanceEntity leaveBalance = restTemplate.getForObject(
				Constant.url + "/EMS/viewLeaveBalance/" + employee.getEmployee_id(), LeaveBalanceEntity.class);
		modelView.setViewName("EmployeeHomePage.jsp?action=view_leave_balance");
		modelView.addObject("leaveBalance", leaveBalance);
		return modelView;
	}

}
