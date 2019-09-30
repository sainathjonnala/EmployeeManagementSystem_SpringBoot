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
		modelView = new ModelAndView();
		modelView.setViewName("index.jsp");
		return modelView;
	}

	@RequestMapping("/loginValidation")
	@PostMapping
	public ModelAndView validate(LoginEntity loginCredentials) {
		rest = new RestTemplate();
		modelView = new ModelAndView();

		try {

			loginCredentials = rest.postForObject(Constant.url + "/EMS/login", loginCredentials, LoginEntity.class);

			String role = loginCredentials.getRole().getRole_name();

			if (role.equals("admin")) {
				modelView.setViewName("AdminHomePage.jsp");
				modelView.addObject("loginCredentials", loginCredentials);
			} else {
				employee = rest.getForObject(Constant.url + "/EMS/user/" + loginCredentials.getUsername(), EmployeeEntity.class);
				modelView.setViewName("EmployeeHomePage.jsp");
				modelView.addObject("employee", employee);
			}
		} catch (Exception e) {
			modelView.setViewName("index.jsp?validation=invalid");
		}
		return modelView;
	}
}
