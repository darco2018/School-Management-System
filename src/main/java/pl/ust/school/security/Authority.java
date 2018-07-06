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
	//@Column(name="user_role", nullable=false)
	private String authority;
	
	/*
	 create table authorities (
		       id bigint not null auto_increment,
		        is_deleted bit default false not null,
		        authority varchar(50) not null,
		        username varchar(50) not null,
		        primary key (id)
		    ) engine=InnoDB
		    
		    alter table authorities 
       add constraint ix_auth_username unique (username, authority)
       
        alter table authorities 
       add constraint FKhjuy9y4fd8v5m3klig05ktofg 
       foreign key (username)  references users (id)
	*/
	
	
	/* // private boolean enabled;

	CREATE TABLE `authorities` (
			  `username` varchar(255) NOT NULL,
			  `authority` varchar(255) NOT NULL,
			  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
			  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
			) ENGINE=InnoDB DEFAULT;

			CREATE TABLE `users` (
			  `username` varchar(255) NOT NULL,
			  `password` varchar(255) NOT NULL,
			  `enabled` tinyint(1) NOT NULL,
			  PRIMARY KEY (`username`)
			) ENGINE=InnoDB DEFAULT;
	*/

}