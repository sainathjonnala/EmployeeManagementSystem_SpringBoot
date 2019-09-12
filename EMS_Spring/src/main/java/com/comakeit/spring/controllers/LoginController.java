package com.comakeit.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.spring.constants.Constant;
import com.comakeit.spring.entities.EmployeeEntity;
import com.comakeit.spring.entities.LoginEntity;

@Controller
@SessionAttributes({ "loginCredentials", "employee" })
@RequestMapping
public class LoginController {

	LoginEntity loginCredentials;
	EmployeeEntity employee;
	ModelAndView modelView;
	RestTemplate rest;

	@RequestMapping("/index")
	public ModelAndView displayHomePage() {
		modelView = new ModelAndView("index.jsp");
		return modelView;
	}

	@RequestMapping("/loginValidation")
	@PostMapping
	public ModelAndView validate(LoginEntity loginCredentials) {
		rest = new RestTemplate();

		loginCredentials = rest.postForObject(Constant.url + "/EMS/login", loginCredentials, LoginEntity.class);

		if (loginCredentials != null) {
			String role = loginCredentials.getRole().getRole_name();

			if (role.equals("admin")) {
				modelView = new ModelAndView("forward:AdminHomePage.jsp");
				modelView.addObject("loginCredentials", loginCredentials);
				return modelView;
			} else {
				employee = rest.postForObject(Constant.url + "/EMS/user", loginCredentials, EmployeeEntity.class);
				modelView = new ModelAndView("forward:EmployeeHomePage.jsp");
				modelView.addObject("employee", employee);
				return modelView;
			}
		}
		return new ModelAndView("forward:index.jsp?validation=invalid");
	}
}
