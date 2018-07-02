package  pl.ust.school.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.ust.school.user.Role;


public class EncodePassword {
		
	public static void main(String[] args) {
		
		String devPassword = Role.DEVELOPER.getLogin();
		String adminPassword = Role.ADMIN.getLogin();
		String libPassword = Role.LIBRARIAN.getLogin();
		String userPassword = Role.USER.getLogin();
			
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.err.println(devPassword == null);
		System.out.println("dev login password: " + passwordEncoder.encode(devPassword));
		// $2a$10$8UIxVZL.jqRFtYnBuqYN1OS80N3wQbgJq0F3txglyaEgfZ3SfPIr6
		System.out.println("admin login password: " + passwordEncoder.encode(adminPassword));
		// $2a$10$2VYRrT14AqU0mKMSE3yPqOGquk2/mEZJTgW0EZeZ3kJwDCG9NdSXK
		System.out.println("librarian login password: " + passwordEncoder.encode(libPassword));
		// $2a$10$ajf3Zn1F0Gl6hgvvk7dAcOz/inmlfgMCo5vg4InvKcgRes/cCAtkS
		System.out.println("user login password: " + passwordEncoder.encode(userPassword));
		// $2a$10$40AVFuSQ3vrz.9i2pPufguOSBrFBGUYszQyjrPn1f1mjOADVC1x6S
		
		System.err.println("Role.LIBRARIAN" + Role.LIBRARIAN);
		System.err.println("Role.LIBRARIAN.value()" + Role.LIBRARIAN.value());
	}

}
/*

SQL script FOR WORKBENCH:
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



SQL script FOR TERMINAL:

create table users(


        username varchar(255) not null primary key,
        password varchar(255)  not null,
        enabled boolean not null
);

create table authorities (
        username varchar(255)  not null,
        authority varchar(255)   not null,
        constraint fk_authorities_users foreign key(username) references users(username)
);


create unique index ix_auth_username on authorities (username,authority);

RENAME TABLE authorities TO user_roles;
ALTER TABLE user_roles CHANGE authority user_role varchar(255);

insert into users(username, password, enabled) values('dev', '$2a$10$LTFSrszGtMGLKA4DfU1YhetB.VCFZELQKNL1xfVu1DvXUe/LZ3PQi', true);
insert into users(username, password, enabled) values('admin', '$2a$10$AMpy2QfYz2pR00.BtkpmcOB6dV0eg7r77WSJkVxsWSFIAM/nfHSKa', true);
insert into users(username, password, enabled) values('lib', '$2a$10$r29SYRp3TlJ.chPF8qrH3eQ23.MPjR75Oc.clCB8eMYk/qM5n1Tia', true);
insert into users(username, password, enabled) values('user', '$2a$10$40AVFuSQ3vrz.9i2pPufguOSBrFBGUYszQyjrPn1f1mjOADVC1x6S', true);


insert ignore into user_roles(id, username, user_role) values(default, 'dev', 'ROLE_DEVELOPER');
insert ignore into user_roles(id, username, user_role) values(default, 'dev', 'ROLE_ADMIN');
insert into user_roles(id, username, user_role) values(default, 'dev', 'ROLE_LIBRARIAN');
insert into user_roles(id, username, user_role) values(default, 'dev', 'ROLE_USER');

insert into user_roles(id, username, user_role) values(default, 'admin', 'ROLE_ADMIN');
insert into user_roles(id, username, user_role) values(default, 'admin', 'ROLE_LIBRARIAN');
insert into user_roles(id, username, user_role) values(default, 'admin', 'ROLE_USER');

insert into user_roles(id, username, user_role) values(default, 'lib', 'ROLE_LIBRARIAN');
insert into user_roles(id, username, user_role) values(default, 'lib', 'ROLE_USER');

insert into user_roles(id, username, user_role) values(default, 'user', 'ROLE_USER');

mysql> COMMIT;

*/