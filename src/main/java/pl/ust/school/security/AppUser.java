package pl.ust.school.security;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.model.BaseEntity;

@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"username"}, callSuper = false) 
@Entity
@Table(name = "users", indexes =  @Index( name = "idx_user_username", columnList = "username", unique = true ))
public class AppUser extends BaseEntity{
	 
	private static final long serialVersionUID = 1L;

	@NotNull @Size(min=3, max=50)
	@NaturalId(mutable = true)
	@Column(unique = true, nullable=false) 
    private String username;
	
	
	@NotNull @Size(min=3, max=100) 
	@Column(nullable=false) 
    private String password;
   
    
    @OneToMany(mappedBy="username", cascade = CascadeType.MERGE, fetch=FetchType.LAZY) 
	private List<Authority> authorities;
}

