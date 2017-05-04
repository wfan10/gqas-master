package com.ford.gqas.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// use x-auth-token header for security.
	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// find the user in question from some list here ???
		auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN", "USER").and()
				.withUser("wfan10").password("password2").roles("USER");
	}

	// ignore security when going to resources.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources");
	}

	// Reference.
	// http://stackoverflow.com/questions/36968963/how-to-configure-cors-in-a-spring-boot-spring-security-application
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS", "DELETE", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "X-Auth-Token", "Content-Type", "Accept", "x-requested-with", "Cache-Control"));
		// This is important to get a session token: response.addHeader("Access-Control-Expose-Headers", "X-Auth-Token");
		configuration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// public void
	/*
	 * Reference:
	 * http://www.sedooe.com/2016/04/rest-authentication-using-spring-security-
	 * and-spring-session/(non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * reference https://github.com/spring-projects/spring-boot/issues/5834
		 * This is replaced. http .addFilterBefore(new WebSecurityCorsFilter(),
		 * ChannelProcessingFilter.class);
		 */
		http
				// by default use a bean by the name of corsConfigurationSource.
				.cors()
				// disable csrf for localhost testing.
				// Reference:
				// http://docs.spring.io/spring-security/site/docs/4.0.x/reference/htmlsingle/#csrf-configure
				.and().csrf().disable()

				.authorizeRequests()
				// Allow cross origin OPTIONS requests.
				// Reference:
				// http://www.sedooe.com/2016/08/cors-and-authentication/
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// authenticate all the rest.
				.anyRequest().authenticated()

				// cache protected pages until login.
				.and().requestCache().requestCache(new NullRequestCache())

				.and().httpBasic()
				// manage sessions: only 1 session per user and migrate session
				// variables.
				// reference: http://www.baeldung.com/spring-security-session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).sessionFixation()
				.migrateSession().maximumSessions(1);

	}
}
