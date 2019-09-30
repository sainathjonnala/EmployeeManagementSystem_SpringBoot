package com.comakeit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LoginEntity;
import com.comakeit.spring.repositories.LoginRepository;

@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;

	public LoginEntity findUser(LoginEntity loginCredentials) {
		String password = loginCredentials.getPassword();
		if (loginRepository.existsById(loginCredentials.getUsername())) {
			loginCredentials = loginRepository.findById(loginCredentials.getUsername()).get();
			if (password.equals(loginCredentials.getPassword())) {
				return loginCredentials;
			}
		}
		return null;
	}

	public EmployeeEntity getEmployee(String username) {
		return loginRepository.getEmployee(username);
	}

}
