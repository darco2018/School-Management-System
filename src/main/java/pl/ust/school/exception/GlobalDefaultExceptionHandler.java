package pl.ust.school.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(GlobalDefaultExceptionHandler.class);
	
	private static final String MESSAGE_ERROR = "It's our fault, not yours!<br>Application has encountered an error."
			+ "Please contact support at <b>us3cki@zoho.com</b><br>Support may ask you to right click to view page source.";
	
	public static final String VIEW_SUPPORT = "/support";
	
	
	
	 @ExceptionHandler(value = Exception.class)
	  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {
		 
		 logger.error("Request: " + req.getRequestURI() + " raised " + exception);
	    
		 // Rethrow @ResponseStatus-annotated exceptions or they will be processed here instead.
	    if ( AnnotationUtils.findAnnotation (exception.getClass(), ResponseStatus.class) != null)
	      throw exception;

	    // Otherwise setup and send the user to a default error/support-view.
	    Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
	    
		ModelAndView mav = new ModelAndView();
		mav.addObject("message",MESSAGE_ERROR);
		mav.addObject("timestamp", new Date().toString());
	    mav.addObject("exception", exception); // info will be hidden in th source code of JSP
	    mav.addObject("url", req.getRequestURL());
		mav.addObject("status", statusCode);
		
	    mav.setViewName(VIEW_SUPPORT);
	    return mav;
	  }
	 
	 
	 /*
	  @ExceptionHandler({SQLException.class, DataAccessException.class})
	  public String databaseError(HttpServletRequest req, Exception ex) {
	   
	    // Note that the exception is NOT available to this view (it is not added
	    // to the model) but see "Extending ExceptionHandlerExceptionResolver" below.

		  logger.error("Request: " + req.getRequestURL() + " raised " + ex);
	    return "databaseError";
	  }  */
	 
	 /*
	 	// Convert a predefined exception to an HTTP Status code 
		@ResponseStatus(HttpStatus.CONFLICT)  // 409
	    @ExceptionHandler(DataIntegrityViolationException.class)
	    public void handleConflict(HttpServletRequest req, Exception ex) {
			 logger.error("Request: " + req.getRequestURL() + " raised " + ex);
	    }*/

}
