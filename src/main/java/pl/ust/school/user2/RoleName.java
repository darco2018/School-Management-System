package pl.ust.school.user2;

import lombok.Getter;

@Getter
public enum RoleName {

    ADMIN("ADMIN", "admin"),
    SCHOOLADMIN("SCHOOLADMIN", "schooladmin"),
    DEVELOPER("DEVELOPER", "dev"),
    STUDENT("STUDENT", "student"),
	TEACHER("TEACHER", "teacher"),
	PARENT("PARENT", "parent");

    private String userRole;
    private String login;

    RoleName(String role, String login) {
        this.userRole = role;
        this.login = login;
    }

    public boolean isEqualTo(String role) {
        return this.userRole.equals(role);
    }

    public String value() {
        return userRole;
    }
    
    
    
    

}