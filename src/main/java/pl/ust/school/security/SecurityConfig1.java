package pl.ust.school.security;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {

		authManager.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());
		
		// .usersByUsernameQuery("select username, password, enabled from users where username=?")
		// .authoritiesByUsernameQuery("select username, user_role from user_roles where username=?");
	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	 
	        http.csrf().disable();
	 
	        // The pages does not require login
	       // http.authorizeRequests().antMatchers("/**").permitAll();
	        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
	 
	      
	        // /userInfo page requires login as ROLE_STUDENT or ROLE_ADMIN.
	        // If no login, it will redirect to /login page.
	        http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN')");
	 
	        // For ADMIN only.
	        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
	 
	        // When the user has logged in as XX.
	        // But access a page that requires role YY,
	        // AccessDeniedException will be thrown.
	        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
	 
	        // Config for Login Form
	        http.authorizeRequests().and().formLogin()//
	                // Submit URL of login page.
	                .loginProcessingUrl("/j_spring_security_check") // Submit URL
	                .loginPage("/login")//
	                .defaultSuccessUrl("/userInfo")// nie ma tekiego URL w kontrolerze
	                .failureUrl("/login?error=true")//
	                .usernameParameter("username")//
	                .passwordParameter("password")
	                // Config for Logout Page
	                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
	        
	        
	 
	    }

}
