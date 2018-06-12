package pl.ust.school.system;

import java.util.Date;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ResponseDataControllerAdvice {
	
	@ModelAttribute("timestamp")
	public String getTimestamp() {
		return new Date().toString();
	}

}
