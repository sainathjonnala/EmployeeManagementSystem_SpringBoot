package com.comakeit.spring.controllers;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.spring.constants.Constant;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LeaveEntity;

@Controller
@SessionAttributes({ "leavesList" })
public class LeaveController {

	RestTemplate restTemplate;
	ResponseEntity<List<LeaveEntity>> leavesList;
	ModelAndView modelView;
	String restResponse;

	@RequestMapping(value = "/applyLeave", method = RequestMethod.POST)
	public ModelAndView applyForLeave(LeaveEntity leave, @RequestParam("start_date") String from_date,
			@RequestParam("end_date") String to_date, @SessionAttribute("employee") EmployeeEntity employee) {
		leave.setLeave_id(createLeaveId());
		leave.setFrom_date(LocalDate.parse(from_date));
		leave.setTo_date(LocalDate.parse(to_date));
		leave.setStatus("pending");
		leave.setApply_to(employee.getManager_id());
		leave.setEmployee(employee);
		restTemplate = new RestTemplate();
		restResponse = restTemplate.postForObject(Constant.url + "/EMS/applyLeave", leave, String.class);
		if (restResponse.equals("true"))
			modelView = new ModelAndView("EmployeeHomePage.jsp?message=success");
		else
			modelView = new ModelAndView("EmployeeHomePage.jsp?message=failed");
		return modelView;
	}

	@RequestMapping(value = "/viewLeaves", method = RequestMethod.GET)
	public ModelAndView viewLeavesOfEmployee(@SessionAttribute("employee") EmployeeEntity employee) {
		restTemplate = new RestTemplate();
		HttpEntity<EmployeeEntity> employeeHttpEntity = new HttpEntity<EmployeeEntity>(employee);
		leavesList = restTemplate.exchange(Constant.url + "/EMS/viewLeaves", HttpMethod.POST, employeeHttpEntity,
				new ParameterizedTypeReference<List<LeaveEntity>>() {
				});
		modelView = new ModelAndView("EmployeeHomePage.jsp?action=view_leaves");
		modelView.addObject("leavesList", leavesList.getBody());
		modelView.addObject("employee", employee);
		return modelView;
	}

	@RequestMapping(value = "/viewAppliedLeaves", method = RequestMethod.GET)
	public ModelAndView viewAppliedLeavesOfEmployee(@SessionAttribute("employee") EmployeeEntity employee) {
		restTemplate = new RestTemplate();
		HttpEntity<EmployeeEntity> employeeHttpEntity = new HttpEntity<EmployeeEntity>(employee);
		leavesList = restTemplate.exchange(Constant.url + "/EMS/viewAppliedLeaves", HttpMethod.POST, employeeHttpEntity,
				new ParameterizedTypeReference<List<LeaveEntity>>() {
				});
		modelView = new ModelAndView("EmployeeHomePage.jsp?action=cancel_leave");
		modelView.addObject("leavesList", leavesList.getBody());
		return modelView;
	}

	@RequestMapping(value = "/rejectLeave", method = RequestMethod.GET)
	public ModelAndView rejectLeaveRequest(LeaveEntity leave) {
		restTemplate = new RestTemplate();
		restTemplate.put(Constant.url + "/EMS/rejectLeave", leave);
		modelView = new ModelAndView("/viewLeaveRequests");
		return modelView;
	}

	@RequestMapping(value = "/acceptLeave", method = RequestMethod.GET)
	public ModelAndView acceptLeaveRequest(LeaveEntity leave) {
		restTemplate = new RestTemplate();
		restTemplate.put(Constant.url + "/EMS/acceptLeave", leave);
		modelView = new ModelAndView("/viewLeaveRequests");
		return modelView;
	}

	@RequestMapping(value = "/cancelLeave", method = RequestMethod.GET)
	public ModelAndView cancelLeaveRequest(LeaveEntity leave) {
		restTemplate = new RestTemplate();
		restTemplate.put(Constant.url + "/EMS/cancelLeave", leave);
		modelView = new ModelAndView("/viewAppliedLeaves");
		return modelView;
	}

	@RequestMapping(value = "/viewLeaveRequests", method = RequestMethod.GET)
	public ModelAndView viewLeaveRequestsOfManager(@SessionAttribute("employee") EmployeeEntity employee) {
		restTemplate = new RestTemplate();
		HttpEntity<EmployeeEntity> employeeHttpEntity = new HttpEntity<EmployeeEntity>(employee);
		leavesList = restTemplate.exchange(Constant.url + "/EMS/leaveRequests", HttpMethod.POST, employeeHttpEntity,
				new ParameterizedTypeReference<List<LeaveEntity>>() {
				});
		modelView = new ModelAndView("EmployeeHomePage.jsp?action=view_leave_requests");
		modelView.addObject("leavesList", leavesList.getBody());
		return modelView;
	}

	@RequestMapping("/viewLeaveBalance")
	@PostMapping
	public ModelAndView viewLeaveBalance(@SessionAttribute("employee") EmployeeEntity employee) {
		restTemplate = new RestTemplate();
		LeaveBalanceEntity leaveBalance = restTemplate.postForObject(Constant.url + "/EMS/viewLeaveBalance", employee,
				LeaveBalanceEntity.class);
		modelView = new ModelAndView("EmployeeHomePage.jsp?action=view_leave_balance");
		modelView.addObject("leaveBalance", leaveBalance);
		if (leaveBalance != null) {
			return modelView;
		}
		return null;
	}

	public static String createLeaveId() {
		return "LV" + new SecureRandom().nextInt() % 100000;
	}

}
