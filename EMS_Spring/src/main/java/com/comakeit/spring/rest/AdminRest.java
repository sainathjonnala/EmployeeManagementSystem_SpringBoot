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
import com.comakeit.spring.services.AdminService;

@RestController
@RequestMapping("EMS")
public class AdminRest {

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

	@RequestMapping("viewDepartments")
	@GetMapping
	public List<DepartmentEntity> viewDepartments() {
		return adminService.viewDepartments();
	}

	@RequestMapping("viewEmployees")
	@GetMapping
	public List<EmployeeEntity> viewEmployees() {
		return adminService.viewEmployees();
	}

	@RequestMapping("viewEmployeeDetails")
	@PostMapping
	public EmployeeEntity viewEmployeeDetails(@RequestBody EmployeeEntity employee) {
		return adminService.viewEmployeeDetails(employee);
	}

	@RequestMapping("viewEmployeesOfManager")
	@PostMapping
	public List<EmployeeEntity> viewEmployeesOfManager(@RequestBody String manager_id) {
		return adminService.viewEmployeesOfManager(manager_id);
	}

	@RequestMapping("viewEmployeesBySalary/{salaryFrom}/{salaryTo}")
	@GetMapping
	public List<EmployeeEntity> viewEmployeesBySalary(@PathVariable("salaryFrom") double salaryFrom,
			@PathVariable("salaryTo") double salaryTo) {
		return adminService.viewEmployeesBySalary(salaryFrom, salaryTo);
	}

}
