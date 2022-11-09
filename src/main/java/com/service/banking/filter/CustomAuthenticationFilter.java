package com.service.banking.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.banking.model.LoginRequest;
import com.service.banking.service.UserService;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		// extract json from request
		
		// https://www.appsdeveloperblog.com/read-body-from-httpservletrequest-in-spring-filter/
		// convert the request to bytes
//		try {
//			byte[] inputStreamBytes = org.springframework.util.StreamUtils.copyToByteArray(request.getInputStream());
//			Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
//			LoginRequest loginCreds = new ObjectMapper().readValue(jsonRequest.get("body"), LoginRequest.class);
//			
//			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCreds.getUsername(), loginCreds.getPassword(), new ArrayList<>()));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
		
//		https://bitcoden.com/answers/get-the-post-request-body-from-httpservletrequest
//		StringBuffer sb = new StringBuffer();
//		BufferedReader reader = null;
//		String content = "";
//		try {
//			reader = request.getReader();
//			while((content = reader.readLine()) != null) sb.append(content); 
//		} catch(Exception e) {
//			e.printStackTrace();
//			response.setHeader("error", e.getMessage());
//			response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
//		}
		
		// https://stackoverflow.com/questions/10308452/how-to-convert-the-following-json-string-to-java-object
		LoginRequest loginCred = new LoginRequest();
		if("POST".equalsIgnoreCase(request.getMethod())) {
			try {
				String stringObj = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> map = mapper.readValue(stringObj, Map.class);
//				System.out.println(map.get("username"));
				loginCred.setUsername(map.get("username"));
				loginCred.setPassword(map.get("password"));
			} catch (IOException e) {
				// replace with custom handlerexception
				e.printStackTrace();
//				response.setHeader("error", e.getMessage());
//				response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
			}
		}
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCred.getUsername(), loginCred.getPassword()));
		
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		// gets the logged in user
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refresh_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
//		response.setHeader("access_token", access_token);
//		response.setHeader("refresh_token", refresh_token);
//		Long userId = userService.getUser(user.getUsername()).getId();
//		System.out.println("-----------------------------------------------");
//		System.out.println(user.getUsername());
//		System.out.println(userId);
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
//		tokens.put("user", "1");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	}
	
}
