package com.comakeit.spring.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.spring.entities.DepartmentEntity;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LeaveBalanceEntity;
import com.comakeit.spring.entities.LeaveEntity;
import com.comakeit.spring.entities.LoginEntity;
import com.comakeit.spring.entities.RoleEntity;
import com.comakeit.spring.repositories.DepartmentRepository;
import com.comakeit.spring.repositories.EmployeeRepository;
import com.comakeit.spring.repositories.LeaveRepository;
import com.comakeit.spring.repositories.RoleRepository;

@Service
public class AdminService {

	DepartmentEntity department;
	LeaveEntity leave;
	LoginEntity login;
	RoleEntity role;
	LeaveBalanceEntity leaveBalance;
	EmployeeEntity employee;

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private LeaveRepository leaveRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	public boolean addEmployee(EmployeeEntity employee) {
		
		role = roleRepository.findById(employee.getLogin().getRole().getRole_id()).get();
		
		login = new LoginEntity();
		login.setUsername(createUsername());
		login.setPassword(createPassword());
		login.setRole(role);
		employee.setLogin(login);
		
		department = departmentRepository.findById(employee.getDepartment().getDepartment_id()).get();
		employee.setDepartment(department);
		
		leaveBalance = new LeaveBalanceEntity();
		leaveBalance.setCasual_leaves(10);
		leaveBalance.setLoss_of_pay(10);
		employee.setLeave_balance(leaveBalance);
		
		employeeRepository.save(employee);
		return true;
	}

	public boolean removeEmployee(String employee_id) {
		
		if (employeeRepository.existsById(employee_id)) {
			EmployeeEntity employee = employeeRepository.findById(employee_id).get();
			List<LeaveEntity> leavesList = leaveRepository.findAll();
			for (LeaveEntity iterator : leavesList) {
				if (iterator.getEmployee().getEmployee_id().equals(employee.getEmployee_id())) {
					leaveRepository.delete(iterator);
				}
			}
			employeeRepository.delete(employee);
			return true;
		}
		return false;
	}

	public List<DepartmentEntity> viewDepartments() {
		return departmentRepository.getDepartmentsList();
	}

	public List<EmployeeEntity> viewEmployees() {
		return employeeRepository.getEmployeesList();
	}

	public EmployeeEntity viewEmployeeDetails(String employee_id) {
		
		if (employeeRepository.existsById(employee_id)) {
			employee = employeeRepository.findById(employee_id).get();
			employee.setPF(employee.getSalary() * (0.05));
			return employee;
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
	public static String createUsername() {
		return "UN" + new SecureRandom().nextInt() % 100000;
	}

	public static String createPassword() {
		return "PW" + new SecureRandom().nextInt() % 100000;
	}

}
