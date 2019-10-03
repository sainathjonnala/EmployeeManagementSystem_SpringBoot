package com.comakeit.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.spring.entities.DepartmentEntity;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.exceptions.DetailsNotFound;
import com.comakeit.spring.services.AdminService;

@RestController
@RequestMapping("EMS")
public class AdminRest {

	EmployeeEntity employee;

	@Autowired
	private AdminService adminService;

	@RequestMapping("createEmployee")
	@PostMapping
	public String createEmployee(@RequestBody EmployeeEntity employee) {
		return adminService.insertEmployee(employee);
	}

	@RequestMapping("removeEmployee/{employee_id}")
	@DeleteMapping
	public String deleteEmployee(@PathVariable("employee_id") String employee_id) {
		return adminService.removeEmployee(employee_id);
	}

	@RequestMapping("departments")
	@GetMapping
	public List<DepartmentEntity> viewDepartments() {

		return adminService.viewDepartments();
	}

	@RequestMapping("employees")
	@GetMapping
	public List<EmployeeEntity> viewEmployees() {

		return adminService.viewEmployees();
	}

	@RequestMapping("employee/{employee_id}")
	@GetMapping
	public EmployeeEntity viewEmployeeDetails(@PathVariable String employee_id) throws Exception {

		employee = adminService.viewEmployeeDetails(employee_id);
		if (employee == null) {
			throw new DetailsNotFound();
		}
		return employee;
	}

	@RequestMapping("employeesOfManager/{manager_id}")
	@GetMapping
	public List<EmployeeEntity> viewEmployeesOfManager(@PathVariable String manager_id) throws Exception {
		return adminService.viewEmployeesOfManager(manager_id);
	}

	@RequestMapping("salaries/{salaryFrom}/{salaryTo}")
	@GetMapping
	public List<EmployeeEntity> viewEmployeesBySalary(@PathVariable("salaryFrom") double salaryFrom,
			@PathVariable("salaryTo") double salaryTo) {

		return adminService.viewEmployeesBySalary(salaryFrom, salaryTo);
	}

}
