package pl.ust.school.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import pl.ust.school.system.AppConstants;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(GlobalDefaultExceptionHandler.class);
	
	private static final String MESSAGE_ERROR = "It's our fault, not yours!<br>Application has encountered an unexpected condition."
			+ "Please contact support at <b>us3cki@zoho.com</b><br>Support may ask you to right click to view page source.";
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception exception)  {

		logger.error("Request: " + req.getRequestURI() + " raised " + exception);

		Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", MESSAGE_ERROR);
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("status", statusCode);

		// exception details - hidden from user - will be available in the .JSP source code
		mav.setViewName(AppConstants.VIEW_SUPPORT); // change to "/errorDetails" if you want to see stacktrace on page 
									// rather than in source code of the genarated html page
		return mav;
	}
	
}
