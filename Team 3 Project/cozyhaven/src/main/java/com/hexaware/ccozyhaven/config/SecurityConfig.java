package com.hexaware.ccozyhaven.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hexaware.ccozyhaven.filter.JwtAuthFilter;





@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	JwtAuthFilter authFilter;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UsersInfoUserDetailsService();
	}
	
	  @Bean
	    public  SecurityFilterChain   getSecurityFilterChain(HttpSecurity http) throws Exception {
	    	
	    		return http.csrf().disable()
	    			.authorizeHttpRequests().requestMatchers("/api/user/login","/api/user/register","/api/hotelowner/login","/api/hotelowner/register","/api/admin/login","api/admin/register","/swagger-ui/","/swagger-resources/").permitAll()
	    			.and()
	    			.authorizeHttpRequests().requestMatchers("api/admin/**","api/user/**","api/hotelowner/**", "api/room", "api/review", "api/reservation", "api/hotel")
	    			.authenticated().and()   //.formLogin().and().build();
	    			.sessionManagement()
	    			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    			.and()
	    			.authenticationProvider(authenticationProvider())
	    			.addFilterBefore(authFilter	, UsernamePasswordAuthenticationFilter.class)
	    			.build();
	    	
	    }
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
    	return config.getAuthenticationManager();
    }
}
