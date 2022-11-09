package com.service.banking.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {
	
	// handle all 500 internal server error 
//	https://stackoverflow.com/questions/48508285/how-to-handle-internal-server-error-500-on-spring-rest-api
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception ex, HttpServletResponse response, HttpServletRequest request) {
		if(ex instanceof NullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// only works for exceptions thrown by controllers.
		// not spring security.
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getClass().getName());
//		error.put("error_cause", ex.getCause().toString());
		error.put("error_message", ex.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		
//		try {
//			
//		} catch(Exception e) {
//			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
}
