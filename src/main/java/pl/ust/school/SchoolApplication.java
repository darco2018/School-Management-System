package pl.ust.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ImportResource("classpath:mvc-configuration.xml") mport Spring XML configuration file(s)
@SpringBootApplication   //
//import for security
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}
}
