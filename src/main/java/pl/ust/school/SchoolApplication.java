package pl.ust.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import pl.ust.school.security.DatabaseSecurityConfig;

@SpringBootApplication  
@Import(DatabaseSecurityConfig.class)
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}
}
