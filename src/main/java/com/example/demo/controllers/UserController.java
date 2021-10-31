package com.example.demo.controllers;

import java.util.Optional;

import com.example.demo.ResourceNotFound;
import com.example.demo.models.User;
import com.example.demo.models.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

    @GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public String employeeAccess() {
		return "Employee Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/user-info")
	@PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<User> getUserInfo(@RequestParam(value="username")String username) throws ResourceNotFound {
		Optional<User> user = userRepository.findByUsername(username);

		return ResponseEntity.ok().body(user.get());
	}

	@PutMapping("/user-info")
	@PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<User> updateUserInfo(@RequestParam(value="username")String username, @RequestBody UserInfo userInfo) throws ResourceNotFound {
		Optional<User> user = userRepository.findByUsername(username);

		if (user.isPresent()) {
			User _user = user.get();
			_user.getUser_info().setAvatar(userInfo.getAvatar());
			_user.getUser_info().setName(userInfo.getName());
			_user.getUser_info().setTelephone(userInfo.getTelephone());
			_user.getUser_info().setGender(userInfo.getGender());
			return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
