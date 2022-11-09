package com.service.banking.filter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.format.datetime.standard.InstantFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
			// pass to next filter if login.
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					Stream.of(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
//					JWTVerifier
				} catch (IllegalArgumentException e) {
					// replace with custome exceptionhandler too
//					e.printStackTrace();
					Map<String, String> errors = new HashMap<>();
					errors.put("error_message", e.getMessage());
					response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				} catch (JWTVerificationException e) {

					// HTTP ERROR RESPONSE
//					throw new JWTVerificationException(e.getLocalizedMessage(), e.getCause());

					e.printStackTrace();
					Map<String, String> errors = new HashMap<>();
					errors.put("error", e.getClass().getName());
//					errors.put("error_cause", e.getCause().toString());
					errors.put("error_message", e.getMessage());
					response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
//					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), errors);

				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
