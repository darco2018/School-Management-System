package pl.ust.school.user2;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	List<Role> findRoleByUserId(long userId);

}
