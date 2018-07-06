package pl.ust.school.security;

import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, Long> {
	
	AppUser findUserByUsername(String username);

}
