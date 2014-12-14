package org.sidhu.utility.imagehotspot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	/**
	 * Create a global AuthenticationManagerBuilder that can optionally be used for creating an AuthenticationManager that is shared.
	 * @param auth AuthenticationManagerBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	/**
	 * Configures Spring Web Security.
	 * @param http HttpSecurity Object.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/register").permitAll()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").and()
				.formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/admin/main")
				.usernameParameter("username").passwordParameter("password")
				.permitAll().and().logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login?logout").and().csrf()
				.and().exceptionHandling().accessDeniedPage("/403");
	}
/**
 * Create a PasswordEncoder Bean.
 * @return PasswordEncoder Object.
 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}