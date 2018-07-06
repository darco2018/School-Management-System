package pl.ust.school.security;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
//@RequestMapping("/")
public class SecurityController1 {
	
	@RequestMapping("/") // bez tego mapuje do index.html w resources
	public String goToIndex() {
		return "index";
	}  

	@RequestMapping(value = { "/welcome"}, method = RequestMethod.GET) 
	public String welcomePage(Model model) {
		model.addAttribute("message", "This is welcome page!");
		return "security/welcomePage";
	}
	

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
    	   	
    	 org.springframework.security.core.userdetails.User loginedUser = (User) ((Authentication) principal).getPrincipal(); 
 
        String userInfo = WebUtils.getAuthoritiesAsString(loginedUser); // produces GrantedAuthorities user.getAuthorities() as String
        model.addAttribute("userInfo", userInfo); // wyswietli to na adminPage:  UserName:dbadmin1 (ROLE_ADMIN, ROLE_USER)
         
        return "security/adminPage";
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
 
        return "security/loginPage";
    }
 
    
    // path do logoutu w konfiguracji tylko: .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout"); 
        // menu dodaje link do logout tylko jesli jest principal:  th:if="${#request.userPrincipal != null}"
        return "security/logoutSuccessfulPage";
    }
 
    
    // After user login successfully as user/student
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {    // "${#request.userPrincipal.name}
        
        String userName = principal.getName();  //z samego principala tylko tyle mozna wyciagnac System.out.println("User Name: " + userName); // User Name: dbadmin1 
        
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = userName + ", " +
        		loginedUser.getUsername() + ",password: " + loginedUser.getPassword() + ", authorities by userName: " + loginedUser.getAuthorities() 
        	+ ", enabled: " + loginedUser.isEnabled() + ", account nonexpired: " + loginedUser.isAccountNonExpired();
 
        userInfo += ", </br>authorities using special method: " + WebUtils.getAuthoritiesAsString(loginedUser); // można więcej info wyciagnac z User(Details)
        model.addAttribute("userInfo", userInfo);
 
        return "security/userInfoPage";  
    }
 
    
    // wszedlem jako zwykly user na http://localhost:8080/admin  i zostalem forwarded tu, bo w pasku adresu jest nadal:
    // http://localhost:8080/admin !            Ale moge tez od razu wejść na http://localhost:8080/403
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.getAuthoritiesAsString(loginedUser); 
            model.addAttribute("userInfo", userInfo);
            
            System.out.println("WebUtils.toString(loginedUser): " + userInfo); // UserName:dbuser1 (ROLE_USER)
 
            String message = "Message passed from controller for /403: <br>"
            		+ " Hi " + principal.getName() + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
            
            
            model.addAttribute("devInfo", "wszedlem jako zwykly user na http://localhost:8080/admin  i zostalem forwarded tu, bo w pasku adresu jest nadal:\n" + 
            		"    // http://localhost:8080/admin !            Ale moge tez od razu wejść na http://localhost:8080/403");
        }
 
        return "security/403Page";
    }
 
}