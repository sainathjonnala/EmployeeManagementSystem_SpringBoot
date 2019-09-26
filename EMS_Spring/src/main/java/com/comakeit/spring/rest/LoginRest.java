package com.comakeit.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LoginEntity;
import com.comakeit.spring.exceptions.InvalidLoginException;
import com.comakeit.spring.services.LoginService;

@RestController
@RequestMapping("EMS")
public class LoginRest {

	@Autowired
	private LoginService loginService;

	@RequestMapping("/login")
	@PostMapping
	public LoginEntity isValidUser(@RequestBody LoginEntity loginCredentials) throws RuntimeException {
		loginCredentials = loginService.findUser(loginCredentials);
		if (loginCredentials == null) {
			throw new InvalidLoginException("Invalid Credentials");
		}
		return loginCredentials;
	}

	@RequestMapping("/user")
	@PostMapping
	public EmployeeEntity findUser(@RequestBody LoginEntity loginCredentials) {
		return loginService.getEmployee(loginCredentials);
	}

}
