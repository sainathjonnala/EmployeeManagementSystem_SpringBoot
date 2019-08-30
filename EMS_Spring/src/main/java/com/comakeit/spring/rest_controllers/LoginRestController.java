package com.comakeit.spring.rest_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LoginEntity;
import com.comakeit.spring.services.LoginService;

@RestController
@RequestMapping("EMS")
public class LoginRestController {
	
	EmployeeEntity employee;
	@Autowired
	private LoginService loginService;

	@RequestMapping("/login")
	@PostMapping
	public LoginEntity isValidUser(@RequestBody LoginEntity loginCredentials) {
		loginCredentials = loginService.findUser(loginCredentials);
		return loginCredentials;
	}
	
	@RequestMapping("/user")
	@PostMapping
	public EmployeeEntity findUser(@RequestBody LoginEntity loginCredentials) {
		employee = loginService.getEmployee(loginCredentials);
		if(employee != null)
			return employee;
		return null;
	}
	

}
