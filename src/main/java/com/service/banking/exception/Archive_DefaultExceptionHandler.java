package com.service.banking.exception;
//package com.service.banking.exception;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
//	
//	// https://www.baeldung.com/spring-security-exceptionhandler
//	// linked with delegatedAuthenticationEntryPoint
//	
//	@ExceptionHandler({ AuthenticationException.class })
//	@ResponseBody
//	public ResponseEntity<Map> handleAuthenticationException(Exception ex) {
//		Map<String, String> error = new HashMap<>();
//		error.put("error", ex.getClass().getName());
////		error.put("error_cause", ex.getCause().toString());
//		error.put("error_message", ex.getLocalizedMessage());
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	}
//}
