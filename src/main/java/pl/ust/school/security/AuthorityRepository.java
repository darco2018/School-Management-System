package pl.ust.school.security;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	
	List<Authority> findAuthorityByUsername(String username);
}
