package pl.ust.school.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.ust.school.user.Role;


@Configuration
@EnableWebSecurity // enables httpbasic and form authentication, renders login page automatically
// remember to Import this class in AppConfig.java
public class DatabaseSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception{
		
		authenticationMgr
			.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			//.withUser("darek").password("darek").authorities("DEV").and() - will work if email is nullable and default names for users and authorities tables
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, user_role from user_roles where username=?");
		/* the two lines above necessary when changing default table values
		    RENAME TABLE authorities TO user_roles;
			ALTER TABLE user_roles CHANGE authority user_role varchar(255);*/
	}
			
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		final String developerPath = "dev";
		final String adminPath = "admin";
		final String schoolAdminPath = "schooladmin";
		final String teacherPath = "teacheruser";
		final String studentPath = "studentuser";
		final String parentPath = "parentuser";
		
		http
		.authorizeRequests()
			.antMatchers("/", "**/static/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login").permitAll()
			.and()
		.logout().permitAll();
			
	}

}
