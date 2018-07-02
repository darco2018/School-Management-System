package pl.ust.school.user;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN", "admin"),
    USER("USER", "user"),
    DEVELOPER("DEVELOPER", "dev"),
    LIBRARIAN("LIBRARIAN", "lib");

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