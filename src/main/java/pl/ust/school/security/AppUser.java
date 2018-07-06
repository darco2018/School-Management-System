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
	 
	@NotNull @Size(min=3, max=50)
	@NaturalId(mutable = true)
	@Column(unique = true, nullable=false) 
    private String username;
	
	
	@NotNull @Size(min=3, max=100) 
	@Column(nullable=false) 
    private String password;
   
    
    @OneToMany(mappedBy="username", cascade = CascadeType.MERGE, fetch=FetchType.LAZY) 
	private List<Authority> authorities;
    /*
    create table users (
    	       id bigint not null auto_increment,
    	        is_deleted bit default false not null,
    	        password varchar(100) not null,
    	        username varchar(50) not null,
    	        primary key (id)
    	    ) engine=InnoDB
    	    
    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
       
       
       
    	    */
    
 
}

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