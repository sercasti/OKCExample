package com.example.okc.auth.rest;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.okc.auth.model.User;
import com.example.okc.auth.service.SecurityService;
import com.example.okc.auth.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@PostMapping(value = "/registration")
	@ResponseBody
	public ResponseEntity<Object> registration(@Valid User userForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			return new ResponseEntity<>(errors, HttpStatus.OK);
		}

		if (userService.findByUsername(userForm.getUsername()) != null) {
			return new ResponseEntity<>(Collections.singletonList("Duplicate.userForm.username"), HttpStatus.CONFLICT);
		}

		if (!userForm.getPasswordConfirm().equals(userForm.getPassword())) {
			return new ResponseEntity<>(Collections.singletonList("Diff.userForm.passwordConfirm"),
					HttpStatus.CONFLICT);
		}

		userService.save(userForm);
		securityService.autologin(userForm.getUsername(), userForm.getPassword());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/mod")
	public String getMessageOfTheDay(Principal principal) {
		return "The message of the day is boring for user: " + principal.getName();
	}
}
