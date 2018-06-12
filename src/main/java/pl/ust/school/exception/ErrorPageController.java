package pl.ust.school.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorPageController  implements ErrorController {
	
	private static final String VIEW_DEFAULT_ERROR = "/error";
	private static final String MESSAGE_ERROR = "It's our fault, not yours!<br>Application has encountered an error."
			+ "Please contact support at <b>us3cki@zoho.com</b><br>Copy the information below and attach it in your "
			+ "email to help us fix the issue.";
	
	@Override
	public String getErrorPath() {
		return VIEW_DEFAULT_ERROR;
	}

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request, Model model) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("message",MESSAGE_ERROR);
		mav.addObject("exception", request.getAttribute("javax.servlet.error.exception"));
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("url", request.getQueryString());
		mav.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
		mav.setViewName(this.getErrorPath()); 

		return mav;
	}
}
