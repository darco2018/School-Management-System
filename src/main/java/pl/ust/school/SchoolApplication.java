package pl.ust.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

import pl.ust.school.security.SecurityConfig;

// @PropertySource("classpath:db.properties")


@SpringBootApplication // (exclude = { SecurityAutoConfiguration.class })
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
