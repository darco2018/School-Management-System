package pl.ust.school.user;

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
@EqualsAndHashCode(of = {"username", "role"}, callSuper = false) 
@Where(clause = "is_deleted=false")
@Entity
@Table(name = "user_roles", 
	uniqueConstraints = {@UniqueConstraint( columnNames = {"username", "user_role"}, name="ix_auth_username")})
public class UserRole extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min=3, max=50)
	@Column(nullable=false)
	private String username;

	@NotNull
	@Size(min=3, max=50)
	@Column(name="user_role", nullable=false)
	private String role;

}

