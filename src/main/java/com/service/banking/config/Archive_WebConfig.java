package com.service.banking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableWebMvc
//@SuppressWarnings("deprecation")
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {

	// cors
	// https://stackoverflow.com/questions/35091524/spring-cors-no-access-control-allow-origin-header-is-present
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("http://localhost:4200");
//	}

	// this works too
//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "DELETE", "PUT")
//        .allowedHeaders("*")
//        .allowedOriginPatterns("*")
//        .allowCredentials(true).maxAge(3600);
//    }
	
//	.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Access-Control-Allow-Origin")
//	"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//    "Access-Control-Request-Headers", "Access-Control-Allow-Origin"
	
//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//    }
	
//}
