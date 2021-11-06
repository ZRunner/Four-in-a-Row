package fourinarow.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fourinarow.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("select Count(1) from User u where u.username = ?1 and u.password = ?2")
	int existsFromLogin(String username, String password);

	@Query("select u from User u where u.username = ?1")
	List<User> getFromUsername(String username);

}