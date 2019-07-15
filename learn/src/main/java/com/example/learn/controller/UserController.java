package com.example.learn.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learn.metaData.ResponseBody;
import com.example.learn.model.ApplicationUser;
import com.example.learn.sevices.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	// will save in encoded password
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/sign-up")
	public ResponseEntity<ResponseBody<ApplicationUser>> signUp(
			@Valid @RequestBody(required = true) ApplicationUser u) {

		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		ResponseBody<ApplicationUser> responseBody = userService.createUser(u);

		return new ResponseEntity<>(responseBody, HttpStatus.OK);

	}

	@GetMapping("/user")
	public ResponseEntity<ApplicationUser> getUser(@RequestParam("username") String username) {
		ApplicationUser user = userService.getUser(username);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@DeleteMapping("/user/delete/{username}")
	public ResponseEntity<ResponseBody<String>> deleteUser(@PathVariable String username) {
		ResponseBody<String> b = userService.deleteUser(username);
		return new ResponseEntity<>(b, HttpStatus.OK);

	}

	@PutMapping("/user/update/{username}")
	public ResponseEntity<ApplicationUser> updateUser(@PathVariable String username,
			@RequestBody ApplicationUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		ApplicationUser appUser = userService.updateUser(user);
		return new ResponseEntity<>(appUser, HttpStatus.OK);

	}

}
