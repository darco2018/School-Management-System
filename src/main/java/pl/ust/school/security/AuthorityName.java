package pl.ust.school.security;

import lombok.Getter;

@Getter
public enum AuthorityName {

    ADMIN("ADMIN"),
    SCHOOLADMIN("SCHOOLADMIN"),
    DEV("DEV"),
    STUDENT("STUDENT"),
	TEACHER("TEACHER"),
	PARENT("PARENT");

    private String userRole;

    AuthorityName(String role) {
        this.userRole = role;
    }

    public boolean isEqualTo(String role) {
        return this.userRole.equals(role);
    }

    public String value() {
        return userRole;
    }
    
    
    
    

}