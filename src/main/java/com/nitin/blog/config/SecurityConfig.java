package com.nitin.blog.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.nitin.blog.security.CustomUserDetailService;
import com.nitin.blog.security.JwtAuthenticationEntryPoint;
import com.nitin.blog.security.JwtAuthenticationFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.authentication.AuthenticationManager;
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig{
	
	public static final String[] PUBLIC_URLS= {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	 
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	
	
//	.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
  //  .requestMatchers(new AntPathRequestMatcher("/v3/api-docs")).permitAll()
	
	    @Bean   
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable().authorizeHttpRequests()
	        .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/*")).permitAll()
	        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
	        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs")).permitAll()
	        //.requestMatchers(new AntPathRequestMatcher("/v2/api-docs")).permitAll()
	        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
	                .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
	        .requestMatchers(HttpMethod.GET)
	         .permitAll()
	        .anyRequest().authenticated().and().exceptionHandling()
	        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
	        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        
	        http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
	          http.authenticationProvider(daoAuthenticationProvider());
	        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
	        return defaultSecurityFilterChain;
	    }
	 
//	 public void filterChain(AuthenticationManagerBuilder auth) throws Exception
//	 {
//		 auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
////		 return auth.
//	 }
	    
	    @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider()
	    {
	    	DaoAuthenticationProvider povider = new DaoAuthenticationProvider();
	    	povider.setUserDetailsService(this.customUserDetailService);
	    	povider.setPasswordEncoder(passwordEncoder());
	    	return povider;
	    }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder()
	 {
	return new BCryptPasswordEncoder();	 
	 }
	 

	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	 return authenticationConfiguration.getAuthenticationManager();
	 }
	 

	}
