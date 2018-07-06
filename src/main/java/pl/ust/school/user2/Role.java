package pl.ust.school.user2;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.security.AppUser.AppUserBuilder;

/*@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"roleName"}, callSuper = false) 
@Entity
@Table(name = "roles", indexes =  @Index( name = "idx_user_username", columnList = "username", unique = true ))*/
public class Role {
	
	private String roleName;
	
	

}
