package pl.ust.school.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AppUserService implements UserDetailsService {
	
	@Autowired
	AppUserRepository userRepository;
	
	@Autowired
	AuhtorityRepository roleRepository;

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
        	System.err.println("PASSWORD FROM MY USER SERVICE: " + appUser.getPassword());
        	// ALTERNATYWA B:													// a tu musza byc authorities jako GrantedAuthorities
        	 User springUser = new User(appUser.getUsername(), appUser.getPassword(), loadUserAuthorities(appUser.getUsername())); 
        	
        	return  springUser; // lub springSecurityUser
	}
	
	
	
	
	// zastepuje Dao z this.getJdbcTemplate().queryForList(sql, params, String.class);
	private String[] loadUserAuthoritiesAsStrings(String username) {
		List<Authority> roles = this.roleRepository.findAuthorityByUsername(username);
		List<String> roleNames = new ArrayList<>();
		
		if(roles == null)
			return new String[0];
		
		for(Authority role : roles) {
			roleNames.add(role.getAuthority());
		}
		
		return (String[]) roleNames.toArray();
	}
	
	
	private List<GrantedAuthority> loadUserAuthorities(String username) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		List<Authority> roles = this.roleRepository.findAuthorityByUsername(username);
        
        if (roles != null) {
            for (Authority role : roles) {  // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getAuthority());
                grantedAuthorities.add(authority);
            }
        }
		return grantedAuthorities;
	}

}
