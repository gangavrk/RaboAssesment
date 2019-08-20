package com.rabo.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth)
			throws Exception {

		auth.jdbcAuthentication()
				.dataSource(jdbcTemplate.getDataSource())
				.usersByUsernameQuery(
						"select username, password, enabled"
								+ " from users where username=?")
				.authoritiesByUsernameQuery(
						"select username, authority "
								+ "from authorities where username=?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
				.antMatchers("/person/getPerson/**").permitAll()
				.antMatchers("/person/addPerson/**").permitAll()
				.antMatchers("/actuator/**").hasRole("METRICS").anyRequest().authenticated()
				.antMatchers("/person/updatePerson/**").hasRole("ADMIN").anyRequest()
				.authenticated();
		http.csrf().disable();
	}

	@Bean
	@Override
	public JdbcUserDetailsManager userDetailsService() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
		manager.setJdbcTemplate(jdbcTemplate);
		return manager;
	}

}
