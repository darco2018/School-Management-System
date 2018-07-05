package pl.ust.school.user2;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<AppUser, Long> {
	
	AppUser findUserByUsername(String username);

}
