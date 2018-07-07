package pl.ust.school.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.security.CustomUserDetailsService.CustomUserDetails;


@Controller
public class SecurityController {
	
	@GetMapping("/") // bez tego mapuje do index.html w resources
	public String goToIndex() {
		return "index";
	}  

	@GetMapping(value = { "/welcome"}) 
	public String welcomePage(Model model) {
		model.addAttribute("message", "This is welcome page!");
		return "security/welcomePage";
	}
	

	@GetMapping(value = "/admin")
    public String adminPage(Model model, Principal principal, HttpServletRequest request) {
    	   	                                            // can also cast it into CustomUserDetails
    	org.springframework.security.core.userdetails.User loginedUser = (User) ((Authentication) principal).getPrincipal(); 
        model.addAttribute("userInfo", WebUtils.getAuthoritiesAsString(loginedUser));  // gets GrantedAuthorities by loginedUser.getAuthorities() 
         
        
        // TODO remove when not needed
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("uri", request.getRequestURI())
        .addAttribute("user", auth.getName())
        .addAttribute("roles", auth.getAuthorities());
        
       /*NLP if csrf diabled  CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        System.out.println("printing the token X" + csrf.getToken());*/
        
        return "security/adminPage";
    }
 
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
	/*
	@GetMapping(value = "/logout")
    public String logout() {
        return "forward:/logout";
    }*/
    
	@GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout"); //TODO add logout only when logined User
        return "security/logoutSuccessfulPage";
    }
    
	@GetMapping(value = "/userInfo")
    public String userInfo(Model model, Principal principal) {    
        
		if (principal != null) {
			// String userName = principal.getName(); //the only info you can get from
			// Principal about UserDetails. More can be get from Authentication obj
			Authentication auth = (Authentication) principal;
			CustomUserDetails loginedUser = (CustomUserDetails) auth.getPrincipal();
			String userInfo = getUserDetailsInfo(loginedUser);

			userInfo += "<br>, using WebUtils.getAuthoritiesAsString(loginedUser)"
					+ WebUtils.getAuthoritiesAsString(loginedUser);
			model.addAttribute("userInfo", userInfo);
		}
 
        return "security/userInfoPage";  
    }

	@GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
        	Authentication auth = (Authentication) principal;
			CustomUserDetails loginedUser = (CustomUserDetails) auth.getPrincipal();
			String userInfo = getUserDetailsInfo(loginedUser);
            model.addAttribute("userInfo", userInfo);
 
            String message = "Message passed from controller for /403: <br>"
            		+ " Hi " + principal.getName() + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
            
            model.addAttribute("devInfo", "wszedlem jako zwykly user na http://localhost:8080/admin  i zostalem forwarded tu, bo w pasku adresu jest nadal:\n" + 
            		"    // http://localhost:8080/admin !            Ale moge tez od razu wejść na http://localhost:8080/403");
        }
 
        return "security/403Page";
    }
    
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