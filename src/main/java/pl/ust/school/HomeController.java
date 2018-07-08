package pl.ust.school;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/") // otherwise will map to resources/index.html, not WEB-INF/index.jsp 
	public String goToIndex() {
		return "index";
	}  
	
	@RequestMapping("/locale")
	@ResponseBody
    public String home(Locale locale) {
        return locale.toString();
    }
	
	@RequestMapping("/helloworld")
	@ResponseBody
    public String helloworld() {
        return "Hello World";
    }
	
	@RequestMapping("/ex")
	@ResponseBody
    public void throwException() {
		throw new RuntimeException("test exception");
    }

}
