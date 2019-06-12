package com.testyantra.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource ds;
/**
 * for configuring authentication with db.
 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(ds)
				.usersByUsernameQuery("select name,password,enabled from emp where NAME=?")
				.authoritiesByUsernameQuery("SELECT NAME,ROLE FROM  USER_ROLES WHERE NAME=?");

	}// method configure(-)
/**
 *for configuring all the view pages
 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/empform", "/viewemp").hasAuthority("admin").antMatchers("/success")
				.hasAnyAuthority("normal", "admin").antMatchers("/deleteemp", "/editsave", "/editemp")
				.hasAuthority("admin").antMatchers("/empform").permitAll().and().formLogin()
				.defaultSuccessUrl("/success").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and().exceptionHandling().accessDeniedPage("/logout");
		/*
		 * http.authorizeRequests().antMatchers("/success").access("permitAll")
		 * .antMatchers("/viewemp.jsp").access("hasAnyRole('admin')").and()
		 * .formLogin(). and(). exceptionHandling().accessDeniedPage("/access_fail.jsp")
		 * .and() .logout().logoutSuccessUrl("/success.jsp");
		 * 
		 * System.out.println("insode web configure=-=-=-=-=-=-=-=-=-=-=-=-=-");
		 */

		/*
		 * http.authorizeRequests().antMatchers("/empform",
		 * "/viewemp").hasAnyAuthority("admin") .antMatchers("/deleteemp", "/editsave",
		 * "/editemp").hasAuthority("admin")
		 * 
		 * .antMatchers("/login/*").permitAll().anyRequest().authenticated().and().
		 * formLogin() .defaultSuccessUrl("/success").and().logout()
		 * .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout")).and().exceptionHandling()
		 * .accessDeniedPage("/logout");
		 */

		/*
		 * http.authorizeRequests()
		 * .antMatchers("/viewemp").access("hasRole('admin')").antMatchers("/success").
		 * access("hasRole('normal')").and()
		 * .formLogin().loginPage("/login").failureUrl("/login?error")
		 * .usernameParameter("username").passwordParameter("password") .and()
		 * .logout().logoutSuccessUrl("/login") .and()
		 * .exceptionHandling().accessDeniedPage("/access_fail.jsp");
		 */

		/*
		 * http.authorizeRequests().antMatchers("/viewemp").access("hasRole('admin')").
		 * antMatchers("/success").access("hasRole('normal')").and().formLogin().
		 * loginPage("/success") .failureForwardUrl("/access_fail").and()
		 * .exceptionHandling().accessDeniedPage("/access_fail").and();
		 */

		/*
		 * http.authorizeRequests() .antMatchers("/login").permitAll()
		 * .antMatchers("/viewemp").hasRole("admin")
		 * .antMatchers("/**").hasAnyRole("admin") .and().formLogin()
		 * .and().logout().logoutSuccessUrl("/login").permitAll()
		 * .and().csrf().disable();
		 */
	}//method configure(-)
}//class WebSecurityConfig end
