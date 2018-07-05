package pl.ust.school.user2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 1. Find User in db  
		
        AppUser appUser = this.userRepository.findUserByUsername(username);
        
        if(appUser == null) {
        	System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");        	
        }
        
       /* ALTERNATYWA A:
        	//2.Map into into UserDetails/User object. Necessary because: from doc:  "make sure you return a copy (of this data) from your UserDetailsService each time it is invoked"
        	UserDetails springSecurityUser = User.builder()
            		.username(username)
            		.password(appUser.getEncrytedPassword())
            		.roles(loadUserAuthoritiesAsStrings(appUser.getUserId()))  // tu wystarczą Strings of authorities
            		.build(); 
        	*/
        	
        	// ALTERNATYWA B:													// a tu musza byc authorities jako GrantedAuthorities
        	 User springUser = new User(appUser.getUserName(), appUser.getEncrytedPassword(), loadUserAuthorities(appUser.getUserId())); 
        	
        	return  springUser; // lub springSecurityUser
	}
	
	private String[] loadUserAuthoritiesAsStrings(long userId) {
		List<Role> roles = this.roleRepository.findRoleByUserId(userId);
		List<String> roleNames = new ArrayList<>();
		
		if(roles == null)
			return new String[0];
		
		for(Role role : roles) {
			roleNames.add(role.getRoleName());
		}
		
		return (String[]) roleNames.toArray();
	}
	
	
	private List<GrantedAuthority> loadUserAuthorities(long userId) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		List<Role> roles = this.roleRepository.findRoleByUserId(userId);
        
        if (roles != null) {
            for (Role role : roles) {  // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                grantedAuthorities.add(authority);
            }
        }
		return grantedAuthorities;
	}

}
