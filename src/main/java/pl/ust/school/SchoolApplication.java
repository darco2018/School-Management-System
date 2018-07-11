package pl.ust.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// @PropertySource("classpath:db.properties")


@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class}) 
// disable security: also comment @EnableWebSecurity and @Configuration. Doesnt work for tests   
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}
	
	/*
	@Autowired
	private Environment env;
	
	Properties props = new Properties();
    
    // Setting JDBC properties
    props.put(DRIVER, env.getProperty("mysql.driver"));*/
}
