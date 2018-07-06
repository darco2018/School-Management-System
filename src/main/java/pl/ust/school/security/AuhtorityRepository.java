package pl.ust.school.security;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AuhtorityRepository extends CrudRepository<Authority, Long> {
	
	List<Authority> findAuthorityByUsername(String username);
}
