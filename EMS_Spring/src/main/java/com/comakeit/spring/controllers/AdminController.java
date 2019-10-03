package com.comakeit.spring.controllers;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
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

	ModelAndView modelView = new ModelAndView();
	RestTemplate restTemplate = new RestTemplate();
	String response;
	ResponseEntity<List<EmployeeEntity>> employeesList;
	ResponseEntity<List<DepartmentEntity>> departmentsList;
	EmployeeEntity employee;

	@RequestMapping("/addEmployee")
	@PostMapping
	public ModelAndView addEmployee(EmployeeEntity employee, DepartmentEntity department, RoleEntity role) {

		employee.setDepartment(department);

		LoginEntity login = new LoginEntity();
		
		login.setRole(role);
		employee.setLogin(login);

		response = restTemplate.postForObject(Constant.url + "/EMS/createEmployee", employee, String.class);

		if (response.equals("true")) {
			modelView.setViewName("AdminHomePage.jsp?result=created");
			modelView.addObject("employee", employee);
			return modelView;
		} else {
			modelView.setViewName("AdminHomePage.jsp?error=create");
		}
		return modelView;
	}

	@RequestMapping("/deleteEmployee")
	@PostMapping
	public ModelAndView deleteEmployee(EmployeeEntity employee) {

		employee = restTemplate.getForObject(Constant.url + "/EMS/employee/"+ employee.getEmployee_id(),
				EmployeeEntity.class);
		if (employee != null) {

			restTemplate.delete(Constant.url + "/EMS/removeEmployee/" + employee.getEmployee_id() + "/");
			modelView.setViewName("AdminHomePage.jsp?result=deleted");
			modelView.addObject("employee", employee);
		} else {
			modelView.setViewName("AdminHomePage.jsp?error=delete");
		}
		return modelView;
	}

	@RequestMapping("/employees")
	@GetMapping
	public ModelAndView viewEmployees() {

		employeesList = restTemplate.exchange(Constant.url + "/EMS/employees", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EmployeeEntity>>() {
				});

		if (employeesList != null) {
			modelView.setViewName("AdminHomePage.jsp?result=employees");
			modelView.addObject("employeesList", employeesList.getBody());

		}
		return modelView;
	}

	@RequestMapping("/departments")
	@GetMapping
	public ModelAndView viewDepartments() {

		departmentsList = restTemplate.exchange(Constant.url + "/EMS/departments", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DepartmentEntity>>() {
				});

		if (departmentsList != null) {
			modelView.setViewName("AdminHomePage.jsp?result=departments");
			modelView.addObject("departmentsList", departmentsList.getBody());
		}
		return modelView;
	}

	@RequestMapping("/employeeDetails")
	@PostMapping
	public ModelAndView viewEmployeeDetails(@RequestParam String employee_id) {

		try {
			employee = restTemplate.getForObject(Constant.url + "/EMS/employeeDetails/" + employee_id,
					EmployeeEntity.class);
			modelView.setViewName("AdminHomePage.jsp?result=employeeDetails");
			modelView.addObject("employee", employee);
		} catch (Exception e) {
			modelView.setViewName("AdminHomePage.jsp?action=employeeDetails&error=employeeDetails");
		}
		return modelView;
	}

	@RequestMapping("/employeesOfManager")
	@PostMapping
	public ModelAndView viewEmploeesOfManager(@RequestParam("manager_id") String manager_id) {

		if (!manager_id.equals("")) {
			employeesList = restTemplate.exchange(Constant.url + "/EMS/employeesOfManager/" + manager_id,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeEntity>>() {
					});

			if (employeesList.getBody() != null) {
				modelView.setViewName("AdminHomePage.jsp?result=employeesOfManager");
				modelView.addObject("manager_id", manager_id);
				modelView.addObject("employeesList", employeesList.getBody());
			}
		} else
			modelView.setViewName("AdminHomePage.jsp?action=employeesOfManager&error=employeesOfManager");
		return modelView;
	}

	@RequestMapping("/salaries")
	@PostMapping
	public ModelAndView viewEmploeesOfManager(@RequestParam("salaryFrom") double salaryFrom,
			@RequestParam("salaryTo") double salaryTo) {

		employeesList = restTemplate.exchange(Constant.url + "/EMS/salaries/" + salaryFrom + "/" + salaryTo,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeEntity>>() {
				});

		if (!employeesList.getBody().isEmpty()) {
			modelView.setViewName("AdminHomePage.jsp?result=salaries");
			modelView.addObject("employeesList", employeesList.getBody());
			return modelView;
		}
		modelView.setViewName("AdminHomePage.jsp?action=salaries&error=salaries");
		return modelView;
	}

}
