package pl.ust.school.user;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN", "admin"),
    SCHOOLADMIN("SCHOOLADMIN", "schooladmin"),
    USER("USER", "user"),
    DEVELOPER("DEVELOPER", "dev"),
    STUDENT("STUDENT", "student"),
	TEACHER("TEACHER", "teacher"),
	PARENT("PARENT", "parent");

    private String userRole;
    private String login;

    Role(String role, String login) {
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