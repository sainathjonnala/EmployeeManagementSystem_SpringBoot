package com.comakeit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.spring.entities.DepartmentEntity;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveEntity;
import com.comakeit.spring.repositories.DepartmentRepository;
import com.comakeit.spring.repositories.EmployeeRepository;
import com.comakeit.spring.repositories.LeaveRepository;

@Service
public class AdminService {

	DepartmentEntity department;
	LeaveEntity leave;

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private LeaveRepository leaveRepository;

	public String insertEmployee(EmployeeEntity employee) {
		department = departmentRepository.findById(employee.getDepartment().getDepartment_id()).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
		return "true";
	}

	public String removeEmployee(String employee_id) {
		if (employeeRepository.existsById(employee_id)) {
			EmployeeEntity employee = employeeRepository.findById(employee_id).get();
			List<LeaveEntity> leavesList = leaveRepository.findAll();
			for (LeaveEntity iterator : leavesList) {
				if (iterator.getEmployee().getEmployee_id().equals(employee.getEmployee_id())) {
					leaveRepository.delete(iterator);
				}
			}
			employeeRepository.delete(employee);
			return "true";
		}
		return "false";
	}

	public List<DepartmentEntity> viewDepartments() {
		return departmentRepository.getDepartmentsList();
	}

	public List<EmployeeEntity> viewEmployees() {
		return employeeRepository.getEmployeesList();
	}

	public EmployeeEntity viewEmployeeDetails(String employee_id) {
		if (employeeRepository.existsById(employee_id)) {
			return employeeRepository.findById(employee_id).get();
		}
		return null;
	}

	public List<EmployeeEntity> viewEmployeesOfManager(String manager_id) {
		if (employeeRepository.existsById(manager_id)) {
			return employeeRepository.getEmployeesOfManager(manager_id);
		}
		return null;
	}

	public List<EmployeeEntity> viewEmployeesBySalary(double salaryFrom, double salaryTo) {
		return employeeRepository.getEmployeesBySalary(salaryFrom, salaryTo);
	}

}
