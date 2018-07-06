package pl.ust.school.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.BaseEntity;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false)
@EqualsAndHashCode(of = {"username", "authority"}, callSuper = false) 
@Where(clause = "is_deleted=false")
@Entity
@Table(name = "authorities", 
	uniqueConstraints = {@UniqueConstraint( columnNames = {"username", "authority"}, name="ix_auth_username")})
public class Authority extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@NotNull @Size(min=3, max=50)
	@Column(nullable=false)
	private String username; 

	@NotNull @Size(min=3, max=50)
	@Column(name="authority", nullable=false)
	private String authority;

}