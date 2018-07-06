package pl.ust.school.security;

import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration             
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	final @NotNull UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	//@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {

		authManager.authenticationProvider(authenticationProvider());
		
		/* SHORTER SOLUTION: you can remove the line above and authenticationProvider() method
		 * authManager.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());*/
		
		// .usersByUsernameQuery("select username, password, enabled from users where username=?")
		// .authoritiesByUsernameQuery("select username, user_role from user_roles where username=?");
	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	 
	        http.csrf().disable();
	 
	        // Allow access to home/login/signup   
	        http.authorizeRequests().antMatchers("/", 
	        									"/home", 
	        									"/welcome", 
	        									"/login", 
	        									"/logout", 
	        									"/logoutSuccessful",
	        									"/signup").permitAll();
	        
	        //TODO remove when done - access to HomeController handlers for testing only
	        http.authorizeRequests().antMatchers( 
												"/locale", 
												"/helloworld", 
												"/ex").permitAll();
	        
	        // Allow access to static resources
	        http.authorizeRequests().antMatchers("/css/**", 
	        									"/images/**", 
	        									"/js/**", 
	        									"/**/favicon.ico",
	        									"/webjars/**"
	        									).permitAll();
	        
	        /////////////////////////////// ?! /////////////////////////////////////////////

          //  .antMatchers("/**").fullyAuthenticated()
           // .anyRequest().authenticated();
	        
	        ////////////////////////  access for authorities ///////////////////////////////////////////
	        
	        // .antMatchers("/**").hasRole("ADMIN")
	        
	        // /userInfo page requires login as ROLE_STUDENT or ROLE_ADMIN.
	        // If no login, it will redirect to /login page.
	        http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN')");
	 
	        // For ADMIN only.
	        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
	        
	        http.authorizeRequests().antMatchers("/**").access("hasRole('ROLE_ADMIN')");
	 
	        // When the user has logged in as XX. But access a page that requires role YY,
	        // AccessDeniedException will be thrown.
	        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
	 
	        //////////////////////////////// Config for Login Form ////////////////////////////////////////////
	        http.authorizeRequests()
	        		.and()
	        			.formLogin()//
	        			.loginProcessingUrl("/j_spring_security_check") 
	        			.loginPage("/login")//
	        			.defaultSuccessUrl("/userInfo")
	        			.failureUrl("/login?error=true")
	        			.usernameParameter("username")
	        			.passwordParameter("password")
	                // Config for Logout Page
	                .and()
	                	.logout()
	                	.logoutUrl("/logout")
	                	.logoutSuccessUrl("/logoutSuccessful");
	    }

}
