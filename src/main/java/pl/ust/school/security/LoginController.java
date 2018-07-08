package pl.ust.school.security;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.security.CustomUserDetailsService.CustomUserDetails;


@Controller
public class LoginController {
 
	@GetMapping(value = "/login")
    public String loginPage(Model model, @RequestParam(required=false) String error, HttpSession session) {
    	
    	if(error != null && session != null) {
    			Exception lastExc =  (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
    			if(lastExc != null) {
    				model.addAttribute("invalidLogin", lastExc.getMessage());
    			}
    	}
 
        return "security/loginPage";
    }
	
	@GetMapping(value = "/logoutConfirm")
    public String confirmLogoutPage() {
        return "security/logoutConfirmPage";
    }
	
    
	@GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage() {
        return "security/logoutSuccessfulPage";
    }
    
	@GetMapping(value = "/loginSuccessful")
    public String userInfo(Model model, Principal principal) {    
        
		////////////////////for testing only //////////////////////////  //TODO remove
		if (principal != null) {
			
			// String userName = principal.getName(); //the only info you can get from
			// Principal about UserDetails. More can be get from Authentication obj
			Authentication auth = (Authentication) principal;
			CustomUserDetails loginedUser = (CustomUserDetails) auth.getPrincipal();
			String userInfo = getUserDetailsInfo(loginedUser);

			userInfo += "<br>, using WebUtils.getAuthoritiesAsString(loginedUser)"
					+ WebUtils.getAuthoritiesAsString(loginedUser);
			model.addAttribute("userInfo", userInfo);
			
		//////////////////// END for testing only //////////////////////////  
		}
 
        return "security/loginSuccessfulPage";  
    }

	@GetMapping(value = "/403") //you get forwarded here when you dont have the right authority
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
        	/* FOR TESTING ONLY
        	 Authentication auth = (Authentication) principal;
			CustomUserDetails loginedUser = (CustomUserDetails) auth.getPrincipal();
			String userInfo = getUserDetailsInfo(loginedUser);
            model.addAttribute("userInfo", userInfo);*/
 
            String message = "Hi " + principal.getName() + "<br>You do not have permission to access this page.";
            model.addAttribute("message", message);
     }
 
        return "security/403Page";
    }
    
	
	
	// for testing only
    private String getUserDetailsInfo(UserDetails loginedUser) {
		String registrationNum = "";
		if(loginedUser instanceof CustomUserDetails) {
			registrationNum = ((CustomUserDetails)loginedUser).getRegistrationNum();
		}
		
		return  loginedUser.getUsername() 
        		+ ",password: " + loginedUser.getPassword() 
        		+ ", authorities by userName: " + loginedUser.getAuthorities() 
        	    + ", enabled: " + loginedUser.isEnabled() 
        	    + ", account nonexpired: " + loginedUser.isAccountNonExpired()
        	    + ", registrationNum: " + registrationNum; 
	}
 
}