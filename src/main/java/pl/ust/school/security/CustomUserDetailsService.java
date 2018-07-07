package pl.ust.school.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	final @NotNull AppUserRepository userRepository;
	final @NotNull AuhtorityRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username){

		Optional<AppUser> opt = this.userRepository.findUserByUsername(username);
		AppUser appUser = opt.orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
        return toUserDetails(appUser);
    	 
	}
	
	private UserDetails toUserDetails(AppUser appUser) {
		
		return  new CustomUserDetails(appUser.getUsername(), 
					appUser.getPassword(), 
					loadUserAuthorities(appUser.getUsername()), 
					"1234567"); //TODO remove this mock registration number with actual
		
		// using custom UserDetails enables to add custom properties to Spring Security USerDetails kept in session
        // for alternative solutions look at bottom of page
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
	
	// enables to store extra fields in SpringSecurity's UserDetails object, eg registration number
	// you can also override UserDetails methods
	@Getter @Setter 
	@EqualsAndHashCode(callSuper=true, exclude="registrationNum") // super uses only "username"
	class CustomUserDetails extends User{
		
		private static final long serialVersionUID = 1L;
		private String registrationNum; // other fields can be added to be stored in Spring's session User
		
		public CustomUserDetails(String username, String password, 
				Collection<? extends GrantedAuthority> authorities, String registrationNum) {
			super(username, password, authorities);
			this.registrationNum = registrationNum;
		}
	}
	
	
	
	///////////////////// info for later //////////////////
	/* ALTERNATIVE FOR List<GrantedAuthority> loadUserAuthorities(String username)
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
	*/
	
	
	/* ALTERNATYWA A for new CustomUserDetails(....):
	//2.Map appUser into UserDetails/User object. Necessary because: 
 	from doc:  "make sure you return a copy (of this data) from your UserDetailsService each time it is invoked"
	UserDetails springSecurityUser = User.builder()
		.username(username)
		.password(appUser.getEncrytedPassword())
		.roles(loadUserAuthoritiesAsStrings(appUser.getUserId()))  // tu wystarczą Strings of authorities
		.build(); 
		
	return springSecurityUser;  */
	

/*  

// ALTERNATYWA B for new CustomUserDetails(....):												// a tu musza byc authorities jako GrantedAuthorities
 User springUser = new User(appUser.getUsername(), appUser.getPassword(), loadUserAuthorities(appUser.getUsername())); 
 return  springUser;
*/

}
