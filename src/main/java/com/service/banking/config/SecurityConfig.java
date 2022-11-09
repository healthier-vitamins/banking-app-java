package com.service.banking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.service.banking.filter.CustomAuthenticationFilter;
import com.service.banking.filter.CustomAuthorizationFilter;

// to fix deprecated warning
// https://www.youtube.com/watch?v=7HQ-x9aoZx8


@SuppressWarnings("deprecation")
@Configuration 
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
	}

//	Archived
//	@Bean
//	public AuthenticationFailureHandler authenticationFailureHandler() {
//		return new CustomAuthenticationFailureHandler();
//	}
//
//	@Bean
//	public AccessDeniedHandler accessDeniedHandler() {
//		return new CustomAccessDeniedHandler();
//	}
//	
//	@Autowired
//	@Qualifier("delegatedAuthenticationEntryPoint")
//	AuthenticationEntryPoint authEntryPoint;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/token/refresh").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/all").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/user/delete/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/customer/save").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/customer/all").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/user/saved-updated").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
//		http.formLogin().failureHandler(authenticationFailureHandler());
//		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
//		http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
		
		http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.build();
	}

	// cors
	// https://stackoverflow.com/questions/40418441/spring-security-cors-filter
//   @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("*")));
//        configuration.setAllowedMethods(Collections.unmodifiableList(Arrays.asList("HEAD",
//                "GET", "POST", "PUT", "DELETE", "PATCH")));
//        // setAllowCredentials(true) is important, otherwise:
//        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
//        configuration.setAllowCredentials(true);
//        // setAllowedHeaders is important! Without it, OPTIONS preflight request
//        // will fail with 403 Invalid CORS request
//        configuration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList("Authorization", "Cache-Control", "Content-Type")));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
	
}
