package com.service.banking.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.banking.model.EditUser;
import com.service.banking.model.Role;
import com.service.banking.model.User;
import com.service.banking.repo.UserRepo;
import com.service.banking.service.UserService;
import com.service.banking.utility.DateFormatterUtil;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/user/all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/user/get/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(userService.getUserById(id)); 
	}
	
	@GetMapping("/user/get-username/{username}")
	public ResponseEntity<User> getByUsername(@PathVariable String username) {
		return ResponseEntity.ok().body(userService.findByUsername(username)); 
	}

//	@PostMapping("/user/save")
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, path = "/user/save")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
//		ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//		String currentDateInMilliS = DateFormatterUtil.currentDateInMilliS();
//		System.out.println("[TERMINAL] -- " + currentDateInMilliS + " --");
//		user.getCustomer().getBankAcc().setAccCreationDate(new Date(System.currentTimeMillis()).toString());
		if(user.getCustomer().getBankAcc().getAccCreationDate() == null || user.getCustomer().getBankAcc().getAccCreationDate() == "") {
			user.getCustomer().getBankAcc().setAccCreationDate(DateFormatterUtil.currentDateInString());
		}
		return ResponseEntity.ok().body(userService.saveUser(user));
	}
	
	@PutMapping("/user/save-updated")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<User> saveUpdatedUser(@RequestBody User user) {
		return ResponseEntity.ok().body(userService.saveUpdatedUser(user));
	}

	@DeleteMapping("/user/delete/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void deleteById(@PathVariable Long id) {
		userService.deleteById(id);
	}
	
	@PostMapping("/user/change-password")
	public ResponseEntity<Object> editUser(@RequestBody EditUser editUserCreds) {
		User user;
		user = userRepo.findByUsername(editUserCreds.getUsername());
		if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
//		String encodedOldPassword = passwordEncoder.encode(editUserCreds.getOldPassword());
		if(passwordEncoder.matches(editUserCreds.getOldPassword(), user.getPassword())) {
			user.setPassword(editUserCreds.getNewPassword());
			userService.saveUser(user);
			System.out.println("[TERMINAL] -- Password changed for " + user.getUsername() + " --");
			return ResponseEntity.ok().body(user);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Old password does not match");
		}
	}
	
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.getUser(username);
				String access_token = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
//				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				e.printStackTrace();
				response.setHeader("error", e.getMessage());
				response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
//				response.sendError(org.springframework.http.HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
//				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
	
	// for commandLineRunner
	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		// to send back 201 instead of 200 to be precise since saving an item in db
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
//		return ResponseEntity.created(uri).body(userService.saveRole(role ));
		return ResponseEntity.ok().body(userService.saveRole(role));
	}

	@PostMapping("/role/attacher")
	public ResponseEntity<?> addRoleToUser(@RequestBody String username, String role) {
		userService.addRoleToUser(username, role);
		return ResponseEntity.ok().build();
	}
}
