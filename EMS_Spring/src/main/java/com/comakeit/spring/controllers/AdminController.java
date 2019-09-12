package com.comakeit.spring.controllers;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.spring.constants.Constant;
import com.comakeit.spring.entities.DepartmentEntity;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LoginEntity;
import com.comakeit.spring.entities.RoleEntity;

@Controller
@ResponseBody
public class AdminController {
	ModelAndView modelView;
	RestTemplate restTemplate;
	String response;
	ResponseEntity<List<EmployeeEntity>> employeesList;
	ResponseEntity<List<DepartmentEntity>> departmentsList;
	EmployeeEntity employee;

	@RequestMapping("/addEmployee")
	@PostMapping
	public ModelAndView addEmployee(EmployeeEntity employee, DepartmentEntity department, RoleEntity role) {
		restTemplate = new RestTemplate();

		employee.setDepartment(department);

		if (role.getRole_id() == 2)
			role.setRole_name("employee");
		else
			role.setRole_name("manager");

		LoginEntity login = new LoginEntity();
		login.setUsername(createUsername());
		login.setPassword(createPassword());
		login.setRole(role);
		employee.setLogin(login);

		LeaveBalanceEntity leaveBalance = new LeaveBalanceEntity();
		leaveBalance.setCasual_leaves(10);
		leaveBalance.setLoss_of_pay(10);
		employee.setLeave_balance(leaveBalance);

		response = restTemplate.postForObject(Constant.url + "/EMS/createEmployee", employee, String.class);

		if (response.equals("true")) {
			modelView = new ModelAndView("AdminHomePage.jsp?result=created");
			modelView.addObject("employee", employee);
			return modelView;
		}

		return new ModelAndView("AdminHomePage.jsp?error=create");
	}

	@RequestMapping("/deleteEmployee")
	@PostMapping
	public ModelAndView deleteEmployee(EmployeeEntity employee) {
		restTemplate = new RestTemplate();
		employee = restTemplate.postForObject(Constant.url + "/EMS/viewEmployeeDetails", employee,
				EmployeeEntity.class);
		if (employee != null) {

			restTemplate.delete(Constant.url + "/EMS/removeEmployee/" + employee.getEmployee_id() + "/");
			modelView = new ModelAndView("AdminHomePage.jsp?result=deleted");
			modelView.addObject("employee", employee);
		}
		modelView = new ModelAndView("AdminHomePage.jsp?error=delete");
		return modelView;
	}

	@RequestMapping("/employees")
	@GetMapping
	public ModelAndView viewEmployees() {
		restTemplate = new RestTemplate();

		employeesList = restTemplate.exchange(Constant.url + "/EMS/viewEmployees", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EmployeeEntity>>() {
				});

		if (employeesList != null) {
			modelView = new ModelAndView("AdminHomePage.jsp?result=employees");
			modelView.addObject("employeesList", employeesList.getBody());
			return modelView;
		}
		return null;
	}

	@RequestMapping("/departments")
	@GetMapping
	public ModelAndView viewDepartments() {
		restTemplate = new RestTemplate();

		departmentsList = restTemplate.exchange(Constant.url + "/EMS/viewDepartments", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DepartmentEntity>>() {
				});

		if (departmentsList != null) {
			modelView = new ModelAndView("AdminHomePage.jsp?result=departments");
			modelView.addObject("departmentsList", departmentsList.getBody());
			return modelView;
		}
		return null;
	}

	@RequestMapping("/employeeDetails")
	@PostMapping
	public ModelAndView viewEmployeeDetails(@RequestParam String employee_id) {

			restTemplate = new RestTemplate();
			System.out.println(employee_id);
			employee = restTemplate.getForObject(Constant.url + "/EMS/viewEmployeeDetails/"+ employee_id,
					EmployeeEntity.class);

			if (employee != null) {
				employee.setPF(employee.getSalary() * (0.05));
				modelView = new ModelAndView("AdminHomePage.jsp?result=employeeDetails");
				modelView.addObject("employee", employee);
				return modelView;
			}
		return new ModelAndView("AdminHomePage.jsp?action=employeeDetails&error=employeeDetails");
	}

	@RequestMapping("/employeesOfManager")
	@PostMapping
	public ModelAndView viewEmploeesOfManager(@RequestParam("manager_id") String manager_id) {

		if (!manager_id.equals("")) {
			restTemplate = new RestTemplate();

			employeesList = restTemplate.exchange(Constant.url + "/EMS/viewEmployeesOfManager/" + manager_id,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeEntity>>() {
					});

			if (employeesList.getBody() != null) {
				modelView = new ModelAndView("AdminHomePage.jsp?result=employeesOfManager");
				modelView.addObject("manager_id", manager_id);
				modelView.addObject("employeesList", employeesList.getBody());
				return modelView;
			}
		}
		modelView = new ModelAndView(
				"AdminHomePage.jsp?action=employeesOfManager&error=employeesOfManager");
		return modelView;
	}

	@RequestMapping("/salaries")
	@PostMapping
	public ModelAndView viewEmploeesOfManager(@RequestParam("salaryFrom") double salaryFrom,
			@RequestParam("salaryTo") double salaryTo) {
		restTemplate = new RestTemplate();
		String salaries = "/" + salaryFrom + "/" + salaryTo;
		employeesList = restTemplate.exchange(Constant.url + "/EMS/viewEmployeesBySalary" + salaries, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<EmployeeEntity>>() {
				});

		if (!employeesList.getBody().isEmpty()) {
			modelView = new ModelAndView("AdminHomePage.jsp?result=salaries");
			modelView.addObject("employeesList", employeesList.getBody());
			return modelView;
		}
		modelView = new ModelAndView(
				"AdminHomePage.jsp?action=salaries&error=salaries");
		return modelView;
	}

	public static String createUsername() {
		return "UN" + new SecureRandom().nextInt() % 100000;
	}

	public static String createPassword() {
		return "PW" + new SecureRandom().nextInt() % 100000;
	}

}
