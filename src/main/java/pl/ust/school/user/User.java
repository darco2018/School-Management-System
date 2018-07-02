package pl.ust.school.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.BaseEntity;


@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude = "userRoles")
@EqualsAndHashCode(of = {"username"}, callSuper = false) 
@Where(clause = "is_deleted=false")
@Entity
@Table(name = "users", indexes =  @Index( name = "idx_user_username", columnList = "username", unique = true ))
public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "first_name", nullable = true) 
	private String firstName;

	@Column(name = "last_name", nullable = true)
	private String lastName;
	
	@Email
	@NaturalId(mutable = true)
	@Column(unique = true, nullable=false) 
	private String email;
	
	@NotNull
	@Size(min=3, max=50)
	@NaturalId(mutable = true)
	@Column(unique = true, nullable=false) 
	private String username;
	
	@Size(min=8, max=100)
	@NotNull
	@Column(nullable=false) 
	private String password;
	
	@Column(nullable=false) 
	private boolean enabled;
	
	@Transient
	private String psswrdConfirmation;

	@JsonIgnore
	@OneToMany(mappedBy="username", cascade = CascadeType.MERGE, fetch=FetchType.LAZY) 
	private List<UserRole> userRoles;
	


}
