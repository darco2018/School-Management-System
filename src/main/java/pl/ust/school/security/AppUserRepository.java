package pl.ust.school.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, Long> {
	
	Optional<AppUser> findUserByUsername(String username);

}
