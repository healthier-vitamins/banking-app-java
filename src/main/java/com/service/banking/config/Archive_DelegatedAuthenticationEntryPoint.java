package com.service.banking.config;
//package com.service.banking.config;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//// https://www.baeldung.com/spring-security-exceptionhandler
//
//@Component("delegatedAuthenticationEntryPoint")
//public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {
//	
//	@Autowired
//	@Qualifier("handlerExceptionResolver")
//	private HandlerExceptionResolver resolver;
////	to inject the DefaultHandlerExceptionResolver
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws IOException, ServletException {
//		// delegate handler to this resolver. 
//		// to allow security exceptions to be handled with controller advice and exception handler methods.
//		resolver.resolveException(request, response, null, authException);
//	}
//	
//	
//
//}
