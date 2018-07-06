package pl.ust.school.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
 
public class WebUtils {
 
    public static String getAuthoritiesAsString(org.springframework.security.core.userdetails.User user) {
        StringBuilder grantedAuthorities = new StringBuilder();
 
        grantedAuthorities.append("UserName:").append(user.getUsername()); // getPassword(), getAuthorities() ,isEnabled/Locked etc.
 
        //GrantedAuthority Represents an authority granted to an Authentication object.
        
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        
        if (authorities != null && !authorities.isEmpty()) {
            grantedAuthorities.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    grantedAuthorities.append(a.getAuthority());
                    first = false;
                } else {
                    grantedAuthorities.append(", ").append(a.getAuthority());
                }
            }
            grantedAuthorities.append(")");
        }
        return grantedAuthorities.toString();
    }
}