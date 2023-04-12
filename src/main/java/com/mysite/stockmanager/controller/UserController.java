package com.mysite.stockmanager.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.stockmanager.model.LoginDTO;
import com.mysite.stockmanager.model.User;
import com.mysite.stockmanager.model.UserRepository;
import com.mysite.stockmanager.model.UserRequestDTO;
import com.mysite.stockmanager.model.UserResponseDTO;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserRepository repo;
	
	@PostMapping(path = "/login")
	public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO userDTO) {
		User user = repo.findByEmail(userDTO.email());
		UserResponseDTO response = null;
		if(user.getPassword().equals(user.encrypt(userDTO.password()))) {
			Random random = new Random();
			user.setSessionToken(random.nextInt());
			response = new UserResponseDTO(user.getId(), user.getSessionToken());
		} else {
			return new ResponseEntity<UserResponseDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(path = "/register")
	public String createUser(@RequestBody UserRequestDTO userDTO) {
		
		if(repo.findByEmail(userDTO.email()) == null) {
			User user = new User();
			user.setFullName(userDTO.fullName());
			user.setEmail(userDTO.email());
			user.setPassword(userDTO.password());
			repo.save(user);
			return("New user registered");
		} else {
			return("User already exists");
		}
	}
}
