package pl.ust.school.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityCheckController {
	
	@RequestMapping("/dev")
	public String devOnlyAccess() {
		return "Protected page - only for DEV";
	}
	
	@RequestMapping("/admin/test")
	public String adminAccess() {
		return "Protected page - only for DEV, ADMIN";
	}
	
	@RequestMapping("/studentuser/test")
	public String studentuserAccess() {
		return "Protected page - only for DEV, ADMIN, SCHOOLADMIN, STUDENT";
	}
	
	@RequestMapping("/teacheruser/test")
	public String teacheruserAccess() {
		return "Protected page - only for DEV, ADMIN, SCHOOLADMIN, TEACHER";
	}
	
	@RequestMapping("/parentuser/test")
	public String parentuserAccess() {
		return "Protected page -  only for DEV, ADMIN, SCHOOLADMIN, PARENT";
	}

}
