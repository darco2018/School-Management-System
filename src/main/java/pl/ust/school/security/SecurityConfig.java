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
@EnableWebSecurity // The @EnableWebSecurity annotation is crucial if we disable the default security configuration.
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	final @NotNull UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean //some other AuthProviders: RememberMeAuthenticationProvider, OpenIDAuthenticationProvider, TestingAuthenticationProvider, AnonymousAuthenticationProvider,
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {

		authManager.authenticationProvider(authenticationProvider());
		
		
		/* SHORTER SOLUTION: you can remove the line above and authenticationProvider() method
		 * authManager.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());*/
		
		// authManager.jdbcAuthentication()authManager.dataSource(dataSource)
		// .usersByUsernameQuery("select username, password, enabled from users where username=?")
		// .authoritiesByUsernameQuery("select username, user_role from user_roles where username=?");
	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	 // with <form:form> tag and @EnableWebSecurity, the CsrfToken is automatically included for you (using the CsrfRequestDataValueProcessor).
	        http.csrf(); 
	 
	        // Allow access to home/login/signup   
	        http.authorizeRequests().antMatchers("/", 
	        									"/home", 
	        									"/welcome", 
	        									"/login", 
	        									"/logout", 
	        									"/logoutConfirm",
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
	        
	        
	        //http.authorizeRequests().anyRequest().hasRole("DEV");// if i give access to ALL requests before more specific
	        // ones, the rules for more specific ones below will NOT work!
	        
	        http.authorizeRequests().antMatchers("/dev*").hasRole("DEV");
	        http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("DEV","ADMIN");
	        http.authorizeRequests().antMatchers("/schooladmin/**").hasAnyRole("DEV","ADMIN", "SCHOOLADMIN");
	        http.authorizeRequests().antMatchers("/studentuser/**").hasAnyRole("DEV","ADMIN", "SCHOOLADMIN", "STUDENT");
	        http.authorizeRequests().antMatchers("/teacheruser/**").hasAnyRole("DEV","ADMIN", "SCHOOLADMIN", "TEACHER");
	        http.authorizeRequests().antMatchers("/parentuser/**").hasAnyRole("DEV","ADMIN", "SCHOOLADMIN", "PARENT");
	      
	        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
	 
	        // LOGIN FORM
	        http.authorizeRequests()
	        		.and()
	        			.formLogin()//
	        			.loginProcessingUrl("/perform_login") // The default URL where the Spring Login will POST to trigger the authentication process is /login which used to be /j_spring_security_check before Spring Security 4.
	        			.loginPage("/login")// springdefault: spring_security_login
	        			.defaultSuccessUrl("/userInfo")
	        			.failureUrl("/login?error=true")
	        			.usernameParameter("username")
	        			.passwordParameter("password")
	                // LOG OUT
	                .and()
	                	.logout()
	                	.logoutUrl("/logout") // he URL that triggers log out to occur (default is "/logout"). If CSRF protection is enabled (default), then the request must also be a POST.
	                	.logoutSuccessUrl("/logoutSuccessful");
	    }
	 
	 //////////////// EXTRA INFO ///////////////////////////////////////////
     // http.authorizeRequests().anyRequest().hasRole("DEV"); - doesnt work here
    //  http.authorizeRequests().antMatchers("/**").hasRole("DEV"); - doesnt work here
    // http.authorizeRequests().anyRequest().authenticated(); // .antMatchers("/**").fullyAuthenticated()
    /* NIE DZIALA MI
     http.antMatcher("/foo/**") - a request matcher for the whole filter chain
     .authorizeRequests()
       .antMatchers("/foo/bar").hasRole("BAR") - only to choose the access rule to apply.
       .antMatchers("/foo/spam").hasRole("SPAM") - only to choose the access rule to apply.
       .anyRequest().isAuthenticated();
     */

}
