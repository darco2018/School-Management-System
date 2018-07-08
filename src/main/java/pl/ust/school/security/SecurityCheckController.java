package pl.ust.school.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityCheckController {
	
	@RequestMapping("/dev")
	public String devOnlyAccess() {
		return "security/devPage";
	}
	
	@ResponseBody
	@RequestMapping("/admin/test")
	public String adminAccess() {
		return "Protected page - only for DEV, ADMIN";
	}
	
	@ResponseBody
	@RequestMapping("/studentuser/test")
	public String studentuserAccess() {
		return "Protected page - only for DEV, ADMIN, SCHOOLADMIN, STUDENT";
	}
	
	@ResponseBody
	@RequestMapping("/teacheruser/test")
	public String teacheruserAccess() {
		return "Protected page - only for DEV, ADMIN, SCHOOLADMIN, TEACHER";
	}
	
	@ResponseBody
	@RequestMapping("/parentuser/test")
	public String parentuserAccess() {
		return "Protected page -  only for DEV, ADMIN, SCHOOLADMIN, PARENT";
	}
	
	@ResponseBody
	@GetMapping(value = "/admin")
    public String adminPage(Model model, Principal principal, HttpServletRequest request) {
    	   	                                            // can also cast it into CustomUserDetails
    	org.springframework.security.core.userdetails.User loginedUser = (User) ((Authentication) principal).getPrincipal(); 
        model.addAttribute("userInfo", WebUtils.getAuthoritiesAsString(loginedUser));  // gets GrantedAuthorities by loginedUser.getAuthorities() 
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
       /*NLP if csrf diabled  CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        System.out.println("printing the token X" + csrf.getToken());*/
        
        return auth.getName() + " with authorities: " + auth.getAuthorities() + " uri " + request.getRequestURI();
    }

}
