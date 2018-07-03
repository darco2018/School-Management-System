package  pl.ust.school.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SecurityController {
	
	@RequestMapping("login")
	public String login() {
		return "this should be the loggin page";
	}
	
	@RequestMapping("hello")
	public String hello11() {
		return "Hello";
	}
	
	@RequestMapping("teacheruser/page1")
	public String protectedByLibrarianRole1() {
		return "teacheruser can enter teacheruser/page1";
	}
	
	@RequestMapping("teacheruser/page2/whatever") 
	public String protectedByLibrarianRole2() {
		return "teacheruser can enter teacheruser/page2/whatever";
	}
	
	@RequestMapping("whatever/teacheruser/page2/whatever") 
	public String protectedByLibrarianRole3() {
		return "teacheruser can enter whatever/teacheruser/page2/whatever";
	}
	
	@RequestMapping("admin/page1")
	public String protectedByAdminRole1() {
		return "Admin can enter admin/page1";
	}
	
	@RequestMapping("admin/page2/whatever")
	public String protectedByAdminRole2() {
		return "Admin can enter admin/page2/whatever";
	}
	
	@RequestMapping("whatever/admin/page2/whatever")
	public String protectedByAdminRole3() {
		return "Admin can enter admin/page2/whatever";
	}
	
	@RequestMapping("/notprotected")
	public String notProtected() {
		return "Im not protected";
	}
	
	
	@RequestMapping("/protectedByTeacherRole")
	public String protectedByUserRole() {
		return "Hello User!. I'm protected By TeacherRole";
	}
	
	
	@RequestMapping("/protectedByDeveloperAdminRole")
	public String protectedByAdminRole() {
		return "Hello Admin and Developer! Im protected By DeveloperRole, AdminRole";
	}
	
	
	
	

}
